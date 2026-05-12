package islas.abril.pocketdishes.data.room

import android.content.Context
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.data.Ingredients
import islas.abril.pocketdishes.data.Recipe
import islas.abril.pocketdishes.data.RecipeTags
import islas.abril.pocketdishes.data.room.entities.RecipeEntity

// Convierte un RecipeEntity (Room) al modelo Recipe usado por los componentes de UI
fun RecipeEntity.toRecipe(context: Context): Recipe {

    // Detecta si la imagen guardada en BD es una URI de galería (content:// o file://)
    val isUri = image.startsWith("content://") || image.startsWith("file://")

    // Resuelve el nombre de imagen guardado en BD al drawable resource ID aqui como tal consigue la imagen segun el nombre
    // Si es URI, se usa placeholder; el componente usa imageUri directamente con AsyncImage
    val imageId = if (!isUri && image.isNotEmpty()) {
        context.resources.getIdentifier(image, "drawable", context.packageName)
            .let { if (it != 0) it else R.drawable.cheese }
    } else R.drawable.cheese

    // Mapea los nombres de tags (String) a objetos Tag con color
    val mapeoTags = tags.mapNotNull { tagName ->
        RecipeTags.find { it.name.equals(tagName, ignoreCase = true) }
    }

    return Recipe(
        id= idRecipe,
        name = name,
        description = description,
        author = "",
        source = source,
        image = imageId,
        imageUri = if (isUri) image else "",   // pasa la URI para que AsyncImage la muestre
        prepTime = prepTime,
        tags = mapeoTags,
        ingredients = emptyList(),
        steps = emptyList(),
        category = listOf(category),
        secretRecipe = isSecret,
        rating = rating,
        isFavorite=isFavorite
    )
}

// Convierte un IngredientWithAmount (resultado de JOIN en Room) al modelo Ingredients de UI
fun IngredientWithAmount.toIngredients(context: Context): Ingredients {
    val imageId = if (image.isNotEmpty()) {
        context.resources.getIdentifier(image, "drawable", context.packageName)
    } else 0
    return Ingredients(
        name = name,
        image = if (imageId != 0) imageId else R.drawable.cheese,
        amount = amount.toInt(),
        unit = unit
    )
}

// Convierte el modelo de UI (Recipe) de vuelta a RecipeEntity (Room)
fun Recipe.toEntity(authorId: Int): RecipeEntity {
    return RecipeEntity(
        idRecipe = this.id,
        name = this.name,
        description = this.description,
        author = authorId,
        source = this.source,
        image = this.imageUri.ifEmpty { "" },
        tags = this.tags.map { it.name },
        category = this.category.firstOrNull() ?: "General",
        isSecret = this.secretRecipe,
        isPublic = true,
        isFavorite = this.isFavorite,
        rating = this.rating,
        prepTime = this.prepTime
    )
}