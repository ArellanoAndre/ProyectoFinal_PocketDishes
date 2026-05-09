package islas.abril.pocketdishes.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import islas.abril.pocketdishes.data.room.dao.IngredientDao
import islas.abril.pocketdishes.data.room.dao.IngredientRecipeDao
import islas.abril.pocketdishes.data.room.dao.RecipeDao
import islas.abril.pocketdishes.data.room.dao.RecipeStepDao
import islas.abril.pocketdishes.data.room.dao.UserDao
import islas.abril.pocketdishes.data.room.entities.IngredientEntity
import islas.abril.pocketdishes.data.room.entities.IngredientRecipeEntity
import islas.abril.pocketdishes.data.room.entities.RecipeEntity
import islas.abril.pocketdishes.data.room.entities.RecipeStepEntity
import islas.abril.pocketdishes.data.room.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        RecipeEntity::class,
        IngredientEntity::class,
        IngredientRecipeEntity::class,
        RecipeStepEntity::class
    ],
    version = 2,
    exportSchema = false
)


@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun ingredientRecipeDao(): IngredientRecipeDao
    abstract fun recipeStepDao(): RecipeStepDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pocket_dishes_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
