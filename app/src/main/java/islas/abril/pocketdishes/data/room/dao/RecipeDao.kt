package islas.abril.pocketdishes.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import islas.abril.pocketdishes.data.room.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity): Long

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes WHERE idRecipe = :id")
    suspend fun getRecipeById(id: Int): RecipeEntity?

    @Query("SELECT * FROM recipes WHERE isPublic = 1 AND isSecret = 0")
    fun getPublicRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE author = :userId")
    fun getRecipesByAuthor(userId: Int): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE isFavorite = 1 AND author = :userId")
    fun getFavoriteRecipes(userId: Int): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE isSecret = 1 AND author = :userId")
    fun getSecretRecipes(userId: Int): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE name LIKE '%' || :query || '%'")
    fun searchRecipes(query: String): Flow<List<RecipeEntity>>

    @Query("UPDATE recipes SET isFavorite = :isFavorite WHERE idRecipe = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)
}
