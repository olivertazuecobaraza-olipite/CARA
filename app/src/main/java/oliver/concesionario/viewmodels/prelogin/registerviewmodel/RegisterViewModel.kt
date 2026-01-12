package oliver.concesionario.viewmodels.prelogin.registerviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel(val auth: FirebaseAuth) {
    // Email
    private var _email = MutableLiveData<String>()
    var Email: LiveData<String> = _email

    // Password
    private var _password = MutableLiveData<String>()
    var Password: LiveData<String> = _password

    // IsPasswordVisible
    private var _isPswVisible = MutableLiveData<Boolean>()
    var IsPswVisible: LiveData<Boolean> = _isPswVisible

    // Regist Succesfully
    private var _isRegisterSuccesfully = MutableLiveData<Boolean>()
    var IsRegisterSuccesfully: LiveData<Boolean> = _isRegisterSuccesfully

    // email Change
    fun EmailChange(emailChanged: String){
        _email.value = emailChanged
    }

    // Password Changed
    fun PasswordChanged(passwordChanged: String){
        _password.value = passwordChanged
    }

    // clicl on register
    fun OnRegisterSelected() {
        val eml: String = _email.value as String
        val psw: String = _password.value as String

        if (eml.isNotEmpty() && psw.isNotEmpty()){
            auth.createUserWithEmailAndPassword(eml, psw)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        _isRegisterSuccesfully.value = true
                        ResetVariables()
                    } else {
                        _isRegisterSuccesfully.value = false
                        ResetVariables()
                    }
                }
        }


    }

    // On Clic password visible
    fun OnPasswordVisibleClic(){
        _isPswVisible.value = !(_isPswVisible.value ?: false)
    }

    // Reset variables
    fun ResetVariables(){
        _email.value = ""
        _password.value = ""
    }

}