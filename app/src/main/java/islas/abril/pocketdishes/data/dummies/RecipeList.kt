import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.data.Ingredients
import islas.abril.pocketdishes.data.Recipe
import islas.abril.pocketdishes.data.enums.Units
import islas.abril.pocketdishes.data.getTag

val DummyRecipes = listOf(

    Recipe(
        name = "Chicken Teriyaki Bowl",
        Description = "Sweet and savory chicken with rice and veggies.",
        image = R.drawable.chicken_teriyaki,
        prepTime = 35,
        tags = listOf(getTag(3), getTag(9)), // Quick, Dinner
        ingredients = listOf(
            Ingredients("Chicken Breast", R.drawable.chicken, 200, Units.GR),
            Ingredients("Rice", R.drawable.rice, 1, Units.CUPS),
            Ingredients("Broccoli", R.drawable.broccoli, 1, Units.CUPS),
            Ingredients("Teriyaki Sauce", R.drawable.teriyaki, 3, Units.TBSP)
        ),
        steps = listOf(
            "Cook the rice.",
            "Grill chicken and slice it.",
            "Steam broccoli.",
            "Mix everything with teriyaki sauce."
        ),
        category = listOf("Dinner", "Asian Food"),
        secretRecipe = false,
        author = "Jorge Cuevas",
        rating = 3.0f
    ),

    Recipe(
        name = "Vegetarian Curry",
        Description = "Sweet and savory japanese curry with rice and veggies.",
        image = R.drawable.curry,
        prepTime = 45,
        tags = listOf(getTag(2), getTag(9)),
        ingredients = listOf(
            Ingredients("Lentils", R.drawable.lentils, 200, Units.GR),
            Ingredients("Rice", R.drawable.rice, 1, Units.CUPS),
            Ingredients("Potatoes", R.drawable.potatoes, 2, Units.PCS),
            Ingredients("Carrots", R.drawable.carrots, 2, Units.PCS),
            Ingredients("Curry paste", R.drawable.currypaste, 100, Units.GR)
        ),
        steps = listOf(
            "Cook the rice.",
            "Grill chicken and slice it.",
            "Steam broccoli.",
            "Mix everything with teriyaki sauce."
        ),
        category = listOf("Dinner", "Asian Food"),
        secretRecipe = false,
        author = "Jorge Cuevas",
        rating = 4.0f
    ),

    Recipe(
        name = "Classic Cheeseburger",
        Description = "Juicy beef burger with melted cheese.",
        image = R.drawable.cheeseburger,
        prepTime = 25,
        tags = listOf(getTag(9)), // Dinner
        ingredients = listOf(
            Ingredients("Ground Beef", R.drawable.beef, 150, Units.GR),
            Ingredients("Burger Bun", R.drawable.bun, 1, Units.PCS),
            Ingredients("Cheese", R.drawable.cheese, 1, Units.PCS),
            Ingredients("Lettuce", R.drawable.lettuce, 1, Units.PCS)
        ),
        steps = listOf(
            "Form beef patty.",
            "Cook on grill or pan.",
            "Assemble burger with ingredients.",
            "Serve hot."
        ),
        category = listOf("Fast Food", "Dinner"),
        secretRecipe = true,
        author = "Jorge Cuevas",
        rating = 1.0f
    ),
)

fun returnRandomRecipe(): Recipe {
    return DummyRecipes.random()
}

fun returnRecipes():List<Recipe>{
    return DummyRecipes
}