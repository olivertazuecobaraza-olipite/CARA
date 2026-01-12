package oliver.concesionario.model

import oliver.concesionario.R

data class Car(
    val name: String,
    val type: String,
    val price: String,
    val image: Int = R.drawable.ic_logo_cara,
    val imageStr: String = ""
)