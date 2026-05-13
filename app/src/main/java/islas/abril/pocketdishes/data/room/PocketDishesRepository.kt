package islas.abril.pocketdishes.data.room

import islas.abril.pocketdishes.data.room.entities.FavoriteRecipeEntity
import islas.abril.pocketdishes.data.room.entities.IngredientEntity
import islas.abril.pocketdishes.data.room.entities.IngredientRecipeEntity
import islas.abril.pocketdishes.data.room.entities.RecipeEntity
import islas.abril.pocketdishes.data.room.entities.RecipeStepEntity
import islas.abril.pocketdishes.data.room.entities.UserEntity

class PocketDishesRepository(database: AppDatabase) {

    private val userDao = database.userDao()
    private val recipeDao = database.recipeDao()
    private val ingredientDao = database.ingredientDao()
    private val ingredientRecipeDao = database.ingredientRecipeDao()
    private val recipeStepDao = database.recipeStepDao()
    private val favoriteRecipesDao = database.favoriteRecipe()

    // ─── Usuarios ────────────────────────────────────────────────────────────

    suspend fun registerUser(user: UserEntity): Long =
        userDao.insertUser(user)

    suspend fun login(email: String, password: String): UserEntity? =
        userDao.login(email, password)

    suspend fun getUserById(id: Int): UserEntity? =
        userDao.getUserById(id)

    suspend fun getUserByEmail(email: String): UserEntity? =
        userDao.getUserByEmail(email)

    suspend fun updateUser(user: UserEntity) =
        userDao.updateUser(user)

    fun getAllUsers() = userDao.getAllUsers()

    // ─── Recetas ─────────────────────────────────────────────────────────────

    suspend fun insertRecipe(recipe: RecipeEntity): Long =
        recipeDao.insertRecipe(recipe)

    suspend fun updateRecipe(recipe: RecipeEntity) =
        recipeDao.updateRecipe(recipe)

    suspend fun deleteRecipe(recipe: RecipeEntity) =
        recipeDao.deleteRecipe(recipe)

    suspend fun getRecipeById(id: Int): RecipeEntity? =
        recipeDao.getRecipeById(id)

    suspend fun getRecipeByName(name: String): RecipeEntity? =
        recipeDao.getRecipeByName(name)

    suspend fun getRecipeByNameAndAuthor(name: String, authorId: Int): RecipeEntity? =
        recipeDao.getRecipeByNameAndAuthor(name, authorId)

    fun getPublicRecipes() = recipeDao.getPublicRecipes()

    fun getRecipesByAuthor(userId: Int) = recipeDao.getRecipesByAuthor(userId)

    fun getSecretRecipes(userId: Int) = recipeDao.getSecretRecipes(userId)

    fun searchRecipes(query: String) = recipeDao.searchRecipes(query)

    suspend fun updateRating(recipeId: Int, rating: Float) =
        recipeDao.updateRating(recipeId, rating)

    suspend fun updateFavorite(userId: Int, recipeId: Int) {
        if (
            favoriteRecipesDao.isFavorite(userId, recipeId)
        ) {
            favoriteRecipesDao.removeFavorite(userId, recipeId
            )
        } else {
            favoriteRecipesDao.insertFavoriteRecipe(
                FavoriteRecipeEntity(
                    idRecipe = recipeId,
                    idUser = userId
                )
            )
        }
    }
    fun getFavoriteRecipes(userId: Int) =
        favoriteRecipesDao.getFavoriteRecipes(userId)

    // ─── Ingredientes ─────────────────────────────────────────────────────────

    suspend fun insertIngredient(ingredient: IngredientEntity): Long =
        ingredientDao.insertIngredient(ingredient)

    suspend fun getIngredientByName(name: String): IngredientEntity? =
        ingredientDao.getIngredientByName(name)

    fun getAllIngredients() = ingredientDao.getAllIngredients()

    fun searchIngredients(query: String) = ingredientDao.searchIngredients(query)

    // ─── Ingredientes por Receta ──────────────────────────────────────────────

    suspend fun insertIngredientRecipe(ingredientRecipe: IngredientRecipeEntity): Long =
        ingredientRecipeDao.insertIngredientRecipe(ingredientRecipe)

    fun getIngredientsByRecipe(recipeId: Int) =
        ingredientRecipeDao.getIngredientsByRecipe(recipeId)

    fun getIngredientDetailsForRecipe(recipeId: Int): kotlinx.coroutines.flow.Flow<List<IngredientWithAmount>> =
        ingredientRecipeDao.getIngredientDetailsForRecipe(recipeId)

    suspend fun deleteAllIngredientsFromRecipe(recipeId: Int) =
        ingredientRecipeDao.deleteAllIngredientsFromRecipe(recipeId)

    // ─── Pasos de Receta ──────────────────────────────────────────────────────

    suspend fun insertStep(step: RecipeStepEntity): Long =
        recipeStepDao.insertStep(step)

    suspend fun updateStep(step: RecipeStepEntity) =
        recipeStepDao.updateStep(step)

    fun getStepsByRecipe(recipeId: Int) =
        recipeStepDao.getStepsByRecipe(recipeId)

    suspend fun deleteAllStepsFromRecipe(recipeId: Int) =
        recipeStepDao.deleteAllStepsFromRecipe(recipeId)
}
