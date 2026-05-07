package islas.abril.pocketdishes.data.room

import androidx.room.TypeConverter
import islas.abril.pocketdishes.data.enums.Units

class Converters {

    @TypeConverter
    fun fromStringList(value: List<String>): String = value.joinToString(separator = "|")

    @TypeConverter
    fun toStringList(value: String): List<String> =
        if (value.isEmpty()) emptyList() else value.split("|")

    @TypeConverter
    fun fromUnits(unit: Units): String = unit.name

    @TypeConverter
    fun toUnits(value: String): Units = enumValueOf(value)
}
