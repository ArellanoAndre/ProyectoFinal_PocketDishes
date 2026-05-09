package islas.abril.pocketdishes.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.components.BottomNavigationMenu
import islas.abril.pocketdishes.components.favouriteRecipeCard
import islas.abril.pocketdishes.components.header
import islas.abril.pocketdishes.data.room.toRecipe
import islas.abril.pocketdishes.ui.theme.backgroundOrange
import islas.abril.pocketdishes.viewmodel.PocketDishesViewModel

@Composable
fun homescreen(navController: NavController, viewModel: PocketDishesViewModel) {

    val userRecipes by viewModel.userRecipes.collectAsState()
    val context = LocalContext.current
    // Solo muestra recetas NO secretas en "My Recipes" - las secretas van a su propia seccion
    val displayRecipes = remember(userRecipes) {
        userRecipes.filter { !it.isSecret }.map { it.toRecipe(context) }
    }

    androidx.compose.material3.Scaffold(
        topBar = {
            header()
                },
        bottomBar = {
            // LO PUSE ADENTRO DE UN BOX CON NAVIGATION BARS PADDING PARA QUE NO CHOQUE CON EL MENU DEL TELEFONO
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
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp)
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
                        contentDescription = "imagen01",
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
                        color = MaterialTheme.colorScheme.outline,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        modifier = Modifier
                        .clickable{ navController.navigate("explore") }
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(bottom = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,

                ) {
                    Text(
                        text = "My recipes",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.filter_alt_24px),
                        contentDescription = "more",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(35.dp)
                            .clickable { /* navigate */ }
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.favorite_24px),
                        contentDescription = "more",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(35.dp)
                            .clickable { /* navigate */ }
                    )
                }
                if (displayRecipes.isEmpty()) {
                    Text(
                        text = "No tienes recetas aún.\nAgrega tu primer receta!.",
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(
                            top = 8.dp,
                            bottom = 100.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(displayRecipes) { recipe ->
                            favouriteRecipeCard(
                                recipe = recipe,
                                cardColor = backgroundOrange,
                                onCardClick = {
                                    navController.navigate("detail/${recipe.name}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// @Preview(showBackground = true)
// @Composable
// fun homescreenPreview(){
//     PocketDishesTheme {
//         val navController = rememberNavController()
//         homescreen(navController = navController, viewModel = ...)
//     }
// }