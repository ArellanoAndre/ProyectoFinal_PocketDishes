package islas.abril.pocketdishes

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import islas.abril.pocketdishes.screens.LoginScreen
import islas.abril.pocketdishes.screens.RecipeDetailScreen
import islas.abril.pocketdishes.screens.RegisterScreen
import islas.abril.pocketdishes.screens.homescreen
import returnRecipes

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val allRecipes = returnRecipes()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    // al logearse redirige al home
                    navController.navigate("home") {
                        // Se borra el historial para que no pueda volver al login con el boton
                        // pendiente agregar boton de logout o algo asi
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        composable("home") {
            homescreen(navController)
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("login")
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla de Detalle de receta (recibe el nombre como argumento)
        composable("detail/{recipeTitle}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("recipeTitle")
            val selectedRecipe = allRecipes.find { it.name == title }

            if (selectedRecipe != null) {
                RecipeDetailScreen(
                    recipe = selectedRecipe,
                    onBackClick = {
                        // funcion para el boton de regresar
                        navController.popBackStack()
                    }
                )
            }
        }



    }
}