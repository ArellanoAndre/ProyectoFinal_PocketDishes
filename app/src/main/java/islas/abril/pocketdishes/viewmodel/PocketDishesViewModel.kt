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
        viewModelScope.launch {
            insertarDatosMock()
            insertarPublicRecipes()
        }
    }

    // ── Usuario actual ────────────────────────────────────────────────────────

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    // ── Recetas públicas ──────────────────────────────────────────────────────

    // se detiene si ninguna pantalla lo observa durante 5 segundos
    val publicRecipes: StateFlow<List<RecipeEntity>> = repository.getPublicRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ── Recetas del usuario logueado ──────────────────────────────────────────

    private val _userRecipes = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val userRecipes: StateFlow<List<RecipeEntity>> = _userRecipes.asStateFlow()

    private val _favoriteRecipes = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val favoriteRecipes: StateFlow<List<RecipeEntity>> = _favoriteRecipes.asStateFlow()

    private val _secretRecipes = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val secretRecipes: StateFlow<List<RecipeEntity>> = _secretRecipes.asStateFlow()


    // ── Busqueda ──────────────────────────────────────────────────────────────

    private val _searchResults = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val searchResults: StateFlow<List<RecipeEntity>> = _searchResults.asStateFlow()

    // ── Detalle de receta activa ──────────────────────────────────────────────

    private val _activeIngredients = MutableStateFlow<List<IngredientWithAmount>>(emptyList())
    val activeIngredients: StateFlow<List<IngredientWithAmount>> = _activeIngredients.asStateFlow()

    private val _activeSteps = MutableStateFlow<List<RecipeStepEntity>>(emptyList())
    val activeSteps: StateFlow<List<RecipeStepEntity>> = _activeSteps.asStateFlow()

    // Nombre del autor de la receta activa (se resuelve desde la BD para evitar
    // que el mapper devuelva "" ya que RecipeEntity.author es Int, no String)
    private val _activeRecipeAuthorName = MutableStateFlow("")
    val activeRecipeAuthorName: StateFlow<String> = _activeRecipeAuthorName.asStateFlow()

    // ── Autenticacion ─────────────────────────────────────────────────────────

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
        _activeRecipeAuthorName.value = ""
    }

    fun clearLoginError() {
        _loginError.value = null
    }

    // ── Carga de datos del usuario ────────────────────────────────────────────

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

    // ── Datos mock ────────────────────────────────────────────────────────────

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

            // Chicken Teriyaki
            val idTeriyaki = repository.insertRecipe(
                RecipeEntity(
                    name = "Chicken Teriyaki",
                    description = "Sweet and savory chicken with rice and veggies.",
                    author = userId, source = "", image = "chicken_teriyaki",
                    tags = listOf("Quick", "Dinner"), category = "Asian Food",
                    isPublic = true, prepTime = 25, rating = 4.5f
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
                    tags = listOf("Vegetarian", "Dinner"), category = "Asian Food",
                    isPublic = true, prepTime = 30, rating = 4.2f
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

            // Cheeseburger
            val idBurger = repository.insertRecipe(
                RecipeEntity(
                    name = "Cheeseburger",
                    description = "Juicy beef burger with melted cheese.",
                    author = userId, source = "", image = "cheeseburger",
                    tags = listOf("Dinner"), category = "Lunch",
                    isPublic = true, prepTime = 20, rating = 4.8f
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

    fun loadRecipeDetail(recipeId: Int, authorId: Int? = null) {
        // Si se pasa el authorId, buscamos el nombre del autor en la BD
        viewModelScope.launch {
            if (authorId != null) {
                val user = repository.getUserById(authorId)
                _activeRecipeAuthorName.value = user?.name ?: ""
            }
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
            // devuelve la primera receta con ese nombre en toda la BD, aguas)
            val authorId = _currentUser.value?.idUser
            val recipe = if (authorId != null) {
                repository.getRecipeByNameAndAuthor(name, authorId)
                    ?: repository.getRecipeByName(name)
            } else {
                repository.getRecipeByName(name)
            }
            if (recipe != null) loadRecipeDetail(recipe.idRecipe, recipe.author)
        }
    }

    // Recetas publicas de la comunidad

    // INSERT MASIVO

    private suspend fun insertarPublicRecipes() {
        if (repository.getUserByEmail("chef@pocketdishes.com") != null) return

        val chefId = repository.registerUser(
            UserEntity(name = "Community Chef", email = "chef@pocketdishes.com",
                       birthday = "01/01/1990", gender = "Other", password = "")
        ).toInt()

        // BREAKFAST
        sp(chefId, "Fluffy Pancakes", "Golden and fluffy pancakes, perfect for weekend mornings.",
           "pancakes", "Breakfast", listOf("Breakfast", "Quick"),
           listOf("Mix flour, sugar and baking powder.", "Whisk milk, eggs and melted butter.",
                  "Combine wet and dry ingredients gently.", "Cook on a greased griddle until golden."),
           listOf(Triple("Flour","flour",Pair(2f,Units.CUPS)), Triple("Milk","milk",Pair(1f,Units.CUPS)),
                  Triple("Eggs","eggs",Pair(2f,Units.PCS)),   Triple("Butter","butter",Pair(2f,Units.TBSP))),
           prepTime = 20, rating = 4.7f)

        sp(chefId, "Avocado Toast", "Creamy avocado on crispy toast with a hint of lime.",
           "avocado_toast", "Breakfast", listOf("Breakfast", "Healthy"),
           listOf("Toast the bread until golden.", "Mash avocado with lime juice.",
                  "Season with salt and pepper.", "Spread on toast and serve."),
           listOf(Triple("Bread","bread",Pair(2f,Units.PCS)), Triple("Avocado","avocado",Pair(1f,Units.PCS)),
                  Triple("Lime","lime",Pair(1f,Units.PCS)),   Triple("Salt","salt",Pair(1f,Units.TSP))),
           prepTime = 10, rating = 4.4f)

        // LUNCH
        sp(chefId, "BLT Sandwich", "Classic sandwich with crispy bacon, lettuce and tomato.",
           "blt_sandwich", "Lunch", listOf("Lunch", "Quick"),
           listOf("Toast bread slices.", "Cook bacon until crispy.",
                  "Spread mayo on bread.", "Layer bacon, lettuce and tomato."),
           listOf(Triple("Bread","bread",Pair(2f,Units.PCS)), Triple("Bacon","bacon",Pair(3f,Units.PCS)),
                  Triple("Lettuce","lettuce",Pair(1f,Units.PCS)), Triple("Tomato","tomato",Pair(1f,Units.PCS))),
           prepTime = 15, rating = 4.3f)

        // SALADS
        sp(chefId, "Caesar Salad", "Classic romaine salad with parmesan and croutons.",
           "caesar_salad", "Salads", listOf("Healthy", "Lunch"),
           listOf("Wash and chop romaine lettuce.", "Toss with Caesar dressing.",
                  "Top with croutons and parmesan.", "Serve immediately."),
           listOf(Triple("Romaine Lettuce","romaine_lettuce",Pair(1f,Units.PCS)),
                  Triple("Croutons","croutons",Pair(1f,Units.CUPS)),
                  Triple("Parmesan","parmesan",Pair(50f,Units.GR)),
                  Triple("Caesar Dressing","caesar_dressing",Pair(3f,Units.TBSP))),
           prepTime = 15, rating = 4.5f)

        // HEALTHY
        sp(chefId, "Greek Salad", "Fresh Mediterranean salad with feta and olives.",
           "greek_salad", "Healthy", listOf("Healthy", "Vegan"),
           listOf("Dice cucumber and tomatoes.", "Slice red onion.",
                  "Combine with olives and feta.", "Drizzle with olive oil and serve."),
           listOf(Triple("Cucumber","cucumber",Pair(1f,Units.PCS)), Triple("Tomato","tomato",Pair(2f,Units.PCS)),
                  Triple("Feta Cheese","feta_cheese",Pair(100f,Units.GR)), Triple("Olives","olives",Pair(50f,Units.GR))),
           prepTime = 10, rating = 4.6f)

        // SOUPS
        sp(chefId, "Tomato Soup", "Creamy homemade tomato soup with fresh herbs.",
           "tomato_soup", "Soups", listOf("Vegetarian", "Lunch"),
           listOf("Sauté onion and garlic.", "Add chopped tomatoes and broth.",
                  "Simmer for 20 minutes.", "Blend until smooth.", "Stir in cream and season."),
           listOf(Triple("Tomatoes","tomatoes",Pair(500f,Units.GR)), Triple("Onion","onion",Pair(1f,Units.PCS)),
                  Triple("Garlic","garlic",Pair(2f,Units.PCS)),      Triple("Cream","cream",Pair(0.5f,Units.CUPS))),
           prepTime = 30, rating = 4.4f)

        // MAIN DISHES
        sp(chefId, "Spaghetti Bolognese", "Traditional Italian meat sauce pasta.",
           "spaghetti", "Main Dishes", listOf("Dinner"),
           listOf("Cook spaghetti until al dente.", "Brown ground beef in a pan.",
                  "Add tomato sauce and simmer 20 min.", "Mix pasta with sauce.", "Serve with parmesan."),
           listOf(Triple("Spaghetti","spaghetti",Pair(200f,Units.GR)), Triple("Ground Beef","beef",Pair(300f,Units.GR)),
                  Triple("Tomato Sauce","tomato_sauce",Pair(400f,Units.GR)), Triple("Onion","onion",Pair(1f,Units.PCS))),
           prepTime = 40, rating = 4.8f)

        sp(chefId, "Grilled Chicken", "Juicy chicken breast marinated with herbs and lemon.",
           "grilled_chicken", "Main Dishes", listOf("Healthy", "Dinner"),
           listOf("Marinate chicken with oil, garlic and herbs.", "Preheat grill to medium-high.",
                  "Grill 6-7 minutes per side.", "Rest 5 minutes before serving."),
           listOf(Triple("Chicken Breast","chicken",Pair(300f,Units.GR)), Triple("Olive Oil","olive_oil",Pair(2f,Units.TBSP)),
                  Triple("Garlic","garlic",Pair(3f,Units.PCS)), Triple("Lemon","lemon",Pair(1f,Units.PCS))),
           prepTime = 30, rating = 4.7f)

        // SIDE DISHES
        sp(chefId, "Mashed Potatoes", "Creamy and buttery mashed potatoes.",
           "mashed_potatoes", "Side Dishes", listOf("Dinner"),
           listOf("Boil potatoes until tender.", "Drain and mash.",
                  "Add butter and warm milk.", "Season with salt and serve hot."),
           listOf(Triple("Potatoes","potatoes",Pair(500f,Units.GR)), Triple("Butter","butter",Pair(50f,Units.GR)),
                  Triple("Milk","milk",Pair(0.5f,Units.CUPS)), Triple("Salt","salt",Pair(1f,Units.TSP))),
           prepTime = 25, rating = 4.5f)

        // DESSERTS
        sp(chefId, "Chocolate Brownies", "Fudgy chocolate brownies with a crinkly top.",
           "brownies", "Desserts", listOf("Dessert"),
           listOf("Melt chocolate and butter.", "Whisk in sugar and eggs.",
                  "Fold in flour.", "Bake at 180°C for 25 minutes.", "Cool before cutting."),
           listOf(Triple("Dark Chocolate","dark_chocolate",Pair(200f,Units.GR)), Triple("Butter","butter",Pair(100f,Units.GR)),
                  Triple("Sugar","sugar",Pair(200f,Units.GR)), Triple("Eggs","eggs",Pair(3f,Units.PCS)),
                  Triple("Flour","flour",Pair(100f,Units.GR))),
           prepTime = 35, rating = 4.9f)

        sp(chefId, "Fruit Salad", "Fresh seasonal fruits with honey and mint.",
           "fruit_salad", "Desserts", listOf("Healthy", "Dessert"),
           listOf("Wash and cut all fruits.", "Mix in a large bowl.",
                  "Drizzle with honey.", "Garnish with mint leaves and serve cold."),
           listOf(Triple("Strawberries","strawberries",Pair(100f,Units.GR)), Triple("Mango","mango",Pair(1f,Units.PCS)),
                  Triple("Blueberries","blueberries",Pair(100f,Units.GR)), Triple("Honey","honey",Pair(2f,Units.TBSP))),
           prepTime = 10, rating = 4.3f)

        // BEVERAGES
        sp(chefId, "Mango Smoothie", "Tropical mango and banana smoothie.",
           "mango_smoothie", "Beverages", listOf("Breakfast", "Healthy"),
           listOf("Peel and chop mango and banana.", "Add to blender with milk.",
                  "Blend until smooth.", "Serve chilled."),
           listOf(Triple("Mango","mango",Pair(1f,Units.PCS)), Triple("Banana","banana",Pair(1f,Units.PCS)),
                  Triple("Milk","milk",Pair(1f,Units.CUPS)), Triple("Honey","honey",Pair(1f,Units.TBSP))),
           prepTime = 5, rating = 4.6f)

        // SNACKS
        sp(chefId, "Guacamole", "Fresh homemade guacamole with lime and cilantro.",
           "guacamole", "Snacks", listOf("Snacks", "Vegan"),
           listOf("Halve and pit the avocados.", "Mash with a fork.",
                  "Mix in lime juice and cilantro.", "Season with salt and serve with chips."),
           listOf(Triple("Avocado","avocado",Pair(2f,Units.PCS)), Triple("Lime","lime",Pair(1f,Units.PCS)),
                  Triple("Cilantro","cilantro",Pair(1f,Units.TBSP)), Triple("Salt","salt",Pair(1f,Units.TSP))),
           prepTime = 10, rating = 4.7f)

        // COMFORT FOOD
        sp(chefId, "Mac and Cheese", "Ultra creamy mac and cheese made from scratch.",
           "mac_and_cheese", "Comfort Food", listOf("Dinner"),
           listOf("Cook macaroni according to package.", "Melt butter and whisk in flour.",
                  "Add milk and bring to simmer.", "Stir in cheese until melted.", "Mix with pasta."),
           listOf(Triple("Macaroni","macaroni",Pair(200f,Units.GR)), Triple("Cheese","cheese",Pair(200f,Units.GR)),
                  Triple("Butter","butter",Pair(30f,Units.GR)), Triple("Milk","milk",Pair(1f,Units.CUPS))),
           prepTime = 30, rating = 4.8f)

        // QUICK & EASY
        sp(chefId, "Egg Fried Rice", "Quick and delicious fried rice with scrambled eggs.",
           "fried_rice", "Quick & Easy", listOf("Quick", "Dinner"),
           listOf("Heat oil in wok or pan.", "Scramble eggs and set aside.",
                  "Fry cooked rice until slightly crispy.", "Add eggs, vegetables and soy sauce."),
           listOf(Triple("Rice","rice",Pair(2f,Units.CUPS)), Triple("Eggs","eggs",Pair(2f,Units.PCS)),
                  Triple("Soy Sauce","soy_sauce",Pair(2f,Units.TBSP)), Triple("Carrots","carrots",Pair(1f,Units.PCS))),
           prepTime = 20, rating = 4.5f)

        // MEXICAN
        sp(chefId, "Tacos", "Seasoned beef tacos with fresh toppings.",
           "tacos", "Mexican", listOf("Dinner"),
           listOf("Brown ground beef with taco seasoning.", "Warm tortillas on a griddle.",
                  "Fill tortillas with beef and toppings.", "Serve with lime and salsa."),
           listOf(Triple("Flour Tortilla","tortilla",Pair(4f,Units.PCS)), Triple("Ground Beef","beef",Pair(300f,Units.GR)),
                  Triple("Cheese","cheese",Pair(100f,Units.GR)), Triple("Lettuce","lettuce",Pair(1f,Units.PCS))),
           prepTime = 25, rating = 4.7f)

        sp(chefId, "Quesadillas", "Crispy quesadillas filled with cheese and chicken.",
           "quesadilla", "Mexican", listOf("Quick"),
           listOf("Season and cook chicken strips.", "Lay tortilla flat in a pan.",
                  "Add cheese and chicken on one half.", "Fold and cook until golden on both sides."),
           listOf(Triple("Flour Tortilla","tortilla",Pair(2f,Units.PCS)), Triple("Chicken Breast","chicken",Pair(200f,Units.GR)),
                  Triple("Cheese","cheese",Pair(150f,Units.GR))),
           prepTime = 20, rating = 4.6f)

        // ORIENTAL FOOD
        sp(chefId, "Ramen", "Rich and comforting Japanese ramen bowl.",
           "ramen", "Oriental food", listOf("Dinner"),
           listOf("Prepare and heat the broth.", "Cook ramen noodles per package.",
                  "Soft-boil eggs and slice in half.", "Assemble bowl with noodles, broth and toppings."),
           listOf(Triple("Ramen Noodles","ramen_noodles",Pair(200f,Units.GR)), Triple("Eggs","eggs",Pair(2f,Units.PCS)),
                  Triple("Broth","broth",Pair(2f,Units.CUPS)), Triple("Broccoli","broccoli",Pair(1f,Units.CUPS))),
           prepTime = 45, rating = 4.8f)

        // ASIAN FOOD
        sp(chefId, "Spring Rolls", "Crispy vegetable spring rolls with dipping sauce.",
           "spring_rolls", "Asian Food", listOf("Snacks"),
           listOf("Sauté shredded cabbage and carrots.", "Place filling on spring roll wrapper.",
                  "Roll tightly and seal edges with water.", "Deep fry until golden and crispy."),
           listOf(Triple("Cabbage","cabbage",Pair(200f,Units.GR)), Triple("Carrots","carrots",Pair(2f,Units.PCS)),
                  Triple("Broccoli","broccoli",Pair(100f,Units.GR))),
           prepTime = 30, rating = 4.4f)

        // MEAL PREP
        sp(chefId, "Chicken Rice Bowl", "Healthy chicken and brown rice meal prep bowl.",
           "rice_bowl", "Meal Prep", listOf("Healthy", "Meal Prep"),
           listOf("Cook brown rice.", "Season and bake chicken breasts at 200°C.",
                  "Steam broccoli.", "Assemble bowls and drizzle with soy sauce."),
           listOf(Triple("Chicken Breast","chicken",Pair(300f,Units.GR)), Triple("Rice","rice",Pair(2f,Units.CUPS)),
                  Triple("Broccoli","broccoli",Pair(2f,Units.CUPS)), Triple("Soy Sauce","soy_sauce",Pair(2f,Units.TBSP))),
           prepTime = 35, rating = 4.6f)

        // STREET FOOD
        sp(chefId, "Hot Dog", "Classic street-style hot dog with all the toppings.",
           "hot_dog", "Street Food", listOf("Snacks", "Quick"),
           listOf("Grill or steam the sausage.", "Warm the bun on a pan.",
                  "Place sausage in bun.", "Add mustard, ketchup and onions."),
           listOf(Triple("Bun","bun",Pair(1f,Units.PCS)), Triple("Sausage","sausage",Pair(1f,Units.PCS)),
                  Triple("Mustard","mustard",Pair(1f,Units.TBSP)), Triple("Ketchup","ketchup",Pair(1f,Units.TBSP))),
           prepTime = 10, rating = 4.2f)
    }

    // Helper compacto para insertar una receta publica con pasos e ingredientes
    private suspend fun sp(
        authorId: Int, name: String, description: String, image: String,
        category: String, tags: List<String>, steps: List<String>,
        ingredients: List<Triple<String, String, Pair<Float, Units>>>,
        prepTime: Int = 0, rating: Float = 0f
    ) {
        val id = repository.insertRecipe(
            RecipeEntity(
                name = name, description = description, author = authorId,
                source = "", image = image, tags = tags, category = category,
                isPublic = true, prepTime = prepTime, rating = rating
            )
        ).toInt()
        steps.forEachIndexed { i, step ->
            repository.insertStep(RecipeStepEntity(idRecipe = id, stepNumber = i + 1, description = step))
        }
        seedIngredients(id, ingredients)
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
