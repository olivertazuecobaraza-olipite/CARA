package oliver.concesionario


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


import oliver.concesionario.nav3.Navigation
import oliver.concesionario.ui.theme.ConcesionarioTheme
import oliver.concesionario.viewmodels.postlogin.profileviewmodel.ProfileViewModel
import oliver.concesionario.viewmodels.prelogin.initviewmodel.InitViewModel
import oliver.concesionario.viewmodels.prelogin.loginviewmodel.LoginViewModel
import oliver.concesionario.viewmodels.prelogin.registerviewmodel.RegisterViewModel

private lateinit var auth: FirebaseAuth
var isLogg: Boolean = false


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        auth = Firebase.auth
        //enableEdgeToEdge()
        setContent {
            ConcesionarioTheme {

                Navigation(auth = auth,
                    isUserLoggin = isLogg,
                    InitViewModel(),
                    LoginViewModel(auth),
                    RegisterViewModel(auth),
                    ProfileViewModel(auth)

                )
            }
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



