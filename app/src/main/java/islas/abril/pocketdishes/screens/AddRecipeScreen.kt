package islas.abril.pocketdishes.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.components.*
import islas.abril.pocketdishes.data.RecipeTags
import islas.abril.pocketdishes.data.Tag
import islas.abril.pocketdishes.data.recipeCategories
import islas.abril.pocketdishes.ui.theme.*

@Composable
fun AddRecipeScreen(navController: NavController) {

    var recipeName by remember { mutableStateOf("") }
    var recipeDescription by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    var prepTime by remember { mutableStateOf("") }
    val selectedTags = remember { mutableStateListOf<Tag>() }

    Scaffold(
        topBar = { headerV2() },
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(gradientStart, gradientEnd)
                    )
                )
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Add a recipe",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
            ) {

                Column(modifier = Modifier.padding(16.dp)) {

                    // 🔹 INPUTS
                    textField("Name", recipeName, { recipeName = it }, "Homemade Pizza")

                    textField(
                        "Description",
                        recipeDescription,
                        { recipeDescription = it },
                        "Write a description"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // 🔹 IMAGEN
                    Text("Upload a picture", fontWeight = FontWeight.SemiBold)

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(Color(0xFFF3F3F3), RoundedCornerShape(16.dp))
                            .padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.pizza),
                            contentDescription = "Recipe",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 🔹 PREP TIME + TAGS
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        // PREP TIME
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Prep time", fontWeight = FontWeight.Medium)

                            Box(
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .background(Color(0xFFF3F3F3), RoundedCornerShape(10.dp))
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = if (prepTime.isEmpty()) "30 minutes" else prepTime,
                                    color = Color.Gray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        // TAGS
                        Column(modifier = Modifier.weight(1f)) {

                            Text("Tags", fontWeight = FontWeight.Medium)

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 5.dp)
                            ) {

                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFFF3F3F3), RoundedCornerShape(10.dp))
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text("Add tag", color = Color.Gray)
                                }

                                Spacer(modifier = Modifier.width(6.dp))

                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .background(
                                            MaterialTheme.colorScheme.primary,
                                            RoundedCornerShape(6.dp)
                                        )
                                        .clickable {
                                            val randomTag = RecipeTags.random()
                                            if (!selectedTags.contains(randomTag)) {
                                                selectedTags.add(randomTag)
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("+", color = Color.White)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 🔹 TAGS SELECCIONADOS
                    Row {
                        selectedTags.forEach { tag ->
                            Row(
                                modifier = Modifier
                                    .padding(end = 6.dp)
                                    .background(tag.color, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = tag.name,
                                    color = Color.White,
                                    fontSize = 12.sp
                                )

                                Spacer(modifier = Modifier.width(5.dp))

                                Text(
                                    text = "x",
                                    color = Color.White,
                                    modifier = Modifier.clickable {
                                        selectedTags.remove(tag)
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    // 🔹 CATEGORY
                    combobox("Categories", recipeCategories, selectedCategory) {
                        selectedCategory = it
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}