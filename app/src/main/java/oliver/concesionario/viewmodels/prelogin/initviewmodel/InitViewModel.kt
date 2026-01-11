package oliver.concesionario.viewmodels.prelogin.initviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InitViewModel : ViewModel() {

    // Button login
    fun onLoginButtonSelected(navToLogin : () -> Unit){
        navToLogin()
    }

    // Button Register
    fun onRegisterButtonSelected(navToRegister: () -> Unit){
        navToRegister()
    }
}