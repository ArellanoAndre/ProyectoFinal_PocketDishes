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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.components.BottomNavigationMenu
import islas.abril.pocketdishes.components.RecipePreviewCard
import islas.abril.pocketdishes.components.header
import islas.abril.pocketdishes.data.Recipe
import islas.abril.pocketdishes.data.recipeCategories
import islas.abril.pocketdishes.ui.theme.LightGreenMenu
import islas.abril.pocketdishes.ui.theme.PocketDishesTheme
import islas.abril.pocketdishes.ui.theme.backgroundOrange
import islas.abril.pocketdishes.ui.theme.mainOrange
import islas.abril.pocketdishes.ui.theme.typoColorBrown
import returnRecipes

@Composable
fun ExploreScreen(recipeList: List<Recipe>, navController: NavController) {

    androidx.compose.material3.Scaffold(
        topBar = {
            header()
        },
        bottomBar = {
            // LO PUSE ADENTRO DE UN BOX CON NAVIGATION BARS PADDING PARA QUE NO CHOQUE CON EL MENU DEL TELEFONO
            Box(
                modifier = Modifier
                    .background(LightGreenMenu)
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
            Column(
            ) {
                Text(
                    text = "Explore our \ncommunity!",
                    style = MaterialTheme.typography.titleLarge,
                    color = mainOrange,
                    modifier = Modifier.padding(start=20.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(top = 5.dp),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Row(
                        modifier = Modifier.clickable{ /* navigate */ }
                        .padding(start=20.dp)
                    ) {
                        Text(
                            text = "Categories",
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.filter_alt_24px),
                            contentDescription = "more",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(23.dp)
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.favorite_24px),
                        contentDescription = "more",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(23.dp)
                            .clickable { /* navigate */ }
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "Popular lately...",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(start=20.dp)
                )

                LazyColumn(
                    modifier = Modifier.padding(top=30.dp)
                ) {
                    items(recipeCategories) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                            .background(backgroundOrange)
                            .padding(top=15.dp, bottom=20.dp,start=20.dp)
                        ) {
                            Column() {
                                Text(
                                    text = it,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = typoColorBrown,
                                        //MaterialTheme.colorScheme.outline
                                    modifier = Modifier.padding(bottom=10.dp)
                                )
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    items(returnRecipes()){
                                        RecipePreviewCard(it, navController)
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
@Preview (showBackground = true)
@Composable
fun previewExploreScreen(){
    PocketDishesTheme {
        val navController = rememberNavController()
        ExploreScreen(returnRecipes(), navController)
    }
}