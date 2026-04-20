package islas.abril.pocketdishes.screens

import android.net.Uri // 🔥 IMPORTANTE
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.components.BottomNavigationMenu
import islas.abril.pocketdishes.components.favouriteRecipeCard
import islas.abril.pocketdishes.components.header
import islas.abril.pocketdishes.ui.theme.LightGreenMenu
import islas.abril.pocketdishes.ui.theme.PocketDishesTheme
import islas.abril.pocketdishes.ui.theme.typoColorBrown
import returnRecipes

@Composable
fun homescreen(navController: NavController) {

    Scaffold(
        topBar = {
            header()
        },
        bottomBar = {
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
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
            ) {

                Text(
                    text = "New Recipes, \neveryday.",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(
                    modifier = Modifier
                        .padding(start = 0.dp, top = 15.dp, end = 10.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.sushi),
                        contentDescription = "imagen01",
                        modifier = Modifier
                            .size(260.dp, 180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.FillWidth,
                    )

                    Spacer(modifier = Modifier.padding(5.dp))
                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = R.drawable.macarons),
                        contentDescription = "imagen02",
                        modifier = Modifier
                            .size(130.dp, 180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.FillHeight,
                    )
                }

                Row(
                    Modifier.padding(top = 7.dp, end = 15.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "Explore recipes",
                        color = typoColorBrown,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable {
                            navController.navigate("explore")
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(bottom = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "My recipes",
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Medium,
                        fontSize = 25.sp
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.filter_alt_24px),
                        contentDescription = "filter",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .size(27.dp)
                            .clickable { }
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.favorite_24px),
                        contentDescription = "favorite",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .size(27.dp)
                            .clickable { }
                    )
                }

                val recipes = returnRecipes()

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(
                        top = 8.dp,
                        bottom = 100.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(recipes) { recipe ->

                        favouriteRecipeCard(
                            recipe = recipe,
                            onCardClick = {

                                // 🔥 SOLUCIÓN AQUÍ
                                val encodedName = Uri.encode(recipe.name)

                                navController.navigate("detail/$encodedName")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun homescreenPreview() {
    PocketDishesTheme {
        val navController = rememberNavController()
        homescreen(navController = navController)
    }
}