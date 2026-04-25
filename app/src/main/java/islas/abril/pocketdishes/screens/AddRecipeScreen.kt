package islas.abril.pocketdishes.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.components.*
import islas.abril.pocketdishes.data.*
import islas.abril.pocketdishes.data.dummies.IngredientList

@Composable
fun AddRecipeScreen(navController: NavController) {

    val context = LocalContext.current

    // 🔥 IMAGE
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> imageUri = uri }

    // 🔥 BASIC INFO
    var recipeName by remember { mutableStateOf("") }
    var recipeDescription by remember { mutableStateOf("") }
    var prepTime by remember { mutableStateOf("") }

    // 🔥 TAGS
    val selectedTags = remember { mutableStateListOf<Tag>() }
    var selectedTagName by remember { mutableStateOf("") }
    val tagNames = RecipeTags.map { it.name }

    // 🔥 INGREDIENTS
    val ingredientsList = remember { mutableStateListOf<Ingredients>() }
    var selectedIngredient by remember { mutableStateOf<Ingredients?>(null) }
    var ingredientAmount by remember { mutableStateOf("") }
    var expandedIngredient by remember { mutableStateOf(false) }

    // 🔥 STEPS (STRING)
    val stepsList = remember { mutableStateListOf<String>() }
    var showStepDialog by remember { mutableStateOf(false) }
    var stepTitle by remember { mutableStateOf("") }
    var stepDescription by remember { mutableStateOf("") }

    Scaffold(
        topBar = { headerV2() },
        bottomBar = { BottomNavigationMenu(navController = navController) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFFFFE0C2),
                            Color(0xFFFF9A4D)
                        )
                    )
                )
                .padding(16.dp)
        ) {

            Text(
                "Add a recipe",
                color = Color(0xFFFF7A00),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔶 MAIN CARD
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(Color(0xFFFFE9D6))
            ) {

                Column(Modifier.padding(16.dp)) {

                    textField("Name", recipeName, { recipeName = it }, "Homemade Pizza")
                    textField("Description", recipeDescription, { recipeDescription = it }, "Write a description")

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Upload a picture", color = Color.Gray)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { launcher.launch("image/*") }
                    ) {

                        if (imageUri != null) {
                            AsyncImage(
                                model = imageUri,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.pizza),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row {

                        Column(Modifier.weight(1f)) {
                            Text("Prep time", color = Color.Gray)
                            textField("", prepTime, { prepTime = it }, "30 min")
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(Modifier.weight(1f)) {
                            Text("Tags", color = Color.Gray)

                            combobox("Select tag", tagNames, selectedTagName) {
                                selectedTagName = it

                                val tag = RecipeTags.find { it.name == selectedTagName }

                                if (tag != null && !selectedTags.contains(tag)) {
                                    selectedTags.add(tag)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    recipeTags(selectedTags)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔶 INGREDIENTS
            Text("Ingredients", color = Color.White)

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(Color(0xFFFFA85A))
            ) {

                Column(Modifier.padding(12.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Box(Modifier.weight(1f)) {

                            Text(
                                selectedIngredient?.name ?: "Select",
                                modifier = Modifier
                                    .background(Color.White, RoundedCornerShape(12.dp))
                                    .padding(12.dp)
                                    .fillMaxWidth()
                                    .clickable { expandedIngredient = !expandedIngredient }
                            )

                            DropdownMenu(
                                expanded = expandedIngredient,
                                onDismissRequest = { expandedIngredient = false }
                            ) {
                                IngredientList.forEach { ingredient ->
                                    DropdownMenuItem(
                                        text = { Text(ingredient.name) },
                                        onClick = {
                                            selectedIngredient = ingredient
                                            expandedIngredient = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        TextField(
                            value = ingredientAmount,
                            onValueChange = { ingredientAmount = it },
                            placeholder = { Text("1") },
                            modifier = Modifier.width(70.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Box(
                            modifier = Modifier
                                .background(Color.White, RoundedCornerShape(10.dp))
                                .padding(8.dp)
                        ) {
                            Text(selectedIngredient?.unit?.name ?: "GR")
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color(0xFF00C853), RoundedCornerShape(10.dp))
                                .clickable {

                                    val amount = ingredientAmount.toIntOrNull()

                                    if (selectedIngredient != null && amount != null) {

                                        ingredientsList.add(
                                            Ingredients(
                                                name = selectedIngredient!!.name,
                                                image = selectedIngredient!!.image,
                                                amount = amount,
                                                unit = selectedIngredient!!.unit
                                            )
                                        )

                                        ingredientAmount = ""
                                        selectedIngredient = null
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("✔", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    ingredientsList.forEachIndexed { index, ingredient ->

                        Box {
                            IngredientCard(ingredient)

                            Text(
                                "✕",
                                color = Color.Red,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(6.dp)
                                    .clickable {
                                        ingredientsList.removeAt(index)
                                    }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔶 STEPS
            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    "Steps",
                    color = Color.White
                )

                Spacer(modifier = Modifier.width(10.dp))

                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .background(Color(0xFFFF7A00), RoundedCornerShape(6.dp))
                        .clickable { showStepDialog = true },
                    contentAlignment = Alignment.Center
                ) {
                    Text("+", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            stepsList.forEachIndexed { index, step ->

                InstructionStepItem(
                    index = index,
                    step = step,
                    onDelete = {
                        stepsList.removeAt(index)
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔶 SAVE BUTTON
            Button(
                onClick = {
                    Toast.makeText(context, "Recipe Saved", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5A2D1B)
                )
            ) {
                Text("SAVE", color = Color.White)
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }

    // 🔥 POPUP (FIGMA STYLE)
    if (showStepDialog) {
        AlertDialog(
            onDismissRequest = { showStepDialog = false },
            containerColor = Color(0xFFF5F5F5),
            shape = RoundedCornerShape(20.dp),

            title = {
                Text(
                    text = "Add a new step",
                    color = Color(0xFFFF7A00)
                )
            },

            text = {
                Column {

                    OutlinedTextField(
                        value = stepTitle,
                        onValueChange = { stepTitle = it },
                        placeholder = { Text("Title") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = Color.LightGray
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = stepDescription,
                        onValueChange = { stepDescription = it },
                        placeholder = { Text("Insert a description here") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = Color.LightGray
                        )
                    )
                }
            },

            confirmButton = {
                Button(
                    onClick = {
                        if (stepDescription.isNotBlank()) {
                            stepsList.add(stepDescription)
                            stepTitle = ""
                            stepDescription = ""
                            showStepDialog = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),

                    shape = RoundedCornerShape(12.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5A2D1B)
                    )
                ) {
                    Text("SAVE", color = Color.White)
                }
            },

            dismissButton = {}
        )
    }
}