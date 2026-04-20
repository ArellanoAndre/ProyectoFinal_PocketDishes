package islas.abril.pocketdishes.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import islas.abril.pocketdishes.data.Ingredients
import islas.abril.pocketdishes.ui.theme.lightIndigo
import islas.abril.pocketdishes.ui.theme.tertiaryIndigo

@Composable
fun IngredientCard(
    ingredient: Ingredients
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = lightIndigo
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 7.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del ingrediente
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
            ) {
                ingredient.image?.let {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.width(15.dp))

            // Textos
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = ingredient.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = tertiaryIndigo
                )
                Text(
                    text = "${ingredient.amount} ${ingredient.unit.name}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}