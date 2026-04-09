package islas.abril.pocketdishes.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
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
    //PENDIENTE

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

            //funcionalidad pendiente
            Tabs(
                selected = selectedTab,
                onChange = { selectedTab = it }
            )

            // ingredientes... y instrucciones...




            }



    }
}