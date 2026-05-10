package islas.abril.pocketdishes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.components.BottomNavigationMenu
import islas.abril.pocketdishes.components.RecipePreviewCard
import islas.abril.pocketdishes.components.header
import islas.abril.pocketdishes.components.selectionBar
import islas.abril.pocketdishes.data.recipeCategories
import islas.abril.pocketdishes.data.room.toRecipe
import islas.abril.pocketdishes.ui.theme.backgroundOrange
import islas.abril.pocketdishes.ui.theme.mainOrange
import islas.abril.pocketdishes.ui.theme.typoColorBrown
import islas.abril.pocketdishes.viewmodel.PocketDishesViewModel

@Composable
fun ExploreScreen(viewModel: PocketDishesViewModel, navController: NavController) {

    val context = LocalContext.current
    var showOnlyFavorites by remember { mutableStateOf(false) }

    // Recetas publicas desde la BD
    val publicRecipesEntities by viewModel.publicRecipes.collectAsState()
    val displayRecipes = remember(publicRecipesEntities) {
        publicRecipesEntities.map { it.toRecipe(context) }
    }
    //favoritas
    val favoriteRecipes by viewModel.favoriteRecipes.collectAsState()
    val favoriteIds = favoriteRecipes.map { it.idRecipe }.toSet()

    val filteredRecipes = if (showOnlyFavorites) {
        displayRecipes.filter { it.id in favoriteIds }
    } else {
        displayRecipes
    }
    // Agrupar por categoria y solo mostrar las que tienen al menos una receta
    val recipesByCategory = filteredRecipes.groupBy {
        it.category.firstOrNull() ?: ""
    }
    val activeCategories = remember(recipesByCategory) {
        recipeCategories.filter { category -> recipesByCategory.containsKey(category) }
    }

    androidx.compose.material3.Scaffold(
        topBar = {
            header()
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .fillMaxWidth()
            ) {
                Box(modifier = Modifier.navigationBarsPadding()) {
                    BottomNavigationMenu(navController = navController)
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column {
                Text(
                    text = "Explore our \ncommunity!",
                    style = MaterialTheme.typography.titleLarge,
                    color = mainOrange,
                    modifier = Modifier.padding(start = 20.dp)
                )
                Row(
                    modifier = Modifier.padding(start=20.dp)){
                    selectionBar("Filters",  showOnlyFavorites,
                        {
                        showOnlyFavorites = !showOnlyFavorites
                    })
                }

                if (activeCategories.isEmpty()) {
                    Text(
                        text = "No hay recetas disponibles aún.",
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(start = 20.dp, top = 20.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(top = 15.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.padding(15.dp))
                            Text(
                                text = "All categories",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.padding(start = 20.dp)
                            )
                        }
                        items(activeCategories) { category ->
                            val recipesInCategory = recipesByCategory[category] ?: emptyList()
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(backgroundOrange)
                                    .padding(top = 15.dp, bottom = 20.dp, start = 20.dp)
                            ) {
                                Column {
                                    Text(
                                        text = category,
                                        fontSize = 25.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = typoColorBrown,
                                        modifier = Modifier.padding(bottom = 10.dp)
                                    )
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        items(recipesInCategory) { recipe ->
                                                RecipePreviewCard(
                                                    recipe = recipe,
                                                    navController = navController,
                                                    isFavorite = recipe.id in favoriteIds,
                                                    onFavoriteClick = {
                                                        viewModel.updateFavorite(
                                                            recipeId = recipe.id
                                                        )
                                                    }
                                                )
                                            }
                                    }

                                }
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                        }
                    }
                }
            }
        }
    }
}
