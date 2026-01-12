package oliver.concesionario.viewmodels.postlogin.profileviewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel(val auth: FirebaseAuth) : ViewModel() {


    // Cerrar Sesion
    fun CerrarSesion(){
        auth.signOut()
    }
}