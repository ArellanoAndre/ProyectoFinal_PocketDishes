package islas.abril.pocketdishes.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import islas.abril.pocketdishes.data.room.entities.RecipeEntity
import islas.abril.pocketdishes.data.room.entities.FavoriteRecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteRecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteRecipe(
        favoriteRecipe: FavoriteRecipeEntity
    )

    @Query(
        """
        DELETE FROM user_fav_recipes
        WHERE idRecipe = :recipeId
        AND idUser = :userId
    """
    )
    suspend fun removeFavorite(
        userId: Int,
        recipeId: Int
    )

    @Query(
        """
        SELECT recipes.*
        FROM recipes
        INNER JOIN user_fav_recipes
        ON recipes.idRecipe = user_fav_recipes.idRecipe
        WHERE user_fav_recipes.idUser = :userId
    """
    )
    fun getFavoriteRecipes(
        userId: Int
    ): Flow<List<RecipeEntity>>

    @Query(
        """
    SELECT EXISTS(
        SELECT 1
        FROM user_fav_recipes
        WHERE idRecipe = :recipeId
        AND idUser = :userId
    )
"""
    )
    suspend fun isFavorite(
        userId: Int,
        recipeId: Int
    ): Boolean

}