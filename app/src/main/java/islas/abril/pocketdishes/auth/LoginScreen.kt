package islas.abril.pocketdishes.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import islas.abril.pocketdishes.ui.theme.PocketDishesTheme
import islas.abril.pocketdishes.ui.theme.backgroundLightTheme
import islas.abril.pocketdishes.ui.theme.backgroundOrange
import islas.abril.pocketdishes.ui.theme.lightPeach
import islas.abril.pocketdishes.ui.theme.mainOrange
import islas.abril.pocketdishes.ui.theme.typoColorBeige
import islas.abril.pocketdishes.ui.theme.typoColorBrown
import islas.abril.pocketdishes.ui.theme.typoColorLightBrown
import islas.abril.pocketdishes.ui.theme.white

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(backgroundLightTheme, backgroundOrange, mainOrange)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 38.dp)
                .shadow(10.dp, RoundedCornerShape(22.dp))
                .background(lightPeach, RoundedCornerShape(22.dp))
                .padding(horizontal = 22.dp, vertical = 26.dp)
                .widthIn(max = 340.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "PocketDishes.",
                style = TextStyle(
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = mainOrange
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "The tiniest recipe book in the world!",
                fontSize = 12.sp,
                color = mainOrange
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Log In",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = typoColorBrown,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "E-mail",
                color = typoColorBrown,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text(
                        text = "abrilislas@hotmail.com",
                        color = typoColorBeige,
                        fontSize = 12.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    color = typoColorBrown
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = white,
                    unfocusedContainerColor = white,
                    focusedIndicatorColor = typoColorBeige,
                    unfocusedIndicatorColor = typoColorBeige,
                    focusedTextColor = typoColorBrown,
                    unfocusedTextColor = typoColorBrown,
                    focusedPlaceholderColor = typoColorBeige,
                    unfocusedPlaceholderColor = typoColorBeige,
                    cursorColor = typoColorLightBrown
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Password",
                color = typoColorBrown,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = {
                    Text(
                        text = "*************",
                        color = typoColorBeige,
                        fontSize = 12.sp
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    color = typoColorBrown
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = white,
                    unfocusedContainerColor = white,
                    focusedIndicatorColor = typoColorBeige,
                    unfocusedIndicatorColor = typoColorBeige,
                    focusedTextColor = typoColorBrown,
                    unfocusedTextColor = typoColorBrown,
                    focusedPlaceholderColor = typoColorBeige,
                    unfocusedPlaceholderColor = typoColorBeige,
                    cursorColor = typoColorLightBrown
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = mainOrange
                )
            ) {
                Text(
                    text = "Log in",
                    color = white,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onRegisterClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = typoColorBrown
                )
            ) {
                Text(
                    text = "Create account",
                    color = white,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Forgot password?",
                color = mainOrange,
                fontSize = 11.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    PocketDishesTheme {
        LoginScreen(
            onLoginClick = {},
            onRegisterClick = {}
        )
    }
}