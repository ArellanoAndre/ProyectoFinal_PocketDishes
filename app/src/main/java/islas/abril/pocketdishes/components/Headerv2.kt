package islas.abril.pocketdishes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import islas.abril.pocketdishes.ui.theme.darkGray

@Composable
fun headerV2(){
    Row(
        modifier = Modifier
            .padding(top = 30.dp, start =16.dp,end = 16.dp, bottom = 30.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = R.drawable.return_arrow),
            contentDescription = "goback",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
            .clickable{}
        )

        Icon(
            painter = painterResource(id = R.drawable.more_vert_24px),
            contentDescription = "more",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(30.dp)
                .clickable { /* navigate */ }
        )
    }
}

@Preview
@Composable
fun headerV2Preview(){
    headerV2()
}

