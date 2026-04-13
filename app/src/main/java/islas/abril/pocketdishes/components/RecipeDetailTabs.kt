package islas.abril.pocketdishes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import islas.abril.pocketdishes.ui.theme.lightGray

//componente para detalle de receta (ingredientes y instrucciones)
//funcionalidad pendiente con sus atributos
@Composable
fun Tabs(
    selected: Int,
    onChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(lightGray)
    ) {

        TabButton("Ingredients", selected == 0) { onChange(0) }
        TabButton("Instructions", selected == 1) { onChange(1) }
    }
}