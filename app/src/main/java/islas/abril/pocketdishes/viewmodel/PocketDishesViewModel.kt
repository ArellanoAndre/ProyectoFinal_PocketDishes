package islas.abril.pocketdishes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import islas.abril.pocketdishes.data.room.PocketDishesRepository
import islas.abril.pocketdishes.data.room.entities.IngredientRecipeEntity
import islas.abril.pocketdishes.data.room.entities.RecipeEntity
import islas.abril.pocketdishes.data.room.entities.RecipeStepEntity
import islas.abril.pocketdishes.data.room.entities.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PocketDishesViewModel(private val repository: PocketDishesRepository) : ViewModel() {

    init {
        viewModelScope.launch { insertarDatosMock() }
    }

    // Usuario actual

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    // Recetas publicas

    // si ninguna pantalla lo observa durante 5 segundos se detiene
    val publicRecipes: StateFlow<List<RecipeEntity>> = repository.getPublicRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Recetas del usuario logueado

    private val _userRecipes = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val userRecipes: StateFlow<List<RecipeEntity>> = _userRecipes.asStateFlow()

    private val _favoriteRecipes = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val favoriteRecipes: StateFlow<List<RecipeEntity>> = _favoriteRecipes.asStateFlow()

    private val _secretRecipes = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val secretRecipes: StateFlow<List<RecipeEntity>> = _secretRecipes.asStateFlow()

    // Busqueda

    private val _searchResults = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val searchResults: StateFlow<List<RecipeEntity>> = _searchResults.asStateFlow()

    // Detalle de receta activa

    private val _activeIngredients = MutableStateFlow<List<IngredientRecipeEntity>>(emptyList())
    val activeIngredients: StateFlow<List<IngredientRecipeEntity>> = _activeIngredients.asStateFlow()

    private val _activeSteps = MutableStateFlow<List<RecipeStepEntity>>(emptyList())
    val activeSteps: StateFlow<List<RecipeStepEntity>> = _activeSteps.asStateFlow()

    // Autenticacion

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.login(email, password)
            if (user != null) {
                _currentUser.value = user
                _loginError.value = null
                loadUserData(user.idUser)
            } else {
                _loginError.value = "Email o contraseña incorrectos"
            }
        }
    }

    fun register(
        user: UserEntity,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val existing = repository.getUserByEmail(user.email)
                if (existing != null) {
                    onError("Este email ya esta registrado")
                    return@launch
                }
                repository.registerUser(user)
                onSuccess()
            } catch (e: Exception) {
                onError("Error al registrar: ${e.message}")
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        _userRecipes.value = emptyList()
        _favoriteRecipes.value = emptyList()
        _secretRecipes.value = emptyList()
        _loginError.value = null
    }

    fun clearLoginError() {
        _loginError.value = null
    }

    // Carga de datos del usuario

    private fun loadUserData(userId: Int) {
        viewModelScope.launch {
            repository.getRecipesByAuthor(userId).collect {
                _userRecipes.value = it
            }
        }
        viewModelScope.launch {
            repository.getFavoriteRecipes(userId).collect {
                _favoriteRecipes.value = it
            }
        }
        viewModelScope.launch {
            repository.getSecretRecipes(userId).collect {
                _secretRecipes.value = it
            }
        }
    }

    // Datos mock

    private suspend fun insertarDatosMock() {
        val existing = repository.getUserByEmail("test")
        if (existing == null) {
            val userId = repository.registerUser(
                UserEntity(
                    name = "jorge",
                    email = "test",
                    birthday = "01/01/2000",
                    gender = "Male",
                    password = "1234"
                )
            ).toInt()
            repository.insertRecipe(
                RecipeEntity(
                    name = "Chicken Teriyaki",
                    description = "Sweet and savory chicken with rice and veggies.",
                    author = userId,
                    source = "",
                    image = "chicken_teriyaki",
                    tags = listOf("Quick", "Dinner"),
                    category = "Asian Food",
                    isPublic = true
                )
            )
            repository.insertRecipe(
                RecipeEntity(
                    name = "Vegetarian Curry",
                    description = "Japanese curry with vegetables and rice.",
                    author = userId,
                    source = "",
                    image = "curry",
                    tags = listOf("Vegetarian", "Dinner"),
                    category = "Asian Food",
                    isPublic = true
                )
            )
            repository.insertRecipe(
                RecipeEntity(
                    name = "Classic Cheeseburger",
                    description = "Juicy beef burger with melted cheese.",
                    author = userId,
                    source = "",
                    image = "cheeseburger",
                    tags = listOf("Dinner"),
                    category = "Fast Food",
                    isPublic = true
                )
            )
        }
    }

    // Operaciones de recetas

    fun insertRecipe(
        recipe: RecipeEntity,
        steps: List<RecipeStepEntity>,
        ingredients: List<IngredientRecipeEntity>
    ) {
        viewModelScope.launch {
            val recipeId = repository.insertRecipe(recipe).toInt()
            steps.forEach { repository.insertStep(it.copy(idRecipe = recipeId)) }
            ingredients.forEach { repository.insertIngredientRecipe(it.copy(idRecipe = recipeId)) }
        }
    }

    fun updateFavorite(recipeId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateFavorite(recipeId, isFavorite)
        }
    }

    fun deleteRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.deleteRecipe(recipe)
        }
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            repository.searchRecipes(query).collect {
                _searchResults.value = it
            }
        }
    }

    fun loadRecipeDetail(recipeId: Int) {
        viewModelScope.launch {
            repository.getIngredientsByRecipe(recipeId).collect {
                _activeIngredients.value = it
            }
        }
        viewModelScope.launch {
            repository.getStepsByRecipe(recipeId).collect {
                _activeSteps.value = it
            }
        }
    }

    // Factory

    companion object {
        fun factory(repository: PocketDishesRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PocketDishesViewModel(repository) as T
                }
            }
        }
    }
}
