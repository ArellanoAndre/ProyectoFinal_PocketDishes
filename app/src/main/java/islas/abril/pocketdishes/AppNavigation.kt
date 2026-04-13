package islas.abril.pocketdishes

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import islas.abril.pocketdishes.data.dummies.returnProfile
import islas.abril.pocketdishes.screens.ExploreScreen
import islas.abril.pocketdishes.screens.LoginScreen
import islas.abril.pocketdishes.screens.ProfileScreen
import islas.abril.pocketdishes.screens.RecipeDetailScreen
import islas.abril.pocketdishes.screens.RegisterScreen
import islas.abril.pocketdishes.screens.homescreen
import returnRecipes

@Composable
fun AppNavigation(
) {
    val navController = rememberNavController()
    val allRecipes = returnRecipes()

    NavHost(navController = navController, startDestination = "login") {
        // --- LOGIN ---
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        // --- HOME ---
        composable("home") {
            homescreen(navController)
        }

        // --- REGISTER ---
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

        // --- PROFILE ---
        composable("profile") {
            // Obtenemos los datos mock
            val userProfile = returnProfile()

            ProfileScreen(
                profile = userProfile,
                navController = navController,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // --- EXPLORE ---
        composable("explore") {
            ExploreScreen(
                recipeList = allRecipes,
                navController = navController
            )
        }

        // --- DETAIL ---
        composable("detail/{recipeTitle}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("recipeTitle")
            val selectedRecipe = allRecipes.find { it.name == title }

            if (selectedRecipe != null) {
                RecipeDetailScreen(
                    recipe = selectedRecipe,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    navController = navController
                )
            }
        }
    }
}
