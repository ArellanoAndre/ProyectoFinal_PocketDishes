package islas.abril.pocketdishes.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import islas.abril.pocketdishes.data.room.entities.RecipeStepEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeStepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStep(step: RecipeStepEntity): Long

    @Update
    suspend fun updateStep(step: RecipeStepEntity)

    @Delete
    suspend fun deleteStep(step: RecipeStepEntity)

    @Query("SELECT * FROM recipe_steps WHERE idRecipe = :recipeId ORDER BY stepNumber ASC")
    fun getStepsByRecipe(recipeId: Int): Flow<List<RecipeStepEntity>>

    @Query("DELETE FROM recipe_steps WHERE idRecipe = :recipeId")
    suspend fun deleteAllStepsFromRecipe(recipeId: Int)
}
