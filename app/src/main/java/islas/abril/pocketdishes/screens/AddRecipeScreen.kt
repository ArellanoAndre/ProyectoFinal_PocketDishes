package islas.abril.pocketdishes.screens

import android.net.Uri
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.components.*
import islas.abril.pocketdishes.data.*
import islas.abril.pocketdishes.data.dummies.IngredientList
import islas.abril.pocketdishes.data.enums.Units
import islas.abril.pocketdishes.ui.theme.*

@Composable
fun AddRecipeScreen(navController: NavController) {

    // Imagen seleccionada por el usuario (null si no hay)
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Lanzador para abrir la galería y recibir la imagen
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> imageUri = uri }

    // Datos básicos de la receta
    var recipeName by remember { mutableStateOf("") }
    var recipeDescription by remember { mutableStateOf("") }
    var prepTime by remember { mutableStateOf("") }

    // Tags seleccionados (lista mutable para agregar/remover)
    val selectedTags = remember { mutableStateListOf<Tag>() }
    var selectedTagName by remember { mutableStateOf("") }

    // Lista de nombres de tags disponibles para el combo box
    val tagNames = RecipeTags.map { it.name }

    // Lista de ingredientes agregados a la receta
    val ingredientsList = remember { mutableStateListOf<Ingredients>() }
    var selectedIngredient by remember { mutableStateOf<Ingredients?>(null) }  // Ingrediente temporal antes de agregar
    var ingredientAmount by remember { mutableStateOf("") }  // Cantidad como texto (se valida después)
    var expandedIngredient by remember { mutableStateOf(false) }  // Controla si el dropdown está abierto

    Scaffold(
        topBar = { headerV2() },
        bottomBar = { BottomNavigationMenu(navController = navController) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())  // Permite scroll si el contenido es muy largo
                .background(
                    Brush.verticalGradient(
                        listOf(gradientStart, gradientEnd)
                    )
                )
                .padding(20.dp)
        ) {

            Text("Add a recipe", color = Color(0xFFFF7A00))

            Spacer(modifier = Modifier.height(16.dp))

            // Card principal con nombre, descripción, imagen y tags
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(Color(0xFFFFE6CF))
            ) {

                Column(Modifier.padding(16.dp)) {

                    textField("Name", recipeName, { recipeName = it }, "Homemade Pizza")
                    textField("Description", recipeDescription, { recipeDescription = it }, "Write a description")

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Upload a picture")

                    // Caja clickeable que abre la galería y muestra la imagen seleccionada o una por defecto
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { launcher.launch("image/*") }
                    ) {

                        if (imageUri != null) {
                            // Coil carga la imagen desde la URI
                            AsyncImage(
                                model = imageUri,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            // Imagen por defecto mientras no se sube ninguna
                            Image(
                                painter = painterResource(R.drawable.pizza),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row {
                        // Columna izquierda: tiempo de preparación
                        Column(Modifier.weight(1f)) {
                            Text("Prep time")
                            textField("", prepTime, { prepTime = it }, "30 min")
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        // Columna derecha: selector de tags
                        Column(Modifier.weight(1f)) {
                            Text("Tags")

                            combobox("Select tag", tagNames, selectedTagName) {
                                selectedTagName = it

                                // Busca el tag completo por su nombre
                                val tag = RecipeTags.find { tag ->
                                    tag.name == selectedTagName
                                }

                                // Evita duplicados antes de agregar
                                if (tag != null && !selectedTags.contains(tag)) {
                                    selectedTags.add(tag)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Muestra los tags seleccionados como "chips"
                    recipeTags(selectedTags)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Sección de ingredientes
            Text("Ingredients", color = Color.White)

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(Color(0xFFFF9F4A))
            ) {

                Column(Modifier.padding(12.dp)) {

                    // Fila para seleccionar ingrediente, cantidad y agregar
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        // Dropdown para elegir ingrediente
                        Box(Modifier.weight(1f)) {

                            Text(
                                selectedIngredient?.name ?: "Select",  // Muestra "Select" si no hay nada seleccionado
                                modifier = Modifier
                                    .background(Color.White, RoundedCornerShape(12.dp))
                                    .padding(12.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        expandedIngredient = !expandedIngredient // ✅ FIX
                                    }
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

                        // Campo para la cantidad (texto, se valida después)
                        TextField(
                            value = ingredientAmount,
                            onValueChange = {
                                ingredientAmount = it
                                expandedIngredient = false // ✅ FIX
                            },
                            placeholder = { Text("1") },
                            modifier = Modifier.width(70.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        // Muestra la unidad del ingrediente seleccionado (GR, KG, ML, etc.)
                        Box(
                            modifier = Modifier
                                .background(Color.White, RoundedCornerShape(10.dp))
                                .padding(8.dp)
                        ) {
                            Text(selectedIngredient?.unit?.name ?: "GR")
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        // Botón verde para agregar ingrediente a la lista
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.Green, RoundedCornerShape(10.dp))
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

                    Spacer(modifier = Modifier.height(10.dp))

                    // Lista de ingredientes ya agregados, cada uno con una "x" para eliminar
                    ingredientsList.forEachIndexed { index, ingredient ->

                        Box {
                            IngredientCard(ingredient)

                            Text(
                                "x",
                                color = Color.Red,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .clickable {
                                        ingredientsList.removeAt(index)
                                    }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}