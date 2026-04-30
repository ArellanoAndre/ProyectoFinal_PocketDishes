package islas.abril.pocketdishes.components

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import java.util.Calendar

fun showDatePicker(
    context: Context,
    onDateSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            // Formateamos a dd/mm/yyyy
            val formattedDate = "${"%02d".format(selectedDay)}/${"%02d".format(selectedMonth + 1)}/$selectedYear"
            onDateSelected(formattedDate)
        },
        year, month, day
    ).show()
}