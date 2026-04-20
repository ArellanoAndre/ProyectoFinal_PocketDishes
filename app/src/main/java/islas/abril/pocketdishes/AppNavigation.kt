package islas.abril.pocketdishes

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import islas.abril.pocketdishes.data.dummies.returnProfile
import islas.abril.pocketdishes.screens.*
import returnRecipes

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val allRecipes = returnRecipes()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
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

        composable("profile") {
            val userProfile = returnProfile()

            ProfileScreen(
                profile = userProfile,
                navController = navController,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable("explore") {
            ExploreScreen(
                recipeList = allRecipes,
                navController = navController
            )
        }

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

        // 🔥 ESTA ES LA CLAVE
        composable("add_recipe") {
            AddRecipeScreen(navController = navController)
        }
    }
}