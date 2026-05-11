package islas.abril.pocketdishes.screens

import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.components.BottomNavigationMenu
import islas.abril.pocketdishes.components.favouriteRecipeCard
import islas.abril.pocketdishes.components.headerV2
import islas.abril.pocketdishes.data.room.toRecipe
import islas.abril.pocketdishes.ui.theme.backgroundDarkTheme
import islas.abril.pocketdishes.ui.theme.brightIndigo
import islas.abril.pocketdishes.viewmodel.PocketDishesViewModel

@Composable
fun SecretRecipeScreen(navController: NavController, viewModel: PocketDishesViewModel) {
    var isAuthenticated by remember { mutableStateOf(false) }
    var authTrigger by remember { mutableIntStateOf(0) }

    val context = LocalContext.current
    val activity = context as? FragmentActivity
    val secretRecipes by viewModel.secretRecipes.collectAsState()
    val secretRecipesDisplay = secretRecipes.map { it.toRecipe(context) }

    LaunchedEffect(authTrigger) {
        if (activity == null) {
            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            return@LaunchedEffect
        }

        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    isAuthenticated = true
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(context, errString, Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Secret Recipes")
            .setSubtitle("Authenticate to access your private vault")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
            .build()

        val biometricManager = BiometricManager.from(activity)
        if (biometricManager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            ) == BiometricManager.BIOMETRIC_SUCCESS
        ) {
            biometricPrompt.authenticate(promptInfo)
        } else {
            Toast.makeText(context, "Biometrics not available", Toast.LENGTH_LONG).show()
        }
    }

    // cuando es caso de exito
    if (isAuthenticated) {
        Scaffold(
            topBar = { headerV2(navController) },
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
            val currentUser by viewModel.currentUser.collectAsState()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundDarkTheme)
                    .padding(innerPadding)
            ) {
                Column(modifier = Modifier.padding(horizontal = 30.dp)) {
                    Text(
                        text = "${currentUser?.name ?: ""}\nSecret recipes",
                        style = MaterialTheme.typography.titleLarge,
                        color = brightIndigo,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    // Key Icons Row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 10.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.vpn_key_24px),
                            contentDescription = null,
                            tint = brightIndigo,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(Modifier.width(10.dp))
                        repeat(3) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_circle_24),
                                contentDescription = null,
                                tint = brightIndigo,
                                modifier = Modifier.size(20.dp).padding(horizontal = 2.dp)
                            )
                        }
                    }

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(bottom = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(secretRecipesDisplay) { recipe ->
                            favouriteRecipeCard(
                                recipe = recipe,
                                brightIndigo,
                                onCardClick = { navController.navigate("detail/${recipe.name}") }
                            )
                        }
                        item {
                            Text(
                                text = "No more items to show",
                                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                color = Color.Gray.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            }
        }
    } else {
        // reintento o si falla
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.vpn_key_24px),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = brightIndigo
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Authentication required",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = { authTrigger++ }, // este valor sube para lanzar de nuevo el launch effect
                    colors = ButtonDefaults.buttonColors(containerColor = brightIndigo)
                ) {
                    Text("Retry Authentication")
                }

                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Go Back", color = MaterialTheme.colorScheme.outline)
                }
            }
        }
    }
}