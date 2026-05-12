package islas.abril.pocketdishes.components

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import islas.abril.pocketdishes.R
import java.util.Calendar

fun showDatePicker(
    context: Context,
    onDateSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val dialog = DatePickerDialog(
        context,
        R.style.OrangeDatePickerTheme,
        { _, selectedYear, selectedMonth, selectedDay ->
            // Formateamos a dd/mm/yyyy
            val formattedDate = "${"%02d".format(selectedDay)}/${"%02d".format(selectedMonth + 1)}/$selectedYear"
            onDateSelected(formattedDate)
        },
        year, month, day
    )

    // No permitir seleccionar fechas futuras
    dialog.datePicker.maxDate = System.currentTimeMillis()

    dialog.show()

    val orange = Color.parseColor("#F38E3A")   // mainOrange (no me dejaba poner el color como tal no c pq)
    dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(orange)
    dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(orange)
}