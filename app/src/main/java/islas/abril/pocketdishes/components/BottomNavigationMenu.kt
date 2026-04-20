package islas.abril.pocketdishes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.ui.theme.LightGreenMenu
import islas.abril.pocketdishes.ui.theme.secondaryGreen

@Composable
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

        // 🔥 BOTÓN ADD RECIPE (CORREGIDO)
        Row(
            modifier = Modifier.clickable {
                navigateTo(navController, "add_recipe") // 🔥 AQUÍ ESTABA EL ERROR
            },
            verticalAlignment = Alignment.CenterVertically
        ) {

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
                text = "New recipe",
                color = secondaryGreen,
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.width(60.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_explore),
            contentDescription = "Explore recipes",
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
        )
    }
}

/**
 * Navegación limpia
 */
private fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
    }
}