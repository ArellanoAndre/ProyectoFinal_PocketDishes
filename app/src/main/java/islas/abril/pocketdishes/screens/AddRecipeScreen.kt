package islas.abril.pocketdishes.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import islas.abril.pocketdishes.components.textField
import islas.abril.pocketdishes.data.Ingredients
import islas.abril.pocketdishes.data.RecipeTags
import islas.abril.pocketdishes.data.Tag
import islas.abril.pocketdishes.data.dummies.IngredientList
import islas.abril.pocketdishes.data.enums.Units
import islas.abril.pocketdishes.data.recipeCategories
import islas.abril.pocketdishes.data.room.entities.RecipeEntity
import islas.abril.pocketdishes.ui.theme.mainOrange
import islas.abril.pocketdishes.ui.theme.typoColorBrown
import islas.abril.pocketdishes.viewmodel.PocketDishesViewModel

@Composable
fun AddRecipeScreen(
    viewModel: PocketDishesViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val currentUser by viewModel.currentUser.collectAsState()

    // Imagen
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            context.contentResolver.takePersistableUriPermission(
                uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            imageUri = uri
        }
    }

    // Informacion básica
    var recipeName        by remember { mutableStateOf("") }
    var recipeDescription by remember { mutableStateOf("") }
    var prepTime          by remember { mutableStateOf("") }
    var selectedCategory  by remember { mutableStateOf(recipeCategories.first()) }
    var isSecret          by remember { mutableStateOf(false) }

    // Tags
    val selectedTags = remember { mutableStateListOf<Tag>() }
    var selectedTagName by remember { mutableStateOf("") }
    val tagNames = RecipeTags.map { it.name }

    // Ingredientes
    val ingredientsList      = remember { mutableStateListOf<Ingredients>() }
    var selectedIngredient   by remember { mutableStateOf<Ingredients?>(null) }
    var ingredientAmount     by remember { mutableStateOf("") }
    var expandedIngredient   by remember { mutableStateOf(false) }
    var selectedUnit         by remember { mutableStateOf(Units.GR) }
    var expandedUnit         by remember { mutableStateOf(false) }

    // Pasos
    val stepsList        = remember { mutableStateListOf<String>() }
    var showStepDialog   by remember { mutableStateOf(false) }
    var stepTitle        by remember { mutableStateOf("") }
    var stepDescription  by remember { mutableStateOf("") }

    // Validacion
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar    = { headerV2(navController) },
        bottomBar = {
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
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(brush=Brush.verticalGradient(colors=listOf(MaterialTheme.colorScheme.background,mainOrange)))
                .padding(16.dp)
        ) {

            Text(
                "Add a recipe",
                color = mainOrange,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Card principal
            Card(
                shape  = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)

            ) {
                Column(Modifier.padding(16.dp)) {

                    LoginTextField("Name", recipeName, { recipeName = it }, "Homemade Pizza")
                    LoginTextField("Description", recipeDescription, { recipeDescription = it }, "Write a description")

                    Spacer(modifier = Modifier.height(12.dp))

                    // Imagen
                    Text("Upload a picture", color = MaterialTheme.colorScheme.outline)
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
                                model          = imageUri,
                                contentDescription = null,
                                modifier       = Modifier.fillMaxSize(),
                                contentScale   = ContentScale.Crop
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0xFFFFD6A0), RoundedCornerShape(16.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Tap to select a photo", color = Color.Gray)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Prep time + Tags
                    Row {
                        Column(Modifier.weight(1f)) {
                            Text(
                                "Prep time (min)",
                                color = MaterialTheme.colorScheme.outline,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 5.dp)
                            )
                            LoginTextField("", prepTime, { prepTime = it }, "30")
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = "",
                                color = MaterialTheme.colorScheme.outline,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 5.dp))
                            combobox("Select tag", tagNames, selectedTagName) { name ->
                                selectedTagName = name
                                val tag = RecipeTags.find { it.name == name }
                                if (tag != null && !selectedTags.contains(tag)) {
                                    selectedTags.add(tag)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    recipeTags(selectedTags)

                    Spacer(modifier = Modifier.height(12.dp))

                    // Categoría
                    Text(
                        text = "Category",
                        color = MaterialTheme.colorScheme.outline,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    combobox("Select category", recipeCategories, selectedCategory) {
                        selectedCategory = it
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Receta secreta
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Secret recipe", color = Color.Gray)
                        Switch(
                            checked = isSecret,
                            onCheckedChange = { isSecret = it }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Ingredientes
            Text(
                text = "Ingredients",
                color = MaterialTheme.colorScheme.outline,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Card(
                shape  = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(Color(0xFFFFA85A))
            ) {
                Column(Modifier.padding(12.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        // Selector de ingrediente
                        Box(Modifier.weight(1f)) {
                            Text(
                                selectedIngredient?.name ?: "Select ingredient",
                                modifier = Modifier
                                    .background(Color.White, RoundedCornerShape(12.dp))
                                    .padding(12.dp)
                                    .fillMaxWidth()
                                    .clickable { expandedIngredient = !expandedIngredient }
                            )
                            DropdownMenu(
                                expanded  = expandedIngredient,
                                onDismissRequest = { expandedIngredient = false }
                            ) {
                                IngredientList.forEach { ingredient ->
                                    DropdownMenuItem(
                                        text    = { Text(ingredient.name) },
                                        onClick = {
                                            selectedIngredient = ingredient
                                            selectedUnit       = ingredient.unit  // unidad por defecto del ingrediente
                                            expandedIngredient = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        // Cantidad
                        LoginTextField(
                            label = "",
                            value = ingredientAmount,
                            onValueChange = { ingredientAmount = it },
                            placeholder = "0",
                            isPassword = false,
                            enabled = true,
                            modifier = Modifier.width(70.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        // Unidad (dropdown con todos los valores de Units)
                        Box {
                            Box(
                                modifier = Modifier
                                    .background(Color.White, RoundedCornerShape(10.dp))
                                    .padding(8.dp)
                                    .clickable { expandedUnit = true }
                            ) {
                                Text(selectedUnit.name)
                            }
                            DropdownMenu(
                                expanded = expandedUnit,
                                onDismissRequest = { expandedUnit = false }
                            ) {
                                Units.entries.forEach { unit ->
                                    DropdownMenuItem(
                                        text    = { Text(unit.name) },
                                        onClick = {
                                            selectedUnit = unit
                                            expandedUnit = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        // Boton añadir
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                                .clickable {
                                    val amount = ingredientAmount.toIntOrNull()
                                    if (selectedIngredient != null && amount != null && amount > 0) {
                                        ingredientsList.add(
                                            Ingredients(
                                                name   = selectedIngredient!!.name,
                                                image  = selectedIngredient!!.image,
                                                amount = amount,
                                                unit   = selectedUnit          // unidad elegida por el usuario
                                            )
                                        )
                                        ingredientAmount   = ""
                                        selectedIngredient = null
                                        selectedUnit       = Units.GR          // resetear unidad
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
                                color    = Color.Red,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(6.dp)
                                    .clickable { ingredientsList.removeAt(index) }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Pasos
            Row(verticalAlignment = Alignment.CenterVertically) {
               Text( text = "Steps",
                color = MaterialTheme.colorScheme.outline,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 5.dp)
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
                    index    = index,
                    step     = step,
                    onDelete = { stepsList.removeAt(index) }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Boton guardar
            if (showError) {
                Text(errorMessage, color = Color.Red, modifier = Modifier.padding(bottom = 8.dp))
            }

            Button(
                onClick = {
                    // Validaciones
                    val userId = currentUser?.idUser
                    when {
                        userId == null -> {
                            errorMessage = "No hay sesión activa."
                            showError = true
                            return@Button
                        }
                        recipeName.isBlank() -> {
                            errorMessage = "El nombre de la receta es obligatorio."
                            showError = true
                            return@Button
                        }
                        stepsList.isEmpty() -> {
                            errorMessage = "Agrega al menos un paso."
                            showError = true
                            return@Button
                        }
                        else -> showError = false
                    }

                    val recipe = RecipeEntity(
                        name        = recipeName.trim(),
                        description = recipeDescription.trim(),
                        author      = userId!!,
                        source      = "",
                        image       = imageUri?.toString() ?: "",   // URI de galeria o vacío
                        tags        = selectedTags.map { it.name },
                        category    = selectedCategory,
                        isSecret    = isSecret,
                        isPublic    = !isSecret,
                        prepTime    = prepTime.toIntOrNull() ?: 0
                    )

                    // Convertir ingredientes al formato que espera insertRecipeComplete.
                    // Para ingredientes CON drawable propio: getResourceEntryName devuelve el
                    // nombre exacto del archivo (ej. "cheeseburger", "currypaste").
                    // Para ingredientes SIN drawable (usan R.drawable.cheese de placeholder):
                    // se deriva el nombre del drawable del nombre del ingrediente en lowercase+underscore
                    // (ej. "Dark Chocolate" → "dark_chocolate") para que la BD ya tenga el nombre
                    // correcto y funcione automáticamente cuando se añada el archivo de imagen.
                    val ingredientsForDB = ingredientsList.map { ing ->
                        val resourceName = try {
                            context.resources.getResourceEntryName(ing.image)
                        } catch (e: Exception) { "" }
                        val imageName = if (resourceName == "cheese" &&
                            !ing.name.equals("cheese", ignoreCase = true)) {
                            ing.name.lowercase().replace(" ", "_")
                        } else {
                            resourceName
                        }
                        Triple(ing.name, imageName, Pair(ing.amount.toFloat(), ing.unit))
                    }

                    viewModel.insertRecipeComplete(
                        recipe      = recipe,
                        steps       = stepsList.toList(),
                        ingredients = ingredientsForDB
                    )

                    Toast.makeText(context, "¡Receta guardada!", Toast.LENGTH_SHORT).show()
                    navController.navigate("home") {
                        popUpTo("add_recipe") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors   = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A2D1B))
            ) {
                Text("SAVE", color = Color.White)
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }

    // Dialogo agregar paso
    if (showStepDialog) {
        AlertDialog(
            onDismissRequest  = { showStepDialog = false },
            containerColor    = Color(0xFFF5F5F5),
            shape             = RoundedCornerShape(20.dp),
            title = {
                Text("Add a new step", color = mainOrange)
            },
            text = {
                Column {
                    OutlinedTextField(
                        value         = stepTitle,
                        onValueChange = { stepTitle = it },
                        placeholder   = { Text("Title (optional)") },
                        modifier      = Modifier.fillMaxWidth(),
                        shape         = RoundedCornerShape(12.dp),
                        colors        = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = typoColorBrown,
                            focusedBorderColor   = Color.LightGray
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value         = stepDescription,
                        onValueChange = { stepDescription = it },
                        placeholder   = { Text("Insert a description here") },
                        modifier      = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape         = RoundedCornerShape(12.dp),
                        colors        = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor   = Color.LightGray
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (stepDescription.isNotBlank()) {

                            val step =
                                if (stepTitle.isNotBlank())
                                    "$stepTitle|$stepDescription"
                                else stepDescription

                            stepsList.add(step)

                            stepTitle = ""
                            stepDescription = ""
                            showStepDialog = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5A2D1B)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "SAVE",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        )
    }
}
