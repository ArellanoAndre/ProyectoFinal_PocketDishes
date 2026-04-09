package islas.abril.pocketdishes.screens


import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import islas.abril.pocketdishes.components.IngredientCard
import islas.abril.pocketdishes.components.InstructionStepItem
import islas.abril.pocketdishes.components.PrepTime
import islas.abril.pocketdishes.components.RecipeHeader
import islas.abril.pocketdishes.components.Tabs
import islas.abril.pocketdishes.data.Recipe
import islas.abril.pocketdishes.ui.theme.backgroundLightTheme
import returnRandomRecipe

//PREVIEW TEMPORAL CON DATOS MOCK
@Preview(showBackground = true)
@Composable
fun Preview() {
    RecipeDetailContent(returnRandomRecipe())
}

@Composable
fun RecipeDetailContent(recipe: Recipe) {

    // para la funcionalidad de los tabs entre ingredientes y instrucciones
    var selectedTab by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundLightTheme)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            RecipeHeader(
                recipe
            )

            Spacer(modifier = Modifier.height(5.dp))

            PrepTime(recipe.prepTime)

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
                            contentPadding = PaddingValues(bottom = 100.dp)
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
                            contentPadding = PaddingValues(bottom = 100.dp)
                        )  {
                            itemsIndexed(recipe.steps) { index, step ->
                                InstructionStepItem(index, step)
                            }
                        }
                    }
                }
            }

            // menu de navegacion...



        }
    }
}