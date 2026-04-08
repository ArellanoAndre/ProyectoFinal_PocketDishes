package islas.abril.pocketdishes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import islas.abril.pocketdishes.components.BottomNavigationMenu
import islas.abril.pocketdishes.data.Recipe
import islas.abril.pocketdishes.ui.theme.PocketDishesTheme
import returnRecipes

@Composable
fun ExploreScreen(recipeList: List<Recipe>) {

    androidx.compose.material3.Scaffold(
        bottomBar = {
            BottomNavigationMenu()
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {

        }
    }


}

@Preview (showBackground = true)
@Composable
fun previewExploreScreen(){
    PocketDishesTheme {
        ExploreScreen(returnRecipes())
    }
}