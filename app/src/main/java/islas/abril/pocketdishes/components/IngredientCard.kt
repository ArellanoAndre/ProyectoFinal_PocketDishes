package islas.abril.pocketdishes.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import islas.abril.pocketdishes.data.Ingredients

@Composable
fun IngredientCard(ingredient: Ingredients) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFD6A0), RoundedCornerShape(12.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 🔥 IMAGEN (SIN NULLABLE)
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = ingredient.image),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // 🔹 NOMBRE
        Text(
            text = ingredient.name,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp
        )

        // 🔹 CANTIDAD
        Box(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text("${ingredient.amount}")
        }

        Spacer(modifier = Modifier.width(5.dp))

        // 🔹 UNIDAD
        Box(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(ingredient.unit.name)
        }
    }
}