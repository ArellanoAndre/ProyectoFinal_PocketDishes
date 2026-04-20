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
import androidx.navigation.NavController
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.components.*
import islas.abril.pocketdishes.data.Ingredients
import islas.abril.pocketdishes.data.RecipeTags
import islas.abril.pocketdishes.data.Tag
import islas.abril.pocketdishes.data.recipeCategories
import islas.abril.pocketdishes.data.enums.Units
import islas.abril.pocketdishes.ui.theme.*

@Composable
fun AddRecipeScreen(navController: NavController) {

    var recipeName by remember { mutableStateOf("") }
    var recipeDescription by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    var prepTime by remember { mutableStateOf("") }

    val selectedTags = remember { mutableStateListOf<Tag>() }
    var selectedTagName by remember { mutableStateOf("") }

    // 🔥 INGREDIENTES
    var ingredientName by remember { mutableStateOf("") }
    var ingredientAmount by remember { mutableStateOf("") }
    var ingredientUnit by remember { mutableStateOf(Units.GR) }

    val ingredientsList = remember { mutableStateListOf<Ingredients>() }

    val tagNames = RecipeTags.map { it.name }

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
                    Row(modifier = Modifier.fillMaxWidth()) {

                        Column(modifier = Modifier.weight(1f)) {
                            Text("Prep time")

                            textField(
                                "",
                                prepTime,
                                { prepTime = it },
                                "30 minutes"
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {

                            Text("Tags")

                            combobox(
                                "Select tag",
                                tagNames,
                                selectedTagName
                            ) { selectedName ->

                                selectedTagName = ""

                                val tag = RecipeTags.find { it.name == selectedName }

                                if (tag != null && !selectedTags.contains(tag)) {
                                    selectedTags.add(tag)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 🔹 TAGS
                    Row {
                        selectedTags.forEach { tag ->
                            Row(
                                modifier = Modifier
                                    .padding(end = 6.dp)
                                    .background(tag.color, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(tag.name, color = Color.White)

                                Spacer(modifier = Modifier.width(5.dp))

                                Text(
                                    "x",
                                    color = Color.White,
                                    modifier = Modifier.clickable {
                                        selectedTags.remove(tag)
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    // 🔥 INGREDIENTES
                    Text("Ingredients", fontWeight = FontWeight.Bold)

                    textField("Name", ingredientName, { ingredientName = it }, "Dough")
                    textField("Amount", ingredientAmount, { ingredientAmount = it }, "300")

                    combobox(
                        "Unit",
                        Units.values().map { it.name },
                        ingredientUnit.name
                    ) {
                        ingredientUnit = Units.valueOf(it)
                    }

                    Button(
                        onClick = {
                            if (ingredientName.isNotBlank() && ingredientAmount.isNotBlank()) {

                                ingredientsList.add(
                                    Ingredients(
                                        name = ingredientName,
                                        amount = ingredientAmount.toInt(),
                                        unit = ingredientUnit,
                                        image = R.drawable.pizza
                                    )
                                )

                                ingredientName = ""
                                ingredientAmount = ""
                            }
                        }
                    ) {
                        Text("Add Ingredient")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    ingredientsList.forEachIndexed { index, ingredient ->

                        Column {

                            IngredientCard(ingredient)

                            Text(
                                "Remove",
                                color = Color.Red,
                                modifier = Modifier.clickable {
                                    ingredientsList.removeAt(index)
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    combobox("Categories", recipeCategories, selectedCategory) {
                        selectedCategory = it
                    }
                }
            }
        }
    }
}