package islas.abril.pocketdishes.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import islas.abril.pocketdishes.data.room.entities.IngredientRecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientRecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredientRecipe(ingredientRecipe: IngredientRecipeEntity): Long

    @Update
    suspend fun updateIngredientRecipe(ingredientRecipe: IngredientRecipeEntity)

    @Delete
    suspend fun deleteIngredientRecipe(ingredientRecipe: IngredientRecipeEntity)

    @Query("SELECT * FROM ingredient_recipe WHERE idRecipe = :recipeId")
    fun getIngredientsByRecipe(recipeId: Int): Flow<List<IngredientRecipeEntity>>

    @Query("DELETE FROM ingredient_recipe WHERE idRecipe = :recipeId")
    suspend fun deleteAllIngredientsFromRecipe(recipeId: Int)
}
