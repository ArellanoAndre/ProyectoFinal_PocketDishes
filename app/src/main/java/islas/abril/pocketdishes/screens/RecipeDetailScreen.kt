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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import islas.abril.pocketdishes.components.BottomNavigationMenu
import islas.abril.pocketdishes.components.IngredientCard
import islas.abril.pocketdishes.components.InstructionStepItem
import islas.abril.pocketdishes.components.PrepTime
import islas.abril.pocketdishes.components.RecipeHeader
import islas.abril.pocketdishes.components.Tabs
import islas.abril.pocketdishes.data.Recipe
import islas.abril.pocketdishes.ui.theme.LightGreenMenu
import islas.abril.pocketdishes.ui.theme.PocketDishesTheme
import islas.abril.pocketdishes.ui.theme.backgroundLightTheme
import returnRandomRecipe

//PREVIEW TEMPORAL CON DATOS MOCK
@Preview(showBackground = true)
@Composable
fun Preview() {
    PocketDishesTheme() {
        val navController = rememberNavController()
        RecipeDetailScreen(returnRandomRecipe(), onBackClick = {}, navController)
    }
}

@Composable
fun RecipeDetailScreen(recipe: Recipe, onBackClick: () -> Unit, navController: NavController) {

    // para la funcionalidad de los tabs entre ingredientes y instrucciones
    var selectedTab by remember { mutableStateOf(0) }

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
                recipe,
                onBackClick
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
                        )  {
                            items(recipe.ingredients) { ingredient ->
                                IngredientCard(
                                    ingredient
                                )
                            }
                        }
                    }

                    // INSTRUCCIONES
                    1 -> {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(bottom = 160.dp)
                        )  {
                            itemsIndexed(recipe.steps) { index, step ->
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
                .background(LightGreenMenu) // para que no se vean las imagenes de los ingredientes debajo del menu (si son muchas y requieren scroll)
        ) {
            Box(
                modifier = Modifier.navigationBarsPadding() // padding para que no se cubra por el menu de navegacion del telefono
            ) {
                BottomNavigationMenu(navController = navController)
            }
        }
    }
}


