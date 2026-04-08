package islas.abril.pocketdishes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import islas.abril.pocketdishes.data.Tag
import islas.abril.pocketdishes.ui.theme.typoColorBrown
import returnRandomRecipe

@Composable
fun recipeTags(tagList:List<Tag>){
    Row(
        modifier = Modifier
            .padding(3.dp),
            verticalAlignment = Alignment.CenterVertically


    ) {
        tagList.forEach { tag ->
            Box(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .shadow(5.dp, RoundedCornerShape(3.dp))
                    .clip(RoundedCornerShape(7.dp))
                    .background(tag.color)
                    .size(80.dp, 25.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text= tag.name,
                    color = typoColorBrown,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )

            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun previewTags(){
    recipeTags(
        returnRandomRecipe().tags
    )
}