package islas.abril.pocketdishes.data.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import islas.abril.pocketdishes.data.enums.Units

@Entity(
    tableName = "ingredient_recipe",
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["idRecipe"],
            childColumns = ["idRecipe"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = IngredientEntity::class,
            parentColumns = ["idIngredient"],
            childColumns = ["idIngredient"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("idRecipe"), Index("idIngredient")]
)
data class IngredientRecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val idIngredientRecipe: Int = 0,
    val idRecipe: Int,
    val idIngredient: Int,
    val amount: Float,
    val unit: Units
)
