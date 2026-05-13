package islas.abril.pocketdishes.screens


import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import islas.abril.pocketdishes.components.BottomNavigationMenu
import islas.abril.pocketdishes.components.IngredientCard
import islas.abril.pocketdishes.components.InstructionStepItem
import islas.abril.pocketdishes.components.PrepTime
import islas.abril.pocketdishes.components.RecipeHeader
import islas.abril.pocketdishes.components.Tabs
import islas.abril.pocketdishes.data.Recipe
import islas.abril.pocketdishes.data.room.toIngredients
import islas.abril.pocketdishes.viewmodel.PocketDishesViewModel

@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    viewModel: PocketDishesViewModel,
    onBackClick: () -> Unit,
    navController: NavController
) {
    val context = LocalContext.current

    // Carga ingredientes y pasos desde la BD cuando abre la pantalla
    LaunchedEffect(recipe.name) {
        viewModel.loadRecipeDetailByName(recipe.name)
    }

    val activeIngredients by viewModel.activeIngredients.collectAsState()
    val activeSteps by viewModel.activeSteps.collectAsState()
    val activeRecipeAuthorName by viewModel.activeRecipeAuthorName.collectAsState()

    // Convierte datos de BD para poder mostrarse en la screen
    val displayIngredients = remember(activeIngredients) {
        activeIngredients.map { it.toIngredients(context) }
    }
    val displaySteps = remember(activeSteps) {
        activeSteps.sortedBy { it.stepNumber }.map { it.description }
    }

    // para la funcionalidad de los tabs entre ingredientes y instrucciones
    var selectedTab by remember { mutableStateOf(0) }

    // usuario actual (para saber si es el autor)
    val currentUser by viewModel.currentUser.collectAsState()
    val resolvedAuthorName = activeRecipeAuthorName.ifEmpty { recipe.author }
    val isAuthor = currentUser?.name == resolvedAuthorName

    // favoritas
    val favoriteRecipes by viewModel.favoriteRecipes.collectAsState()
    val favoriteIds = favoriteRecipes.map { it.idRecipe }.toSet()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            //HEADER
            RecipeHeader(
                recipe = recipe,
                onBackClick = onBackClick,
                authorName = resolvedAuthorName,
                isFavorite = recipe.id in favoriteIds,
                onFavoriteClick = {
                    viewModel.updateFavorite(recipe.id)
                },
                viewModel = viewModel,
                onRatingSelected = if (!isAuthor && currentUser != null) {
                    { rating -> viewModel.updateRating(recipe.id, rating) }
                } else null,
                onEdit = {
                    navController.navigate("editRecipeScreen/${recipe.name}")
                },
                onDelete = {
                    viewModel.deleteRecipe(recipe)
                    onBackClick()
                },
                onShare = {
                    //nose
                }
            )

            Spacer(modifier = Modifier.height(5.dp))

            //PREP TIME
            PrepTime(recipe.prepTime)

            // TABS (INGREDIENTES Y INSTRUCCIONES)
            Tabs(
                selected = selectedTab,
                onChange = { selectedTab = it }
            )

            // ingredientes... y instrucciones...
            Spacer(modifier = Modifier.height(15.dp))

            Crossfade (targetState = selectedTab) { tab ->
                when (tab) {

                    // INGREDIENTES
                    0 -> {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(bottom = 160.dp)
                        ) {
                            items(displayIngredients) { ingredient ->
                                IngredientCard(ingredient)
                            }
                        }
                    }

                    // INSTRUCCIONES
                    1 -> {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(bottom = 160.dp)
                        ) {
                            itemsIndexed(displaySteps) { index, step ->
                                InstructionStepItem(index, step)
                            }
                        }
                    }
                }
            }

        }
        // MENU DE NAVEGACION
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer) // para que no se vean las imagenes de los ingredientes debajo del menu (si son muchas y requieren scroll)
        ) {
            Box(
                modifier = Modifier.navigationBarsPadding() // padding para que no se cubra por el menu de navegacion del telefono
            ) {
                BottomNavigationMenu(navController = navController)
            }
        }
    }
}


