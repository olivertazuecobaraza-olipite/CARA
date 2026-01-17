package oliver.concesionario.model

import androidx.compose.ui.graphics.Color
import com.google.firebase.firestore.Exclude
import oliver.concesionario.R

data class Car(
    val name: String = "",
    val type: String = "",
    val price: String = "",
    val km: Int = 0,
    val someSpecs: String = "Null Specs",
    val description: String = "Lorem ipsum...",
    val image: Long = R.drawable.ic_logo_cara.toLong(),
    val imageStr: String = "",
    val colors: Map<String, Any>? = null,

    // Use exclude to firestore do not get default color as param
    @get:Exclude
    val defaultColor: Color = Color.White
) {
    // to get the cars from firestore need a empty Conructor
    constructor() : this("", "", "", 0, "Null Specs", "Lorem ipsum...", R.drawable.ic_logo_cara.toLong(), "", null)
}