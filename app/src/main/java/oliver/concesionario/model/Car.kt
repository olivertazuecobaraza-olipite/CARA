package oliver.concesionario.model

import androidx.compose.ui.graphics.Color
import oliver.concesionario.R

data class Car(
    val name: String,
    val type: String,
    val km: Int = 0,
    val someSpecs: String = "Null Specs",
    val price: String,
    val colors: MutableList<Color> = mutableListOf(),
    val defaultColor: Color = Color.White,
    val description: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    val image: Int = R.drawable.ic_logo_cara,
    val imageStr: String = "" // URL

)