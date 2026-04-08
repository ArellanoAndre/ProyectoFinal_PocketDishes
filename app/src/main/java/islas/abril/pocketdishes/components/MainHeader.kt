package islas.abril.pocketdishes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.ui.theme.PocketDishesTheme
import islas.abril.pocketdishes.ui.theme.mainOrange

@Composable
fun header() {
    Row(
        modifier = Modifier
            .padding(top = 30.dp, end = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        SearchBar(
            query = "",
            onQueryChange = {},
            modifier = Modifier
            .weight(1f)
            .padding(16.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.more_vert_24px),
            contentDescription = "more",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(30.dp)
                .clickable { /* navigate */ }
        )
    }
}

@Preview (showBackground = false)
@Composable
fun previewHeader(){
    PocketDishesTheme {
        header()
    }
}
