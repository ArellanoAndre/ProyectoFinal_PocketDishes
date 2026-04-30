package islas.abril.pocketdishes.data.dummies

import islas.abril.pocketdishes.R
import islas.abril.pocketdishes.data.Profile

// Función mock para generar el perfil de prueba
fun returnProfile(): Profile {
    return Profile(
        name = "Abril Islas",
        birthDate = "15/12/2005",
        imageRes = R.drawable.profilepicture,
        statsGraphRes = R.drawable.graph
    )
}