package islas.abril.pocketdishes.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import islas.abril.pocketdishes.components.BottomNavigationMenu
import islas.abril.pocketdishes.components.InstructionStepItem
import islas.abril.pocketdishes.components.IngredientCard
import islas.abril.pocketdishes.components.LoginTextField
import islas.abril.pocketdishes.components.combobox
import islas.abril.pocketdishes.components.headerV2
import islas.abril.pocketdishes.components.recipeTags
import islas.abril.pocketdishes.data.Ingredients
import islas.abril.pocketdishes.data.RecipeTags
import islas.abril.pocketdishes.data.Tag
import islas.abril.pocketdishes.data.dummies.IngredientList
import islas.abril.pocketdishes.data.enums.Units
import islas.abril.pocketdishes.data.recipeCategories
import islas.abril.pocketdishes.data.room.entities.RecipeEntity
import islas.abril.pocketdishes.ui.theme.brightIndigo
import islas.abril.pocketdishes.ui.theme.darkBrown
import islas.abril.pocketdishes.ui.theme.mainOrange
import islas.abril.pocketdishes.ui.theme.secondaryGreen
import islas.abril.pocketdishes.ui.theme.typoColorBrown
import islas.abril.pocketdishes.viewmodel.PocketDishesViewModel

@Composable
fun AddRecipeScreen(
    viewModel: PocketDishesViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val currentUser by viewModel.currentUser.collectAsState()

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            imageUri = uri
        }
    }

    var recipeName by remember { mutableStateOf("") }
    var recipeDescription by remember { mutableStateOf("") }
    var prepTime by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(recipeCategories.first()) }
    var isSecret by remember { mutableStateOf(false) }

    val selectedTags = remember { mutableStateListOf<Tag>() }
    var selectedTagName by remember { mutableStateOf("") }
    val tagNames = RecipeTags.map { it.name }

    val ingredientsList = remember { mutableStateListOf<Ingredients>() }
    var selectedIngredient by remember { mutableStateOf<Ingredients?>(null) }
    var ingredientAmount by remember { mutableStateOf("") }
    var expandedIngredient by remember { mutableStateOf(false) }
    var selectedUnit by remember { mutableStateOf(Units.GR) }
    var expandedUnit by remember { mutableStateOf(false) }

    val stepsList = remember { mutableStateListOf<String>() }
    var showStepDialog by remember { mutableStateOf(false) }
    var stepTitle by remember { mutableStateOf("") }
    var stepDescription by remember { mutableStateOf("") }

    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = { headerV2(navController) },
        bottomBar = {
            Box(modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer).fillMaxWidth()) {
                Box(modifier = Modifier.navigationBarsPadding()) {
                    BottomNavigationMenu(navController = navController)
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(colors = listOf(MaterialTheme.colorScheme.background, mainOrange)))
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 30.dp)
            ) {
                Text(
                    "Add a recipe",
                    color = mainOrange,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                ) {
                    Column(Modifier.padding(30.dp)) {
                        LoginTextField("Name", recipeName, { recipeName = it }, "Homemade Pizza")
                        Spacer(modifier = Modifier.height(10.dp))
                        LoginTextField(
                            "Description",
                            recipeDescription,
                            { recipeDescription = it },
                            "Write a description"
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            "Upload a picture",
                            color = MaterialTheme.colorScheme.outline,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
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
                                Box(
                                    modifier = Modifier.fillMaxSize().background(Color(0xFFFFD6A0)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Tap to select a photo", color = Color.Gray)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(Modifier.weight(1f)) {
                                Text(
                                    "Prep time (min)",
                                    color = MaterialTheme.colorScheme.outline,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                LoginTextField("", prepTime, { prepTime = it }, "30")
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(Modifier.weight(1f)) {
                                Text(
                                    "Tags",
                                    color = MaterialTheme.colorScheme.outline,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                combobox("", tagNames, selectedTagName) { name ->
                                    selectedTagName = name
                                    RecipeTags.find { it.name == name }
                                        ?.let { if (!selectedTags.contains(it)) selectedTags.add(it) }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        recipeTags(selectedTags)
                        Spacer(modifier = Modifier.height(12.dp))
                        combobox(
                            "Select category",
                            recipeCategories,
                            selectedCategory
                        ) { selectedCategory = it }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Secret recipe", color = brightIndigo)
                            Switch(checked = isSecret, onCheckedChange = { isSecret = it })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Ingredients",
                    color = typoColorBrown,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(Modifier.weight(1f)) {
                                Text(
                                    text = selectedIngredient?.name ?: "Select ingredient",
                                    color=typoColorBrown,
                                    modifier = Modifier.fillMaxWidth().height(52.dp)
                                        .background(Color(0xFFF5F5F5), RoundedCornerShape(10.dp))
                                        .clickable { expandedIngredient = !expandedIngredient }
                                        .padding(horizontal = 12.dp, vertical = 15.dp),
                                    fontSize = 14.sp, maxLines = 1
                                )
                                DropdownMenu(
                                    expanded = expandedIngredient,
                                    onDismissRequest = { expandedIngredient = false }) {
                                    IngredientList.forEach { ingredient ->
                                        DropdownMenuItem(
                                            text = { Text(ingredient.name) },
                                            onClick = {
                                                selectedIngredient = ingredient
                                                selectedUnit = ingredient.unit
                                                expandedIngredient = false
                                            })
                                    }
                                }
                            }

                            LoginTextField(
                                label = "",
                                value = ingredientAmount,
                                onValueChange = { ingredientAmount = it },
                                placeholder = "0",
                                modifier = Modifier.width(65.dp).height(52.dp)
                            )

                            Box {
                                Text(
                                    text = selectedUnit.name,
                                    modifier = Modifier.height(52.dp)
                                        .background(Color(0xFFF5F5F5), RoundedCornerShape(10.dp))
                                        .clickable { expandedUnit = true }
                                        .padding(horizontal = 12.dp, vertical = 15.dp),
                                    fontSize = 14.sp, fontWeight = FontWeight.Bold
                                )
                                DropdownMenu(
                                    expanded = expandedUnit,
                                    onDismissRequest = { expandedUnit = false }) {
                                    Units.entries.forEach { unit ->
                                        DropdownMenuItem(
                                            text = { Text(unit.name, color= MaterialTheme.colorScheme.outline) },
                                            onClick = { selectedUnit = unit; expandedUnit = false })
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier.size(52.dp).clip(RoundedCornerShape(10.dp))
                                    .background(secondaryGreen)
                                    .clickable {
                                        val amount = ingredientAmount.toIntOrNull()
                                        if (selectedIngredient != null && amount != null && amount > 0) {
                                            ingredientsList.add(
                                                Ingredients(
                                                    selectedIngredient!!.name,
                                                    selectedIngredient!!.image,
                                                    amount,
                                                    selectedUnit
                                                )
                                            )
                                            ingredientAmount = ""; selectedIngredient =
                                                null; selectedUnit = Units.GR
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("✔", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }

                        if (ingredientsList.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            ingredientsList.forEachIndexed { index, ingredient ->
                                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                                    IngredientCard(ingredient)
                                    Box(
                                        modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                                            .size(24.dp).background(
                                                Color.Red.copy(alpha = 0.8f),
                                                RoundedCornerShape(12.dp)
                                            )
                                            .clickable { ingredientsList.removeAt(index) },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "✕",
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Steps",
                        color = MaterialTheme.colorScheme.outline,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier.size(26.dp)
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
                        onDelete = { stepsList.removeAt(index) })
                }

                Spacer(modifier = Modifier.height(20.dp))
                if (showError) Text(
                    errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(
                    onClick = {
                        val userId = currentUser?.idUser
                        when {
                            userId == null -> {
                                errorMessage = "No hay sesión activa."; showError =
                                    true; return@Button
                            }

                            recipeName.isBlank() -> {
                                errorMessage = "El nombre es obligatorio."; showError =
                                    true; return@Button
                            }

                            stepsList.isEmpty() -> {
                                errorMessage = "Agrega al menos un paso."; showError =
                                    true; return@Button
                            }

                            else -> showError = false
                        }

                        val recipe = RecipeEntity(

                            name = recipeName.trim(),

                            description = recipeDescription.trim(),

                            author = userId!!,

                            source = "",

                            image = imageUri?.toString() ?: "", // URI de galeria o vacío

                            tags = selectedTags.map { it.name },

                            category = selectedCategory,

                            isSecret = isSecret,

                            isPublic = !isSecret,

                            prepTime = prepTime.toIntOrNull() ?: 0

                        )
                        val ingredientsForDB = ingredientsList.map { ing ->
                            val resName = try {
                                context.resources.getResourceEntryName(ing.image)
                            } catch (e: Exception) {
                                ""
                            }
                            val imgName = if (resName == "cheese" && !ing.name.equals(
                                    "cheese",
                                    true
                                )
                            ) ing.name.lowercase().replace(" ", "_") else resName
                            Triple(ing.name, imgName, Pair(ing.amount.toFloat(), ing.unit))
                        }
                        viewModel.insertRecipeComplete(recipe, stepsList.toList(), ingredientsForDB)
                        Toast.makeText(context, "¡Receta guardada!", Toast.LENGTH_SHORT).show()
                        navController.navigate("home") {
                            popUpTo("add_recipe") {
                                inclusive = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = darkBrown),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text("Save recipe", fontSize = 18.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.height(100.dp))
            }

            }

        }

        if (showStepDialog) {
            AlertDialog(
                onDismissRequest = { showStepDialog = false },
                containerColor = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(20.dp),
                title = { Text("Add a new step", color = mainOrange) },
                text = {
                    Column {
                        OutlinedTextField(value = stepTitle, onValueChange = { stepTitle = it }, placeholder = { Text("Title (optional)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(value = stepDescription, onValueChange = { stepDescription = it }, placeholder = { Text("Insert a description here") }, modifier = Modifier.fillMaxWidth().height(100.dp), shape = RoundedCornerShape(12.dp))
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (stepDescription.isNotBlank()) {
                                stepsList.add(if (stepTitle.isNotBlank()) "$stepTitle|$stepDescription" else stepDescription)
                                stepTitle = ""; stepDescription = ""; showStepDialog = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(55.dp).padding(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A2D1B)),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("SAVE", fontSize = 18.sp, color = Color.White) }
                }
            )
        }
    }