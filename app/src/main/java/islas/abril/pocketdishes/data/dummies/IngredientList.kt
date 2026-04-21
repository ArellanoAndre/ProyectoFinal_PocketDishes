package islas.abril.pocketdishes.data.dummies

import islas.abril.pocketdishes.data.Ingredients
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.data.enums.Units

val IngredientList = listOf(

    Ingredients(
        name = "rice",
        image = R.drawable.rice,
        amount = 0,
        unit = Units.GR
    ),

    Ingredients(
        name = "Cheese",
        image = R.drawable.cheese,
        amount = 0,
        unit = Units.GR
    )
)