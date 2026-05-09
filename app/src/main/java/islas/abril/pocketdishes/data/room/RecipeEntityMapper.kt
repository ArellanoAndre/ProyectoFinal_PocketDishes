package islas.abril.pocketdishes.data.room

import android.content.Context
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.data.Recipe
import islas.abril.pocketdishes.data.RecipeTags
import islas.abril.pocketdishes.data.room.entities.RecipeEntity

// Convierte un RecipeEntity (Room) al modelo Recipe usado por los componentes de UI
fun RecipeEntity.toRecipe(context: Context): Recipe {

    // Resuelve el nombre de imagen guardado en BD al drawable resource ID
    val imageId = if (image.isNotEmpty()) {
        context.resources.getIdentifier(image, "drawable", context.packageName)
    } else 0

    // Mapea los nombres de tags (String) a objetos Tag con color
    val mapeoTags = tags.mapNotNull { tagName ->
        RecipeTags.find { it.name.equals(tagName, ignoreCase = true) }
    }

    return Recipe(
        name = name,
        description = description,
        author = "",
        source = source,
        image = if (imageId != 0) imageId else R.drawable.sushi,
        prepTime = 0,
        tags = mapeoTags,
        ingredients = emptyList(),
        steps = emptyList(),
        category = listOf(category),
        secretRecipe = isSecret,
        rating = rating
    )
}
