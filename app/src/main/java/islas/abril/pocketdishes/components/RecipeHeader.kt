package islas.abril.pocketdishes.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.data.Recipe
import islas.abril.pocketdishes.ui.theme.backgroundOrange
import islas.abril.pocketdishes.ui.theme.darkGray
import islas.abril.pocketdishes.ui.theme.secondaryGreen
import returnRandomRecipe
import kotlin.math.roundToInt


//PREVIEW TEMPORAL CON DATOS MOCK
@Preview(showBackground = true)
@Composable
fun Preview() {
    RecipeHeader(returnRandomRecipe())
}

@Composable
fun RecipeHeader(
    recipe: Recipe
) {
    //tamaño del header
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {

        //imagen de fondo
        Image(
            painter = painterResource(recipe.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // overlay para hacer mas oscura la imagen de fondo
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black.copy(0.7f))
                    )
                )
        )

        // Boton regresar
        Box(
            modifier = Modifier
                .statusBarsPadding() // padding del sistema (para que no choque con la barra de arriba del celular)
                .padding(start = 10.dp, top = 10.dp) // padding general
                .size(40.dp) // tamaño
                .background(backgroundOrange.copy(0.9f), CircleShape) //background (circulo)
                .clip(CircleShape)
                .clickable { /* navigate */ }
                .align(Alignment.TopStart),
            contentAlignment = Alignment.Center
        ) {
            //flecha
            Icon(
                painter = painterResource(id = R.drawable.return_arrow),
                contentDescription = "goback",
                tint = darkGray,
                modifier = Modifier.size(24.dp) // tamaño de la flecha
            )
        }

        Row(
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 10.dp)
                .fillMaxWidth(), // Para que el align funcione correctamente
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Boton favorito
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clickable { /* navigate */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.favorite_24px),
                    contentDescription = "favorite",
                    tint = secondaryGreen,
                    modifier = Modifier.size(35.dp)
                )
            }
            // Boton tres puntos
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clickable { /* navigate */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.more_vert_24px),
                    contentDescription = "more",
                    tint = secondaryGreen,
                    modifier = Modifier.size(35.dp)
                )
            }
        }

        // Titulo, autor, y rating
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TITULO
                Text(
                    text = recipe.name,
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false)
                )
                //espacio para rating
                Spacer(modifier = Modifier.width(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //rating (texto)
                    Text(
                        text = recipe.rating.toString(),
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                    //icono estrella rellena
                    Icon(
                        painter = painterResource(id = R.drawable.star_filled),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
            // AUTOR
            Text(
                "By ${recipe.author}",
                color = Color.White.copy(0.8f) // para transparentar un poco el texto
            )

            Spacer(modifier = Modifier.height(8.dp))

            // TAGS
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                recipeTags(recipe.tags)
            }

            Spacer(modifier = Modifier.height(6.dp))

            // maximo de estrellas
            val maxStars = 5
            // numero de estrellas a llenar (segun el rating usando coerce in para verificar que este entre 0 y 5)
            val filledStars = recipe.rating.coerceIn(0f, 5f).roundToInt()
            // numero de estrellas vacias (se resta el numero de estrellas llenas al maximo de estrellas)
            val emptyStars = maxStars - filledStars

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    -5.dp,
                    Alignment.End
                )
            ) {
                //estrellas llenas
                repeat(filledStars) {
                    Icon(
                        painter = painterResource(id = R.drawable.star_filled),
                        contentDescription = null,
                        tint = Color(0xFFFFA000),
                        modifier = Modifier.size(28.dp)
                    )
                }

                //estrellas vacías
                repeat(emptyStars) {
                    Icon(
                        painter = painterResource(id = R.drawable.star_outlined),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}