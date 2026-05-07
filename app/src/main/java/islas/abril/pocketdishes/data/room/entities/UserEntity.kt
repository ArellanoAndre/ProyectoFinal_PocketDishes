package islas.abril.pocketdishes.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val idUser: Int = 0,
    val name: String,
    val email: String,
    val birthday: String,
    val gender: String,
    val password: String,
    val profilePicture: String = ""
)
