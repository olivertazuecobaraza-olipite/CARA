package oliver.concesionario.viewmodels.prelogin.loginviewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(val auth: FirebaseAuth ) : ViewModel() {
    // Email; Observable
    private var _email = MutableLiveData<String>()
    var Email: LiveData<String> = _email

    // Password; Observable
    private var _password = MutableLiveData<String>()
    var Password: LiveData<String> = _password

    // IsLog
    private var _isLogged = MutableLiveData<Boolean>()
    var IsLogged: LiveData<Boolean> = _isLogged

    // visibility change
    private var _isPasswordVisible = MutableLiveData<Boolean>()
    var IspasswordVisible: LiveData<Boolean> = _isPasswordVisible

    // Change
    fun isLoginChange(email: String, password: String){
        _email.value = email
        _password.value = password
    }


    // LoginClick
    fun onLoginSelected(){
        Email.value?.let { email ->
            Password.value?.let { password ->
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        _isLogged.value = true
                    } else {
                        _isLogged.value = false
                    }
                }
            }
        }
    }

    // Visibility clic
    fun onPasswordVisibilityChange(){
        _isPasswordVisible.value = !(_isPasswordVisible.value ?: false)
    }

    // Tools
     fun ResetEmailPassw(){
        _email.value = ""
        _password.value = ""
        _isLogged.value = false
    }
}