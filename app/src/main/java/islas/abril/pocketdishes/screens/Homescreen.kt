package islas.abril.pocketdishes.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.components.BottomNavigationMenu
import islas.abril.pocketdishes.components.favouriteRecipeCard
import islas.abril.pocketdishes.ui.theme.PocketDishesTheme
import islas.abril.pocketdishes.ui.theme.backgroundLightTheme
import islas.abril.pocketdishes.ui.theme.mainOrange
import islas.abril.pocketdishes.ui.theme.secondaryGreen
import islas.abril.pocketdishes.ui.theme.typoColorBrown
import returnRecipes

@Composable
fun homescreen() {
    androidx.compose.material3.Scaffold(
        bottomBar = {
            BottomNavigationMenu()
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            //barra iconos
            Row(
                modifier = Modifier
                    .padding(top=30.dp, end=25.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(id = R.drawable.search_24px),
                    contentDescription = "search",
                    tint = mainOrange,
                    modifier = Modifier.size(30.dp)
                        .clickable { /* navigate */ }
                )
                Icon(
                    painter = painterResource(id = R.drawable.more_vert_24px),
                    contentDescription = "more",
                    tint = mainOrange,
                    modifier = Modifier.size(30.dp)
                        .clickable { /* navigate */ }
                )

            }
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top=75.dp)
            ) {
                Text(
                    text = "New Recipes, \neveryday.",
                    style = MaterialTheme.typography.titleLarge,
                    color = mainOrange
                )
                Row(
                    modifier = Modifier
                        .padding(start = 0.dp, top = 15.dp, end = 10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sushi),
                        contentDescription = "imagen01",
                        modifier = Modifier
                            .size(260.dp, 180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.FillWidth,
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = R.drawable.macarons),
                        contentDescription = "imagen01",
                        modifier = Modifier
                            .size(130.dp, 180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.FillHeight,
                    )
                }
                Row(
                    Modifier.padding(top = 7.dp, end = 15.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Explore recipes",
                        color = typoColorBrown,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        modifier = Modifier
                        .clickable{}
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(bottom = 15.dp),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        text = "My recipes",
                        color = secondaryGreen,
                        fontWeight = FontWeight.Medium,
                        fontSize = 25.sp
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.filter_alt_24px),
                        contentDescription = "more",
                        tint = secondaryGreen,
                        modifier = Modifier.size(27.dp)
                            .clickable { /* navigate */ }
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.favorite_24px),
                        contentDescription = "more",
                        tint = secondaryGreen,
                        modifier = Modifier.size(27.dp)
                            .clickable { /* navigate */ }
                    )
                }
                val recipes = returnRecipes()

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(
                        top = 8.dp,
                        bottom = 100.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(recipes) {
                        favouriteRecipeCard(it)
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun homescreenPreview(){
    PocketDishesTheme {
        homescreen()
    }
}