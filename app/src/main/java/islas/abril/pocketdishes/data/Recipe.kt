package islas.abril.pocketdishes.data

data class Recipe(
    var name: String,
    var description: String,
    var author: String,
    var source:String,
    var image: Int,
    var imageUri: String = "",  // URI de galería; si no está vacío tiene prioridad sobre image, aqui se necesitaba para el de agregar receta
    var prepTime: Int,
    var tags: List<Tag>,
    var ingredients: List<Ingredients>,
    var steps: List<String>,
    var category: List<String>,
    var secretRecipe: Boolean,
    var rating: Float
)
