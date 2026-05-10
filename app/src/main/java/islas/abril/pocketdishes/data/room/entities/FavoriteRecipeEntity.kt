package islas.abril.pocketdishes.data.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "user_fav_recipes",
    primaryKeys = ["idRecipe", "idUser"],
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["idRecipe"],
            childColumns = ["idRecipe"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["idUser"],
            childColumns = ["idUser"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("idRecipe"), Index("idUser")]
)
data class FavoriteRecipeEntity(
    val idRecipe: Int,
    val idUser: Int
)