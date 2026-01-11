package oliver.concesionario.pages.prelogin.init

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oliver.concesionario.R
import oliver.concesionario.viewmodels.prelogin.initviewmodel.InitViewModel

// Start app
@Composable
fun InitScreen(initViewModel: InitViewModel,
               navToLogin: () -> Unit = {},
               navToRegister: () -> Unit = {}, )
{
    Scaffold(containerColor = Color(0xffE2E3E3), modifier = Modifier.fillMaxSize())
    { paddingValues ->
        Column(Modifier.padding(paddingValues)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier.weight(1f))
            LogoImage()

            Spacer(modifier = Modifier.weight(1f))
            Texts()

            Spacer(modifier = Modifier.weight(1f))
            AuthBottons(
                navToLogin = { initViewModel.onLoginButtonSelected(navToLogin)},
                navToRegister = { initViewModel.onRegisterButtonSelected(navToRegister) }
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

// Image Logo
@Composable
fun LogoImage(){
    Image(painter = painterResource(R.drawable.ic_logo_cara),
        contentDescription = "",
        modifier = Modifier.clip(CircleShape),
        contentScale = ContentScale.Crop)
}

// Text Name...
@Composable
fun Texts(){
    Text(text = "CARA", fontSize = 48.sp, fontWeight = FontWeight.Bold)
    Text(text = "Your journey starts here", fontSize = 22.sp)
}

// Botones
@Composable
fun AuthBottons(navToLogin: () -> Unit, navToRegister: () -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LoginButton(navToLogin = { navToLogin() })
        Spacer(Modifier.padding(8.dp))
        RegisterButton(navToRegister = { navToRegister() })
    }
}

// Atomic Login Button
@Composable
private fun LoginButton(navToLogin : () -> Unit){
    val gradiant = Brush.horizontalGradient(
        listOf(Color(0xff4A90E2), Color(0xff8ECAFE))
    )

    Button(onClick = { navToLogin() },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = Modifier
            .width(160.dp)
            .height(48.dp)
            .background(gradiant, CircleShape)
        )
    {
        Text(
            text = "LOG IN",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

// Atomic Register Button
@Composable
private fun RegisterButton(navToRegister : () -> Unit){
    OutlinedButton(
        onClick = { navToRegister() },
        shape = CircleShape,
        border = BorderStroke(2.dp, Color(0xFF4A90E2)),
        modifier = Modifier
            .width(160.dp)
            .height(48.dp)
    ) {
        Text(
            text = "REGISTER",
            color = Color(0xFF4A90E2),
            fontWeight = FontWeight.Bold
        )
    }
}
