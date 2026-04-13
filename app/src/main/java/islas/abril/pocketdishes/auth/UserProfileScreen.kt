package islas.abril.pocketdishes.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.components.BottomNavigationMenu
import islas.abril.pocketdishes.ui.theme.PocketDishesTheme
import islas.abril.pocketdishes.ui.theme.backgroundLightTheme
import islas.abril.pocketdishes.ui.theme.backgroundOrange
import islas.abril.pocketdishes.ui.theme.lightPeach
import islas.abril.pocketdishes.ui.theme.mainOrange
import islas.abril.pocketdishes.ui.theme.typoColorBeige
import islas.abril.pocketdishes.ui.theme.typoColorBrown
import islas.abril.pocketdishes.ui.theme.white

@Composable
fun UserProfileScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavigationMenu(navController = navController)
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(backgroundLightTheme, backgroundOrange)
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Welcome,\nAbril Islas.",
                        style = TextStyle(
                            fontSize = 27.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 28.sp,
                            color = mainOrange
                        )
                    )

                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Lock",
                        tint = mainOrange,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .width(34.dp)
                            .height(34.dp)
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "Profile photo",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(200.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(22.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Update profile picture",
                    color = mainOrange,
                    fontSize = 9.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Account info",
                    color = mainOrange,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Name",
                            color = typoColorBrown,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(lightPeach, RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Abril Islas",
                                color = typoColorBeige,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Birth date",
                            color = typoColorBrown,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(lightPeach, RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "15/12/2005",
                                color = typoColorBrown,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit",
                        tint = typoColorBrown,
                        modifier = Modifier
                            .width(18.dp)
                            .height(18.dp)
                            .padding(bottom = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(255.dp)
                        .background(white, RoundedCornerShape(22.dp))
                        .padding(horizontal = 18.dp, vertical = 20.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Monthly statistics...",
                            color = mainOrange,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Image(
                            painter = painterResource(id = R.drawable.stats_chart),
                            contentDescription = "chart",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(115.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserProfileScreenPreview() {
    PocketDishesTheme {
        val navController = rememberNavController()
        UserProfileScreen(navController = navController)
    }
}