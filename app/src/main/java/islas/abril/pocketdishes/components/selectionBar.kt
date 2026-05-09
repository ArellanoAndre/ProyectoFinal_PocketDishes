package islas.abril.pocketdishes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import islas.abril.pocketdishes.R

@Composable
fun selectionBar(text:String, onFavoriteClick: () -> Unit) {
    Row(
        modifier = Modifier.padding(top = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelMedium
            )
            Icon(
                painter = painterResource(id = R.drawable.filter_alt_24px),
                contentDescription = "more",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(35.dp)
            )

            Icon(
                painter = painterResource(id = R.drawable.favorite_24px),
                contentDescription = "heart",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        onFavoriteClick()
                    }
            )
    }
}
