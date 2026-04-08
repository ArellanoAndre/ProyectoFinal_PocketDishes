package islas.abril.pocketdishes.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import islas.abril.pocketdishes.ui.theme.darkGray
import islas.abril.pocketdishes.ui.theme.darkerGrey

@Composable
fun PrepTime(time: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp), // 🔥 fix
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Prep time", fontSize = 20.sp,  color = darkerGrey)
        Text("${time} minutes...", fontWeight = FontWeight.Bold, color = darkGray)
    }
}