package islas.abril.pocketdishes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import islas.abril.pocketdishes.ui.theme.PocketDishesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PocketDishesTheme {

                // 🔥 USAR TU NAVEGACIÓN REAL
                AppNavigation()

            }
        }
    }
}