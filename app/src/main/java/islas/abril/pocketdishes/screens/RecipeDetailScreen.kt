package islas.abril.pocketdishes.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import islas.abril.pocketdishes.components.*
import islas.abril.pocketdishes.data.Recipe
import islas.abril.pocketdishes.ui.theme.*

@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onBackClick: () -> Unit,
    navController: NavController
) {

    var selectedTab by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // 🔹 Aquí puedes tener tu header si ya lo tienes
        headerV2()

        // 🔹 Tabs (puedes ajustarlos si ya tienes diseño)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TabButton("Info", selectedTab == 0) { selectedTab = 0 }
            TabButton("Steps", selectedTab == 1) { selectedTab = 1 }
        }

        Crossfade(targetState = selectedTab) { tab ->

            when (tab) {

                // 🔶 INFO (puedes personalizarlo)
                0 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text("Recipe Info", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Name: ${recipe.name}")
                        Text("Description: ${recipe.description}")
                        Text("Prep Time: ${recipe.prepTime}")
                    }
                }

                // 🔥 STEPS (CORREGIDO)
                1 -> {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(bottom = 120.dp)
                    ) {

                        itemsIndexed(recipe.steps) { index, step ->

                            InstructionStepItem(
                                index = index,
                                step = step,
                                onDelete = {
                                    // vacío porque aquí solo se visualiza
                                }
                            )
                        }
                    }
                }
            }
        }

        // 🔻 Bottom menu (si ya lo usas)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter as Alignment.Horizontal)
                .fillMaxWidth()
                .background(Color(0xFFDFF5E1))
        ) {
            BottomNavigationMenu(navController = navController)
        }
    }
}