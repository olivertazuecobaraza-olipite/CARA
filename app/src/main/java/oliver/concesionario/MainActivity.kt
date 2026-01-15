package oliver.concesionario


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


import oliver.concesionario.nav3.Navigation
import oliver.concesionario.viewmodels.postlogin.homeviewmodel.HomeViewModel
import oliver.concesionario.viewmodels.postlogin.profileviewmodel.ProfileViewModel
import oliver.concesionario.viewmodels.prelogin.initviewmodel.InitViewModel
import oliver.concesionario.viewmodels.prelogin.loginviewmodel.LoginViewModel
import oliver.concesionario.viewmodels.prelogin.registerviewmodel.RegisterViewModel

val auth: FirebaseAuth = Firebase.auth
@SuppressLint("StaticFieldLeak")
val db: FirebaseFirestore = Firebase.firestore
var isLogg: Boolean = false


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        //enableEdgeToEdge()
        setContent {
            Navigation(auth = auth,
                database = db,
                isUserLoggin = isLogg,
                InitViewModel(),
                LoginViewModel(auth),
                RegisterViewModel(auth),
                ProfileViewModel(auth),
                HomeViewModel(db)
            )
        }
    }

    // Si ya se ha logueado una vez
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        currentUser?.let {
            isLogg = true
        }

    }
}



