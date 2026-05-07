package islas.abril.pocketdishes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun InstructionStepItem(
    index: Int,
    step: String,
    onDelete: (() -> Unit)? = null
) {

    // 🔥 SEPARAR TÍTULO Y DESCRIPCIÓN
    val stepParts = step.split("|", limit = 2)

    val title = stepParts.getOrNull(0) ?: ""
    val description = stepParts.getOrNull(1) ?: ""

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {

        // 🔹 TÍTULO
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE5E5E5), RoundedCornerShape(8.dp))
                .padding(vertical = 6.dp, horizontal = 10.dp)
        ) {
            Text(
                text = "${index + 1}. $title",
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // 🔹 DESCRIPCIÓN
        Box {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF2E0D2), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = description,
                    color = Color(0xFF6E6E6E)
                )
            }

            // 🔥 BOTÓN ELIMINAR
            if (onDelete != null) {
                Text(
                    text = "✕",
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clickable { onDelete() }
                )
            }
        }
    }
}