package islas.abril.pocketdishes.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.data.Recipe
import islas.abril.pocketdishes.ui.theme.backgroundOrange
import islas.abril.pocketdishes.ui.theme.darkGray
import islas.abril.pocketdishes.ui.theme.mainOrange
import islas.abril.pocketdishes.ui.theme.secondaryGreen
import islas.abril.pocketdishes.ui.theme.typoColorBrown
import islas.abril.pocketdishes.ui.theme.typoColorLightBrown
import returnRandomRecipe

@Composable
fun favouriteRecipeCard(recipe: Recipe){

    Card(
        elevation = CardDefaults.cardElevation(7.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundOrange,

        ),
        modifier = Modifier
            .fillMaxWidth()
            .size(402.dp,105.dp)
            .clickable { /* navigate */ },
    )
    {
        Row( modifier = Modifier.padding(top=15.dp,start=15.dp, bottom=2.dp, end=15.dp)
            .fillMaxSize(),
        ){

            Image(
                painter = painterResource(recipe.image),
                contentDescription = recipe.name,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(3.dp, color = mainOrange, shape = RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop

            )
            Spacer(modifier= Modifier.size(16.dp))
            Column(
                modifier = Modifier.weight(1f)

            ){
                Text(
                    text=recipe.name,
                    color = typoColorBrown,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text= recipe.category.random(),
                    color = typoColorLightBrown,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold

                )
                recipeTags((recipe.tags))
            }
            Icon(
                painter = painterResource(id = R.drawable.more_vert_24px),
                contentDescription = "more",
                tint = typoColorBrown,
                modifier = Modifier.size(25.dp)
                .clickable { /* navigate */ }
            )
        }

    }

}

@Preview (showBackground = false)
@Composable
fun previewCard(){
    favouriteRecipeCard(
        returnRandomRecipe()
    )
}