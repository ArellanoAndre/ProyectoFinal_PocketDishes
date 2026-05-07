package islas.abril.pocketdishes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
<<<<<<< HEAD
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
=======
import androidx.compose.foundation.layout.*
>>>>>>> AddRecipe_Arell
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
<<<<<<< HEAD
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
=======
>>>>>>> AddRecipe_Arell
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.ui.theme.LightGreenMenu
import islas.abril.pocketdishes.ui.theme.PocketDishesTheme
import islas.abril.pocketdishes.ui.theme.secondaryGreen

@Composable
<<<<<<< HEAD
fun BottomNavigationMenu(modifier: Modifier = Modifier, navController: NavController){
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(top=10.dp, start = 30.dp, end = 30.dp)
            ,horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
=======
fun BottomNavigationMenu(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(LightGreenMenu)
            .padding(top = 35.dp, start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
>>>>>>> AddRecipe_Arell

        // 🔥 BOTÓN ADD RECIPE (CORREGIDO)
        Row(
            modifier = Modifier.clickable {
                navigateTo(navController, "add_recipe") // 🔥 AQUÍ ESTABA EL ERROR
            },
            verticalAlignment = Alignment.CenterVertically
<<<<<<< HEAD
        ){
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(5.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_recipe),
                        contentDescription = "Agregar receta",
                        tint = secondaryGreen,
                        modifier = Modifier.size(27.dp)

                    )
                }
=======
        ) {
>>>>>>> AddRecipe_Arell

            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = Color(0xFFB3DAB0),
                        shape = RoundedCornerShape(5.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_recipe),
                    contentDescription = "Agregar receta",
                    tint = secondaryGreen,
                    modifier = Modifier.size(27.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(
<<<<<<< HEAD
                text="New recipe",
                color= MaterialTheme.colorScheme.onSecondaryContainer,
=======
                text = "New recipe",
                color = secondaryGreen,
>>>>>>> AddRecipe_Arell
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        NavItem(
            iconRes = R.drawable.ic_explore,
            contentDescription = "Explore recipes",
<<<<<<< HEAD
            route = "explore",
            currentRoute = currentRoute,
            navController = navController
        )
        Spacer(Modifier.padding(start = 10.dp))
        NavItem(
            iconRes = R.drawable.home_24px,
            contentDescription = "Home button",
            route = "home",
            currentRoute = currentRoute,
            navController = navController
        )
        Spacer(Modifier.padding(start = 10.dp))

        NavItem(
            iconRes = R.drawable.ic_profile,
            contentDescription = "User profile",
            route = "profile",
            currentRoute = currentRoute,
            navController = navController
=======
            tint = secondaryGreen,
            modifier = Modifier
                .size(35.dp)
                .clickable { navigateTo(navController, "explore") }
        )

        Icon(
            painter = painterResource(id = R.drawable.home_24px),
            contentDescription = "Home button",
            tint = secondaryGreen,
            modifier = Modifier
                .size(35.dp)
                .clickable { navigateTo(navController, "home") }
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "User profile",
            tint = secondaryGreen,
            modifier = Modifier
                .size(35.dp)
                .clickable { navigateTo(navController, "profile") }
>>>>>>> AddRecipe_Arell
        )
    }
}
@Composable
fun NavigationItem(
    iconRes: Int,
    contentDescription: String,
    route: String,
    currentRoute: String?,
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            tint = if (currentRoute == route)
                secondaryGreen
            else
                MaterialTheme.colorScheme.onSecondaryContainer,

            modifier = Modifier
                .size(35.dp)
                .clickable {
                    navigateTo(navController, route)
                }
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (currentRoute == route) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(
                        secondaryGreen,
                        shape = RoundedCornerShape(50)
                    )
            )
        }
    }
}

<<<<<<< HEAD
=======
/**
 * Navegación limpia
 */
>>>>>>> AddRecipe_Arell
private fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
    }
<<<<<<< HEAD
}

@Preview(showBackground = true)
@Composable
fun previewMenu(){
    PocketDishesTheme() {
        val navController = rememberNavController()
        BottomNavigationMenu(navController = navController)
    }
}

@Composable
fun NavItem(
    iconRes: Int,
    contentDescription: String,
    route: String,
    currentRoute: String?,
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            tint = if (currentRoute == route)
                secondaryGreen
            else
                MaterialTheme.colorScheme.onSecondaryContainer,

            modifier = Modifier
                .size(40.dp)
                .clickable {
                    navigateTo(navController, route)
                }
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (currentRoute == route) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        secondaryGreen,
                        shape = RoundedCornerShape(50)
                    )
            )
        }
    }
=======
>>>>>>> AddRecipe_Arell
}