package islas.abril.pocketdishes.data

import islas.abril.pocketdishes.data.enums.Units

data class Ingredients(
    val name: String,
    val image: Int,
    val amount: Int,
    val unit: Units
)
