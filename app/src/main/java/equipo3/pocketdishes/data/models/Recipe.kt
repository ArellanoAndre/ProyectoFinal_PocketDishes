package equipo3.pocketdishes.data.models

data class Recipe (
    val title: String,
    val author: String,
    val time: String,
    val tags: List<String>,
    val ingredients: List<Ingredient>,
    val steps: List<String>
)