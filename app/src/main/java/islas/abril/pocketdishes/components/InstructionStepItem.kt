package islas.abril.pocketdishes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import islas.abril.pocketdishes.ui.theme.backgroundOrange
import islas.abril.pocketdishes.ui.theme.darkGray

// Soporta dos formatos:
//  - Texto plano: "Cook the rice."          → se muestra solo como descripción
//  - Título|Desc: "Preparar|Lavar y picar." → se muestra título en negrita + descripción
// El parámetro onDelete es opcional: si se pasa, aparece el botón ✕ (para AddRecipeScreen).
@Composable
fun InstructionStepItem(
    index: Int,
    step: String,
    onDelete: (() -> Unit)? = null
) {
    val parts = step.split("|", limit = 2)
    val hasTitle = parts.size > 1 && parts[0].isNotBlank()
    val title = if (hasTitle) parts[0].trim() else ""
    val description = if (hasTitle) parts[1].trim() else step

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundOrange),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Número de paso
            Text(
                text = "${index + 1}.",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = darkGray,
                modifier = Modifier.padding(end = 12.dp, top = 2.dp)
            )

            // Contenido del paso
            Column(modifier = Modifier.weight(1f)) {
                if (hasTitle) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = darkGray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Text(
                    text = description,
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    color = Color.Black
                )
            }

            // Botón eliminar (solo en AddRecipeScreen)
            if (onDelete != null) {
                Text(
                    text = "✕",
                    color = Color.Red,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable { onDelete() }
                )
            }
        }
    }
}
