package islas.abril.pocketdishes.data

import androidx.compose.ui.graphics.Color
import islas.abril.pocketdishes.ui.theme.tagBreakfast
import islas.abril.pocketdishes.ui.theme.tagDessert
import islas.abril.pocketdishes.ui.theme.tagDinner
import islas.abril.pocketdishes.ui.theme.tagHealthy
import islas.abril.pocketdishes.ui.theme.tagLowCarb
import islas.abril.pocketdishes.ui.theme.tagLunch
import islas.abril.pocketdishes.ui.theme.tagQuick
import islas.abril.pocketdishes.ui.theme.tagSpicy
import islas.abril.pocketdishes.ui.theme.tagVegan
import islas.abril.pocketdishes.ui.theme.tagVegetarian

data class Tag(
    val name: String,
    val color: Color
)

val RecipeTags  = listOf(
    Tag("Healthy", tagHealthy),
    Tag("Vegan", tagVegan),
    Tag("Vegetarian", tagVegetarian),
    Tag("Quick", tagQuick),
    Tag("Dessert", tagDessert),
    Tag("Spicy", tagSpicy),
    Tag("Low carb", tagLowCarb),
    Tag("Breakfast", tagBreakfast),
    Tag("Lunch", tagLunch),
    Tag("Dinner",tagDinner)
)

fun getTag(index:Int):Tag{
    if(index<=RecipeTags.size){
        return RecipeTags[index]
    }
    else{
        error("No existe el tag")
    }

}