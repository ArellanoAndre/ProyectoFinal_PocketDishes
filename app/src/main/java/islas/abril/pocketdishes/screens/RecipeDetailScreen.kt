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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import islas.abril.pocketdishes.components.BottomNavigationMenu
import islas.abril.pocketdishes.components.PrepTime
import islas.abril.pocketdishes.components.RecipeHeader
import islas.abril.pocketdishes.data.Recipe
import islas.abril.pocketdishes.data.getTag
import returnRandomRecipe

//PREVIEW TEMPORAL CON DATOS MOCK
@Preview(showBackground = true)
@Composable
fun Preview() {
    RecipeDetailContent(returnRandomRecipe())
}

@Composable
fun RecipeDetailContent(recipe: Recipe) {

    var selectedTab by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEADBC8))
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            RecipeHeader(
                recipe
            )

            Spacer(modifier = Modifier.height(5.dp))

            PrepTime(recipe.prepTime)




            }



    }
}