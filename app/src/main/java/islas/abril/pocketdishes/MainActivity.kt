package islas.abril.pocketdishes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import islas.abril.pocketdishes.auth.LoginScreen
import islas.abril.pocketdishes.auth.RegisterScreen
import islas.abril.pocketdishes.auth.UserProfileScreen
import islas.abril.pocketdishes.ui.theme.PocketDishesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PocketDishesTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun PocketDishesApp() {

}