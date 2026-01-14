package oliver.concesionario.pages.postlogin.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import oliver.concesionario.R
import oliver.concesionario.viewmodels.postlogin.profileviewmodel.ProfileViewModel


@Composable
fun ProfileScreen(auth : FirebaseAuth,
    navToSettings: () -> Unit,
    navToInit: () -> Unit,
    profileViewModel: ProfileViewModel
){

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Title()
            Spacer(Modifier.padding(10.dp))
            ProfileImage()
            Spacer(Modifier.padding(5.dp))
            NombreEmail(auth.currentUser?.email?.split("@")[0], auth.currentUser?.email)
            Spacer(Modifier.padding(15.dp))
            SettingsCard(navToSettings)
            Spacer(modifier = Modifier.weight(1f))
            LogOutButton(
                cerraSesion = { profileViewModel.CerrarSesion() },
                navToInit = navToInit
            )
        }
    }

}

@Composable
private fun Title(){
    Text(text = "PROFILE",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxWidth())
}

@Composable
private fun ProfileImage(){
    Image(painter = painterResource(R.drawable.android_24px),
        contentDescription = "",
        modifier = Modifier
            .border(1.dp, Color.Gray, shape = CircleShape)
            .clip(CircleShape)
            .size(150.dp),
        alignment = Alignment.Center,

        )
}

@Composable
private fun NombreEmail(name: String?, email: String?){
    //Name
    if (name != null){
        Text(text = name, fontWeight = FontWeight.Bold, fontSize = 34.sp)
    } else {
        Text(text = "null name", fontWeight = FontWeight.Bold, fontSize = 34.sp)
    }

    if (email != null) {
        Text(text = email, fontSize = 16.sp)
    }
}

@Composable
private fun SettingsCard(navToSettings: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(horizontal = 14.dp)
            .clickable{ navToSettings() }
            .border(1.dp, Color.LightGray, RoundedCornerShape(20))
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20)),
        shape = RoundedCornerShape(20),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(R.drawable.settings_24px),
                contentDescription = "",
                tint = Color.Gray,
                modifier = Modifier.padding(horizontal = 10.dp)
            )

                Text(text = "Settings",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold) }
            Icon(painter = painterResource(R.drawable.arrow_right_24px),
                contentDescription = "",
                Modifier.size(50.dp),
                tint = Color.Gray)
        }
    }
}

@Composable
private fun LogOutButton(cerraSesion:() -> Unit,
                 navToInit: () -> Unit){

    Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        ) {
        Button(
            onClick = {
                cerraSesion()
                navToInit()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xffb9d9f4))
        ) {
            Text("Cerrar sesión",
                color = Color.DarkGray,
                fontSize = 22.sp)
        }
    }

}
