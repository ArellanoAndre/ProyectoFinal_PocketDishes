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
import androidx.fragment.app.FragmentActivity
import islas.abril.pocketdishes.data.datastore.UserPreferences

class MainActivity : FragmentActivity() {

    private val viewModel: PocketDishesViewModel by viewModels {
        PocketDishesViewModel.factory(
            PocketDishesRepository(
                AppDatabase.getDatabase(this)
            ),
            UserPreferences(this)
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