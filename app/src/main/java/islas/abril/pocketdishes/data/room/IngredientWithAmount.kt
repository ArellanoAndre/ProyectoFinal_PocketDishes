package islas.abril.pocketdishes.data.room

import islas.abril.pocketdishes.data.enums.Units

// Data class que recibe Room al hacer JOIN entre ingredient_recipe e ingredients.
// solo almacena el resultado de la query.
data class IngredientWithAmount(
    val name: String,
    val image: String,
    val amount: Float,
    val unit: Units
)
