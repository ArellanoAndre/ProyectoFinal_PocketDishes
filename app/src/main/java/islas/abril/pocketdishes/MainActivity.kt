package islas.abril.pocketdishes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import islas.abril.pocketdishes.data.room.AppDatabase
import islas.abril.pocketdishes.data.room.PocketDishesRepository
import islas.abril.pocketdishes.ui.theme.PocketDishesTheme
import islas.abril.pocketdishes.viewmodel.PocketDishesViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: PocketDishesViewModel by viewModels {
        PocketDishesViewModel.factory(
            PocketDishesRepository(AppDatabase.getDatabase(this))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PocketDishesTheme {
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}