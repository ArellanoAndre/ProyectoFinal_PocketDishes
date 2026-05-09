package islas.abril.pocketdishes.screens

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.components.BottomNavigationMenu
import islas.abril.pocketdishes.components.ReadOnlyProfileField
import islas.abril.pocketdishes.components.headerV2
import islas.abril.pocketdishes.components.showDatePicker
import islas.abril.pocketdishes.ui.theme.mainOrange
import islas.abril.pocketdishes.ui.theme.orangeButton
import islas.abril.pocketdishes.ui.theme.white
import islas.abril.pocketdishes.viewmodel.PocketDishesViewModel

@Composable
fun ProfileScreen(
    viewModel: PocketDishesViewModel,
    navController: NavController,
    onLogout: () -> Unit
) {
    androidx.compose.material3.Scaffold(
        topBar = {
            headerV2(navController)
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .fillMaxWidth()
            ) {
                Box(modifier = Modifier.navigationBarsPadding()) {
                    BottomNavigationMenu(navController = navController)
                }
            }
        }
    ) { innerPadding ->

        val context = LocalContext.current
        val currentUser by viewModel.currentUser.collectAsState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 35.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                //  Encabezado
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    //verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Welcome,\n${currentUser?.name ?: "User"}.",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )

                    // Contenedor para los iconos a la derecha
                    Row(
                        modifier = Modifier.padding(top=10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(mainOrange)
                                .clickable { navController.navigate("secretrecipes")},
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_lock),
                                contentDescription = "Lock",
                                tint = white,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(mainOrange)
                                .clickable { onLogout() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "Logout",
                                tint = white,
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                // pfp
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = currentUser?.profilePicture,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(210.dp)
                            .clip(RoundedCornerShape(30.dp)),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.profilepicture),
                        error = painterResource(R.drawable.profilepicture)
                    )
                    Text(
                        text = "Update profile picture",
                        color = orangeButton,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickable { /* Accion */ }
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                // Info
                Text(
                    text = "Account info",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = mainOrange
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.weight(1.5f)) {
                        ReadOnlyProfileField(label = "Name", value = "Jorge")
                        ReadOnlyProfileField(label = "Name", value = currentUser?.name ?: "")
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        ReadOnlyProfileField(
                            label = "Birth date",
                            value = currentUser?.birthday ?: "",
                            modifier = Modifier.clickable {
                                showDatePicker(context) { nuevaFecha ->
                                    // Accion
                                }
                            }
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(mainOrange)
                            .clickable {
                                //
                            },
                        contentAlignment = Alignment.Center
                    ){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Edit info",
                        tint = white,
                        modifier = Modifier.size(30.dp).clickable { }
                    )
                        }
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Estadisticas
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(text = "Monthly statistics...", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = orangeButton)
                        Spacer(modifier = Modifier.height(15.dp))
                        Image(
                            painter = painterResource(id = R.drawable.graph),
                            contentDescription = "Stats Graph",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }

                // Spacer (para que se pueda hacer scroll)
                Spacer(modifier = Modifier.height(160.dp))
            }
    }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewProfileScreen() {
//    PocketDishesTheme() {
//        ProfileScreen(
//            navController = rememberNavController(),
//            onLogout = {}
//        )
//    }
//}
