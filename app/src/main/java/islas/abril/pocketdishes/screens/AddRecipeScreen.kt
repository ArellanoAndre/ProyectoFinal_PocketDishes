package islas.abril.pocketdishes.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import coil.compose.rememberAsyncImagePainter
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

    // 🔥 NUEVO: imagen seleccionada
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    var recipeName by remember { mutableStateOf("") }
    var recipeDescription by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var prepTime by remember { mutableStateOf("") }

    val selectedTags = remember { mutableStateListOf<Tag>() }
    var selectedTagName by remember { mutableStateOf("") }

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
                .verticalScroll(rememberScrollState())
                .background(
                    Brush.verticalGradient(
                        colors = listOf(gradientStart, gradientEnd)
                    )
                )
                .padding(20.dp)
        ) {

            Text(
                text = "Add a recipe",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE6CF))
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

                    // 🔥 UPLOAD IMAGE
                    Text("Upload a picture", fontWeight = FontWeight.SemiBold)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .clickable {
                                launcher.launch("image/*") // 🔥 abre galería
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        if (imageUri != null) {
                            // 🔥 IMAGEN DEL USUARIO
                            Image(
                                painter = rememberAsyncImagePainter(imageUri),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            // 🔥 IMAGEN DEFAULT
                            Image(
                                painter = painterResource(id = R.drawable.pizza),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            Text(
                                "+",
                                color = Color.White,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 🔹 PREP + TAGS
                    Row {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Prep time")
                            textField("", prepTime, { prepTime = it }, "30 min")
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text("Tags")

                            combobox(
                                "Select tag",
                                tagNames,
                                selectedTagName
                            ) {
                                selectedTagName = ""

                                val tag = RecipeTags.find { t -> t.name == it }
                                if (tag != null && !selectedTags.contains(tag)) {
                                    selectedTags.add(tag)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 🔹 TAG CHIPS
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

                                Spacer(modifier = Modifier.width(4.dp))

                                Text("x",
                                    color = Color.White,
                                    modifier = Modifier.clickable {
                                        selectedTags.remove(tag)
                                    })
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}