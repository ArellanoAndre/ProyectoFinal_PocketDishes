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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.components.BottomNavigationMenu
import islas.abril.pocketdishes.components.combobox
import islas.abril.pocketdishes.components.headerV2
import islas.abril.pocketdishes.components.textField
import islas.abril.pocketdishes.data.enums.Units
import islas.abril.pocketdishes.data.recipeCategories
import islas.abril.pocketdishes.ui.theme.LightGreenMenu
import islas.abril.pocketdishes.ui.theme.PocketDishesTheme
import islas.abril.pocketdishes.ui.theme.backgroundOrange
import islas.abril.pocketdishes.ui.theme.gradientEnd
import islas.abril.pocketdishes.ui.theme.gradientStart
import islas.abril.pocketdishes.ui.theme.lightOrange
import islas.abril.pocketdishes.ui.theme.lightPeach
import islas.abril.pocketdishes.ui.theme.typoColorBrown
@Composable
fun addRecipeScreen(navController: NavController) {
    androidx.compose.material3.Scaffold(
        topBar = {
            headerV2()
        },
        bottomBar = {
            // LO PUSE ADENTRO DE UN BOX CON NAVIGATION BARS PADDING PARA QUE NO CHOQUE CON EL MENU DEL TELEFONO
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
                //.verticalScroll(scrollState)
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
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    textField("Name", "", {}, "")
                    textField("Description", "", {}, "")

                    Text(
                        "Upload a picture", color = typoColorBrown,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(
                            start = 10.dp,
                            top = 5.dp,
                            bottom = 10.dp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clickable {}
                            .background(backgroundOrange, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_recipe),
                            contentDescription = "Add"
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 10.dp))

                    combobox(
                        "Categories",
                        recipeCategories,
                        "",
                        {}
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Ingredients",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun addRecipePreview(){
    PocketDishesTheme {
        val navController = rememberNavController()
        addRecipeScreen(navController = navController)
    }
}