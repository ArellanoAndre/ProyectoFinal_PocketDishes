package islas.abril.pocketdishes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import islas.abril.pocketdishes.components.GenderDropdown
import islas.abril.pocketdishes.components.LoginTextField
import islas.abril.pocketdishes.ui.theme.darkBrown
import islas.abril.pocketdishes.ui.theme.gradientEnd
import islas.abril.pocketdishes.ui.theme.gradientStart
import islas.abril.pocketdishes.ui.theme.lightPeach
import islas.abril.pocketdishes.ui.theme.mainOrange
import islas.abril.pocketdishes.ui.theme.orangeButton

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    // Estados para cada campo
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Female") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(MaterialTheme.colorScheme.background, gradientEnd)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.85f), // Un poco más alta que la de login
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = lightPeach),
            elevation = CardDefaults.cardElevation(7.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp)
                    .verticalScroll(rememberScrollState()), //para que quepan todos los campos
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                // Titulo
                Text(
                    text = "PocketDishes.",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = mainOrange
                )
                // Subtitulo
                Text(
                    text = "The tiniest recipe book in the world!",
                    fontSize = 14.sp,
                    color = mainOrange.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(45.dp))

                Text(
                    text = "Create account",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkBrown
                )

                Spacer(modifier = Modifier.height(45.dp))

                // nombre
                LoginTextField(
                    label = "Name",
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "Fulanito"
                )

                Spacer(modifier = Modifier.height(15.dp))

                // email
                LoginTextField(
                    label = "E-mail",
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "fulanito@hotmail.com"
                )

                Spacer(modifier = Modifier.height(15.dp))

                // fila fecha (actualmente lo deje como string cambiar a algo mejor) y genero
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        LoginTextField(
                            label = "Birth date",
                            value = birthDate,
                            onValueChange = { birthDate = it },
                            placeholder = "dd/mm/yyyy"
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        GenderDropdown(
                            selectedGender = gender,
                            onGenderSelected = { gender = it }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                // password
                LoginTextField(
                    label = "Password",
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "************",
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(15.dp))

                // confirmar password
                LoginTextField(
                    label = "Confirm password",
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = "************",
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(30.dp))

                // boton crear cuenta
                Button(
                    onClick = { onRegisterSuccess() },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = darkBrown),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Create account", fontSize = 18.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Boton para volver al Login si ya tiene cuenta
                Text(
                    text = "Already have an account? Log In",
                    color = darkBrown.copy(alpha = 0.6f),
                    fontSize = 14.sp,
                    modifier = Modifier.clickable() { onBackToLogin() }
                )

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}