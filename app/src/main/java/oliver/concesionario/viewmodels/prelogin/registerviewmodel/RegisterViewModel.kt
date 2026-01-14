package oliver.concesionario.viewmodels.prelogin.registerviewmodel

import android.util.Patterns
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
        Email.value?.let { email ->
            if (CheckEmail(email)){
                Password.value?.let { password ->
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                ResetVariables()
                                _isRegisterSuccesfully.value = true
                            } else {
                                ResetVariables()
                                _isRegisterSuccesfully.value = false
                            }
                        }
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

    // Check email
    private fun CheckEmail(email: String): Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}