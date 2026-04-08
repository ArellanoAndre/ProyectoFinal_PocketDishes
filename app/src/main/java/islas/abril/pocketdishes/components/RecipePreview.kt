package islas.abril.pocketdishes.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import islas.abril.pocketdishes.data.Recipe
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.ui.theme.lightPeach
import islas.abril.pocketdishes.ui.theme.secondaryGreen
import islas.abril.pocketdishes.ui.theme.typoColorBrown
import returnRandomRecipe

@Composable
fun RecipePreviewCard(recipe: Recipe) {
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = lightPeach,

            ),
        modifier = Modifier
            .fillMaxWidth()
            .size(268.dp,200.dp)
            .clickable { /* navigate */ },
    ){
        Column(
            modifier = Modifier
            .fillMaxSize()
        ){
            Image(
                painter = painterResource(recipe.image),
                contentDescription = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .size( 100.dp),
                contentScale = ContentScale.FillWidth
            )

            Text(
                text= recipe.name,
                color = typoColorBrown,
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
               .padding(10.dp)
            )

            Row( modifier = Modifier
                .fillMaxWidth()
                .padding(start=10.dp , end=15.dp)
            ){
                recipeTags(recipe.tags)
                Spacer(modifier = Modifier.weight(1f))
                Row() {
                    Icon(
                        painter = painterResource(id = R.drawable.favorite_24px),
                        contentDescription = "Favorite",
                        tint = secondaryGreen,
                        modifier = Modifier.size(30.dp)
                            .clickable { /* navigate */ }
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.more_vert_24px),
                        contentDescription = "more",
                        tint = secondaryGreen,
                        modifier = Modifier.size(30.dp)
                            .clickable { /* navigate */ }
                    )
                }

            }

        }

    }
}

@Preview (showBackground = false)
@Composable
fun RecipePreview(){
    RecipePreviewCard(
        returnRandomRecipe()
    )
}
