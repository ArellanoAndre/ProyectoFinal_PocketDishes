package islas.abril.pocketdishes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import islas.abril.pocketdishes.components.LoginTextField
import islas.abril.pocketdishes.ui.theme.darkBrown
import islas.abril.pocketdishes.ui.theme.gradientEnd
import islas.abril.pocketdishes.ui.theme.gradientStart
import islas.abril.pocketdishes.ui.theme.lightPeach
import islas.abril.pocketdishes.ui.theme.mainOrange
import islas.abril.pocketdishes.ui.theme.orangeButton

// PANTALLA LOGIN
// **PENDIENTE AGREGAR SHARED PREFERENCES PARA PERMANECER LOGEADO Y ASI**
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // background (gradient)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(gradientStart, gradientEnd)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // card central
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = lightPeach),
            elevation = CardDefaults.cardElevation(7.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 40.dp, horizontal = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // titulo
                Text(
                    text = "PocketDishes.",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = mainOrange
                )
                // subtitulo
                Text(
                    text = "The tiniest recipe book in the world!",
                    fontSize = 14.sp,
                    color = mainOrange.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(30.dp))

                //texto login
                Text(
                    text = "Log In",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkBrown
                )

                Spacer(modifier = Modifier.height(25.dp))

                // email
                LoginTextField(
                    label = "E-mail",
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "tu_correo@hotmail.com"
                )

                Spacer(modifier = Modifier.height(15.dp))

                // password
                LoginTextField(
                    label = "Password",
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "************",
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(30.dp))

                // boton login
                Button(
                    onClick = { onLoginSuccess() },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = orangeButton),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Log in", fontSize = 18.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // boton crear cuenta
                Button(
                    // **PENDIENTE**
                    onClick = { onNavigateToRegister() },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = darkBrown),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Create account", fontSize = 18.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(15.dp))

                // olvide contraseña
                Text(
                    text = "Forgot password?",
                    color = orangeButton,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable() { /* **PENDIENTE** */ }
                )
            }
        }
    }
}