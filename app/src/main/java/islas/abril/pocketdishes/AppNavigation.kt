package islas.abril.pocketdishes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import islas.abril.pocketdishes.data.room.toRecipe
import islas.abril.pocketdishes.screens.ExploreScreen
import islas.abril.pocketdishes.screens.LoginScreen
import islas.abril.pocketdishes.screens.ProfileScreen
import islas.abril.pocketdishes.screens.RecipeDetailScreen
import islas.abril.pocketdishes.screens.AddRecipeScreen
import islas.abril.pocketdishes.screens.RegisterScreen
import islas.abril.pocketdishes.screens.SecretRecipeScreen
import islas.abril.pocketdishes.screens.homescreen
import kotlinx.coroutines.launch
import returnRecipes

@Composable
fun AppNavigation(
    viewModel: islas.abril.pocketdishes.viewmodel.PocketDishesViewModel
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val allRecipes = returnRecipes()

   // observamos al el id de usuario
    val userId by viewModel.loggedUserId.collectAsState()

    if (userId == -2) {
        return
    }

    val publicRecipesEntities by viewModel.publicRecipes.collectAsState()
    val mappedPublicRecipes = remember(publicRecipesEntities) {
        publicRecipesEntities.map { it.toRecipe(context) }
    }

    val userRecipesEntities by viewModel.userRecipes.collectAsState()
    val mappedUserRecipes = remember(userRecipesEntities) {
        userRecipesEntities.map { it.toRecipe(context) }
    }

    NavHost(
        navController = navController,
        startDestination = if (userId == -1) "login" else "home"
    ) {
        composable("login") {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = { },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        // --- HOME ---
        composable("home") {
            homescreen(navController = navController, viewModel = viewModel)
        }

        // --- REGISTER ---
        composable("register") {
            RegisterScreen(
                viewModel = viewModel,
                onRegisterSuccess = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // --- PROFILE ---
        composable("profile") {
            ProfileScreen(
                viewModel = viewModel,
                navController = navController,
                onLogout = {
                    scope.launch {
                        viewModel.logout()
                    }
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // --- SECRET RECIPES ---
        composable("secretrecipes") {
            SecretRecipeScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }

        // --- EXPLORE ---
        composable("explore") {
            ExploreScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        // --- ADD RECIPE ---
        composable("add_recipe") {
            AddRecipeScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        // --- DETAIL ---
        composable("detail/{recipeTitle}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("recipeTitle")

            // busca la receta segun el titulo
            val selectedRecipe = allRecipes.find { it.name == title }
                ?: mappedPublicRecipes.find { it.name == title }
                ?: mappedUserRecipes.find { it.name == title }

            if (selectedRecipe != null) {
                RecipeDetailScreen(
                    recipe = selectedRecipe,
                    viewModel = viewModel,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    navController = navController
                )
            }
        }
    }
}
