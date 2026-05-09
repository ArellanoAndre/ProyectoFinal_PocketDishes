package islas.abril.pocketdishes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import islas.abril.pocketdishes.data.room.IngredientWithAmount
import islas.abril.pocketdishes.data.room.PocketDishesRepository
import islas.abril.pocketdishes.data.room.entities.IngredientEntity
import islas.abril.pocketdishes.data.room.entities.IngredientRecipeEntity
import islas.abril.pocketdishes.data.room.entities.RecipeEntity
import islas.abril.pocketdishes.data.room.entities.RecipeStepEntity
import islas.abril.pocketdishes.data.room.entities.UserEntity
import islas.abril.pocketdishes.data.enums.Units
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

    private val _activeIngredients = MutableStateFlow<List<IngredientWithAmount>>(emptyList())
    val activeIngredients: StateFlow<List<IngredientWithAmount>> = _activeIngredients.asStateFlow()

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
        val existing = repository.getUserByEmail("testv2")
        if (existing == null) {
            val userId = repository.registerUser(
                UserEntity(
                    name = "jorge",
                    email = "testv2",
                    birthday = "01/01/2000",
                    gender = "Male",
                    password = "1234"
                )
            ).toInt()

            // Chicken Teriyaki
            val idTeriyaki = repository.insertRecipe(
                RecipeEntity(
                    name = "Chicken Teriyaki",
                    description = "Sweet and savory chicken with rice and veggies.",
                    author = userId, source = "", image = "chicken_teriyaki",
                    tags = listOf("Quick", "Dinner"), category = "Asian Food", isPublic = true
                )
            ).toInt()
            listOf("Cook the rice.", "Grill chicken and slice it.",
                   "Steam broccoli.", "Mix everything with teriyaki sauce.")
                .forEachIndexed { i, step ->
                    repository.insertStep(RecipeStepEntity(idRecipe = idTeriyaki, stepNumber = i + 1, description = step))
                }
            seedIngredients(idTeriyaki, listOf(
                Triple("Chicken Breast", "chicken",  Pair(200f, Units.GR)),
                Triple("Rice",           "rice",     Pair(1f,   Units.CUPS)),
                Triple("Broccoli",       "broccoli", Pair(1f,   Units.CUPS)),
                Triple("Teriyaki Sauce", "teriyaki", Pair(3f,   Units.TBSP))
            ))

            // Vegetarian Curry
            val idCurry = repository.insertRecipe(
                RecipeEntity(
                    name = "Vegetarian Curry",
                    description = "Japanese curry with vegetables and rice.",
                    author = userId, source = "", image = "curry",
                    tags = listOf("Vegetarian", "Dinner"), category = "Asian Food", isPublic = true
                )
            ).toInt()
            listOf("Cook the rice.", "Boil potatoes and carrots.",
                   "Mix vegetables with curry paste.", "Serve hot over rice.")
                .forEachIndexed { i, step ->
                    repository.insertStep(RecipeStepEntity(idRecipe = idCurry, stepNumber = i + 1, description = step))
                }
            seedIngredients(idCurry, listOf(
                Triple("Lentils",     "lentils",    Pair(200f, Units.GR)),
                Triple("Rice",        "rice",       Pair(1f,   Units.CUPS)),
                Triple("Potatoes",    "potatoes",   Pair(2f,   Units.PCS)),
                Triple("Carrots",     "carrots",    Pair(2f,   Units.PCS)),
                Triple("Curry Paste", "currypaste", Pair(100f, Units.GR))
            ))

            // Classic Cheeseburger
            val idBurger = repository.insertRecipe(
                RecipeEntity(
                    name = "Classic Cheeseburger",
                    description = "Juicy beef burger with melted cheese.",
                    author = userId, source = "", image = "cheeseburger",
                    tags = listOf("Dinner"), category = "Fast Food", isPublic = true
                )
            ).toInt()
            listOf("Form beef patty.", "Cook on grill or pan.",
                   "Assemble burger with ingredients.", "Serve hot.")
                .forEachIndexed { i, step ->
                    repository.insertStep(RecipeStepEntity(idRecipe = idBurger, stepNumber = i + 1, description = step))
                }
            seedIngredients(idBurger, listOf(
                Triple("Ground Beef", "beef",    Pair(150f, Units.GR)),
                Triple("Burger Bun",  "bun",     Pair(1f,   Units.PCS)),
                Triple("Cheese",      "cheese",  Pair(1f,   Units.PCS)),
                Triple("Lettuce",     "lettuce", Pair(1f,   Units.PCS))
            ))
        }
    }

    // Inserta ingredientes para una receta reutilizando el ingrediente si ya existe
    private suspend fun seedIngredients(
        recipeId: Int,
        items: List<Triple<String, String, Pair<Float, Units>>>
    ) {
        items.forEach { (name, image, amountUnit) ->
            val insertedId = repository.insertIngredient(
                IngredientEntity(name = name, image = image)
            ).toInt()
            val ingredientId = if (insertedId != -1) insertedId
                               else repository.getIngredientByName(name)?.idIngredient ?: return@forEach
            repository.insertIngredientRecipe(
                IngredientRecipeEntity(
                    idRecipe = recipeId,
                    idIngredient = ingredientId,
                    amount = amountUnit.first,
                    unit = amountUnit.second
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
            repository.getIngredientDetailsForRecipe(recipeId).collect {
                _activeIngredients.value = it
            }
        }
        viewModelScope.launch {
            repository.getStepsByRecipe(recipeId).collect {
                _activeSteps.value = it
            }
        }
    }

    fun loadRecipeDetailByName(name: String) {
        viewModelScope.launch {
            // Busca primero la receta del usuario logueado para evitar
            // colisiones de nombre entre usuarios distintos (LIMIT 1 sin filtro
            // devuelve la primera receta con ese nombre en toda la BD aguas
            val authorId = _currentUser.value?.idUser
            val recipe = if (authorId != null) {
                repository.getRecipeByNameAndAuthor(name, authorId)
                    ?: repository.getRecipeByName(name)
            } else {
                repository.getRecipeByName(name)
            }
            if (recipe != null) loadRecipeDetail(recipe.idRecipe)
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
