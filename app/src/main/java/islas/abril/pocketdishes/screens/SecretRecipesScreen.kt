package islas.abril.pocketdishes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.components.BottomNavigationMenu
import islas.abril.pocketdishes.components.favouriteRecipeCard
import islas.abril.pocketdishes.components.headerV2
import islas.abril.pocketdishes.ui.theme.LightGreenMenu
import islas.abril.pocketdishes.ui.theme.PocketDishesTheme
import islas.abril.pocketdishes.ui.theme.backgroundDarkTheme
import islas.abril.pocketdishes.ui.theme.brightIndigo
import returnRecipes

@Composable
fun SecretRecipeScreen(navController: NavController){
    androidx.compose.material3.Scaffold(
        topBar = {
            headerV2()
        },
        bottomBar = {
            // LO PUSE ADENTRO DE UN BOX CON NAVIGATION BARS PADDING PARA QUE NO CHOQUE CON EL MENU DEL TELEFONO
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onSecondaryContainer)
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
                .background(backgroundDarkTheme)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp, bottom = 0.dp)
                ) {
                    Text(
                        text = "Abri's \nSecret Recipes",
                        style = MaterialTheme.typography.titleLarge,
                        color = brightIndigo
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Icon(
                            painter = painterResource(id=R.drawable.vpn_key_24px),
                            contentDescription = "",
                            tint = brightIndigo,
                            modifier = Modifier.size(100.dp)

                        )
                            Spacer(Modifier.padding(end=10.dp))
                            Icon(
                                painter = painterResource(id=R.drawable.baseline_circle_24),
                                contentDescription = "",
                                tint = brightIndigo,
                                modifier = Modifier.size(25.dp)
                            )
                            Spacer(Modifier.padding(2.dp))
                            Icon(
                                painter = painterResource(id=R.drawable.baseline_circle_24),
                                contentDescription = "",
                                tint = brightIndigo,
                                modifier = Modifier.size(25.dp)
                            )
                            Spacer(Modifier.padding(2.dp))
                            Icon(
                                painter = painterResource(id=R.drawable.baseline_circle_24),
                                contentDescription = "",
                                tint = brightIndigo,
                                modifier = Modifier.size(25.dp)
                            )
                    }
                    Spacer(Modifier.padding(10.dp))

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(
                            top = 8.dp,
                            bottom = 20.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val recipes = returnRecipes()

                        items(recipes) { recipe ->
                            favouriteRecipeCard(
                                recipe = recipe,
                                brightIndigo,
                                onCardClick = {
                                    navController.navigate("detail/${recipe.name}")
                                }
                            )
                        }
                        item {
                            Text(
                                text = "No more items to show",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF443838).copy(alpha = 0.55f)
                            )
                        }
                    }
                }

            }
        }
    }

@Preview (showBackground = true)
@Composable
fun prevSecretRecipeScreen(){
    val navController = rememberNavController()
    PocketDishesTheme() {
        SecretRecipeScreen(navController)
    }
}