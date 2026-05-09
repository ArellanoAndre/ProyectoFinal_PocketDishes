package islas.abril.pocketdishes.data.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipes",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["idUser"],
            childColumns = ["author"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("author")]
)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val idRecipe: Int = 0,
    val name: String,
    val description: String,
    val author: Int,
    val source: String,
    val image: String,
    val tags: List<String>,
    val category: String,
    val isSecret: Boolean = false,
    val isPublic: Boolean = true,
    val isFavorite: Boolean = false,
    val rating: Float = 0f,
    val prepTime: Int = 0
)
