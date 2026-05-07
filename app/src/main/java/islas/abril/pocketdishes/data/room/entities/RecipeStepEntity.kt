package islas.abril.pocketdishes.data.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipe_steps",
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["idRecipe"],
            childColumns = ["idRecipe"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("idRecipe")]
)
data class RecipeStepEntity(
    @PrimaryKey(autoGenerate = true)
    val idRecipeStep: Int = 0,
    val idRecipe: Int,
    val stepNumber: Int,
    val description: String
)
