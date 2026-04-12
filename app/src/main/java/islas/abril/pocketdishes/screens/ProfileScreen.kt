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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.components.BottomNavigationMenu
import islas.abril.pocketdishes.components.ReadOnlyProfileField
import islas.abril.pocketdishes.data.Profile
import islas.abril.pocketdishes.ui.theme.LightGreenMenu
import islas.abril.pocketdishes.ui.theme.darkBrown
import islas.abril.pocketdishes.ui.theme.gradientEnd
import islas.abril.pocketdishes.ui.theme.gradientStart
import islas.abril.pocketdishes.ui.theme.mainOrange
import islas.abril.pocketdishes.ui.theme.orangeButton

@Composable
fun ProfileScreen(
    profile: Profile,
    navController: NavController,
    onLogout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(gradientStart, gradientEnd)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 35.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.statusBarsPadding())
            Spacer(modifier = Modifier.height(20.dp))

            // ENCABEZADO
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Welcome,\n${profile.name}.",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = mainOrange,
                    lineHeight = 36.sp,
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = "Logout",
                    tint = orangeButton,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onLogout() }
                )
            }

            // IMAGEN DE PERFIL
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = profile.imageRes),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(200.dp)
                        .clip(RoundedCornerShape(30.dp)),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "Update profile picture",
                    color = orangeButton,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable { /* Accion para cambiar foto */ }
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            // ACCOUNT INFO (todavia no se pueden editar las cosas)
            Text(
                text = "Account info",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = orangeButton
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Box(modifier = Modifier.weight(1.5f)) {
                    ReadOnlyProfileField(label = "Name", value = profile.name)
                }
                Box(modifier = Modifier.weight(1f)) {
                    ReadOnlyProfileField(label = "Birth date", value = profile.birthDate)
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Edit info",
                    tint = darkBrown,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { /* Abrir edicion */ }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // ESTADISTSICAS
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Monthly statistics...",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = orangeButton
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Image(
                        painter = painterResource(id = profile.statsGraphRes),
                        contentDescription = "Stats Graph",
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }

            Spacer(modifier = Modifier.height(130.dp))
        }

        // MENU DE NAVEGACION
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(LightGreenMenu)
        ) {
            Box(modifier = Modifier.navigationBarsPadding()) {
                BottomNavigationMenu(navController = navController)
            }
        }
    }
}