package oliver.concesionario.pages.postlogin.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(auth: FirebaseAuth,
                navToInit: () -> Unit){
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues = paddingValues)
            .fillMaxWidth())
        {
            Button(onClick = {
                auth.signOut()
                navToInit()
            })
            {
                Text(text = "Cerrar Sesion")
            }
        }

    }
}
