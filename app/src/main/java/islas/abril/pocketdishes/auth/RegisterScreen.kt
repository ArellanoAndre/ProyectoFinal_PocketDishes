package islas.abril.pocketdishes.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import islas.abril.pocketdishes.ui.theme.*

@Composable
fun RegisterScreen(
    onRegisterClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val genderOptions = listOf("Female", "Male", "Other")

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
                .padding(horizontal = 20.dp, vertical = 24.dp)
                .widthIn(max = 340.dp)
        ) {

            Text(
                text = "PocketDishes.",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = mainOrange
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "The tiniest recipe book in the world!",
                fontSize = 12.sp,
                color = mainOrange
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Create account",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = typoColorBrown,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Label("Name")
            InputField(
                value = name,
                placeholder = "Abril Islas",
                onValueChange = { name = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Label("E-mail")
            InputField(
                value = email,
                placeholder = "abrilislas@hotmail.com",
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Column(modifier = Modifier.weight(1f)) {
                    Label("Birth date")
                    InputField(
                        value = birthDate,
                        placeholder = "dd/mm/yyyy",
                        onValueChange = { birthDate = it }
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Label("Gender")

                    Box {
                        InputField(
                            value = gender,
                            placeholder = "Female",
                            readOnly = true,
                            onValueChange = {},
                            trailingIcon = {
                                Text(
                                    text = "▼",
                                    fontSize = 10.sp,
                                    color = typoColorBrown
                                )
                            }
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            genderOptions.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it,
                                            color = typoColorBrown
                                        )
                                    },
                                    onClick = {
                                        gender = it
                                        expanded = false
                                    }
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(Color.Transparent)
                        ) {
                            TextButton(
                                onClick = { expanded = true },
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text("")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Label("Password")
            InputField(
                value = password,
                placeholder = "*************",
                isPassword = true,
                onValueChange = { password = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Label("Confirm password")
            InputField(
                value = confirmPassword,
                placeholder = "*************",
                isPassword = true,
                onValueChange = { confirmPassword = it }
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onRegisterClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.75f)
                    .height(44.dp),
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
        }
    }
}

@Composable
fun Label(text: String) {
    Column {
        Text(
            text = text,
            color = typoColorBrown,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
fun InputField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
        placeholder = {
            Text(
                text = placeholder,
                color = typoColorBeige,
                fontSize = 12.sp
            )
        },
        visualTransformation = if (isPassword) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        trailingIcon = trailingIcon,
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
            focusedContainerColor = lightPeach,
            unfocusedContainerColor = lightPeach,
            disabledContainerColor = lightPeach,

            focusedIndicatorColor = typoColorBeige,
            unfocusedIndicatorColor = typoColorBeige,
            disabledIndicatorColor = typoColorBeige,

            focusedTextColor = typoColorBrown,
            unfocusedTextColor = typoColorBrown,
            disabledTextColor = typoColorBrown,

            focusedPlaceholderColor = typoColorBeige,
            unfocusedPlaceholderColor = typoColorBeige,
            disabledPlaceholderColor = typoColorBeige,

            focusedTrailingIconColor = typoColorBrown,
            unfocusedTrailingIconColor = typoColorBrown,

            cursorColor = mainOrange
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    PocketDishesTheme {
        RegisterScreen(
            onRegisterClick = {},
            onBackClick = {}
        )
    }
}