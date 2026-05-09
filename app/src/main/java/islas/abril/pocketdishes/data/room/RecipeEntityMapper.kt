package islas.abril.pocketdishes.data.room

import android.content.Context
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.data.Ingredients
import islas.abril.pocketdishes.data.Recipe
import islas.abril.pocketdishes.data.RecipeTags
import islas.abril.pocketdishes.data.room.entities.RecipeEntity

// Convierte un RecipeEntity (Room) al modelo Recipe usado por los componentes de UI
fun RecipeEntity.toRecipe(context: Context): Recipe {

    // Detecta si la imagen guardada es una URI de galería (content:// o file://)
    // o el nombre de un drawable (e.g. "chicken_teriyaki")
    val isUri = image.startsWith("content://") || image.startsWith("file://")

    val imageId = when {
        isUri       -> R.drawable.cheese   // placeholder; la imagen real se carga con imageUri
        image.isNotEmpty() -> {
            val id = context.resources.getIdentifier(image, "drawable", context.packageName)
            if (id != 0) id else R.drawable.cheese
        }
        else        -> R.drawable.cheese
    }

    // Mapea los nombres de tags (String) a objetos Tag con color
    val mapeoTags = tags.mapNotNull { tagName ->
        RecipeTags.find { it.name.equals(tagName, ignoreCase = true) }
    }

    return Recipe(
        name        = name,
        description = description,
        author      = "",
        source      = source,
        image       = imageId,
        imageUri    = if (isUri) image else "",  // URI de galería pasada directo a AsyncImage
        prepTime    = prepTime,
        tags        = mapeoTags,
        ingredients = emptyList(),
        steps       = emptyList(),
        category    = listOf(category),
        secretRecipe = isSecret,
        rating      = rating
    )
}

// Convierte un IngredientWithAmount (resultado de JOIN en Room) al modelo Ingredients de UI
fun IngredientWithAmount.toIngredients(context: Context): Ingredients {
    val imageId = if (image.isNotEmpty()) {
        val id = context.resources.getIdentifier(image, "drawable", context.packageName)
        if (id != 0) id else R.drawable.cheese
    } else R.drawable.cheese
    return Ingredients(
        name   = name,
        image  = imageId,
        amount = amount.toInt(),
        unit   = unit
    )
}
