package islas.abril.pocketdishes.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.ui.theme.secondaryGreen

@Composable
//tiene que recibir una lista
fun favouriteRecipeCard(){

    Card(
        modifier = Modifier
            .size(402.dp,100.dp)
    )
    {
        Row(){
            Image(
                painter = painterResource(id=R.drawable.home_24px),
                contentDescription = "hola"

            )
            Column(){
                Text("Falafel")
                Text("Arabian Food")

            }
            Icon(
                painter = painterResource(id = R.drawable.more_vert_24px),
                contentDescription = "Home button",
                tint = secondaryGreen,
                modifier = Modifier.size(35.dp)
            )
        }

    }

}

@Preview (showBackground = true)
@Composable
fun previewCard(){
    favouriteRecipeCard()
}