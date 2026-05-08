package islas.abril.pocketdishes.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import islas.abril.pocketdishes.data.room.entities.IngredientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIngredient(ingredient: IngredientEntity): Long

    @Update
    suspend fun updateIngredient(ingredient: IngredientEntity)

    @Delete
    suspend fun deleteIngredient(ingredient: IngredientEntity)

    @Query("SELECT * FROM ingredients WHERE idIngredient = :id")
    suspend fun getIngredientById(id: Int): IngredientEntity?

    @Query("SELECT * FROM ingredients WHERE name = :name LIMIT 1")
    suspend fun getIngredientByName(name: String): IngredientEntity?

    @Query("SELECT * FROM ingredients")
    fun getAllIngredients(): Flow<List<IngredientEntity>>

    @Query("SELECT * FROM ingredients WHERE name LIKE '%' || :query || '%'")
    fun searchIngredients(query: String): Flow<List<IngredientEntity>>
}
