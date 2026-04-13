package islas.abril.pocketdishes.auth

data class User(
    val name: String,
    val email: String,
    val password: String,
    val birthDate: String,
    val gender: String
)