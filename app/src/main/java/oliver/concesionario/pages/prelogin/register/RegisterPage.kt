package oliver.concesionario.pages.prelogin.register

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oliver.concesionario.R
import oliver.concesionario.viewmodels.prelogin.registerviewmodel.RegisterViewModel


@Composable
fun RegisterScreen(registerViewModel: RegisterViewModel,
                   navToLogin: () -> Unit,
                   navToback:() -> Unit)
{
    val email by registerViewModel.Email.observeAsState(initial = "")
    val password by registerViewModel.Password.observeAsState(initial = "")
    val isRegSuc by registerViewModel.IsRegisterSuccesfully.observeAsState(initial = false)
    val isPswVisible by registerViewModel.IsPswVisible.observeAsState(initial = false)

    if (isRegSuc){
        registerViewModel.ResetVariables()
        navToLogin()
    }

    Scaffold(containerColor = Color(0xffE2E3E3),
        modifier = Modifier.fillMaxSize())
    { paddingValues ->
        Column(Modifier.padding(paddingValues)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            BackArrow {
                registerViewModel.ResetVariables()
                navToback()
            }
            Spacer(Modifier.padding(25.dp))
            LogoImage()

            Spacer(Modifier.padding(25.dp))
            Texts()

            Spacer(Modifier.padding(25.dp))
            EmailPassFields(
                email = email,
                password = password,

                onEmailFieldChange = { registerViewModel.EmailChange(it) },
                onPasswordFieldChange = { registerViewModel.PasswordChanged(it) },

                onVisibilityClic = {registerViewModel.OnPasswordVisibleClic()},
                isPasswordVisible = isPswVisible

            )

            Spacer(Modifier.padding(10.dp))
            RegisterButton(email = email,
                password = password,
                onRegClic = { registerViewModel.OnRegisterSelected() })

            Spacer(Modifier.padding(25.dp))
            OtherLogsIcons()
        }
    }
}


// Back Arrow
@Composable
fun BackArrow(navToBack: () -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp))
    {
        Image(painter = painterResource(R.drawable.ic_back_icon),
            contentDescription = "",
            modifier = Modifier
                .clickable{
                    navToBack()
                })
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

// Text Welcome...
@Composable
fun Texts(){
    Text(text = "Create an Account", fontSize = 32.sp, fontWeight = FontWeight.Bold)
}


// Writeables
@Composable
fun EmailPassFields(email: String,
                    password: String,

                    onEmailFieldChange: (String) -> Unit,
                    onPasswordFieldChange: (String) -> Unit,

                    isPasswordVisible: Boolean,
                    onVisibilityClic: () -> Unit){
    EmailField(email, onEmailFieldChange)
    Spacer(Modifier.padding(5.dp))
    PasswordField(password = password,
         onPasswordFieldChange = onPasswordFieldChange,
        isPasswordVisible = isPasswordVisible,
        onVisibilityClic = onVisibilityClic
        )
}

// Atomic Email Field
@Composable
private fun EmailField(email: String, onEmailFieldChange: (String) -> Unit){
    TextField(
        label = { Text("Email")},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp)),
        placeholder = {Text("Email")},
        value = email,
        onValueChange = { onEmailFieldChange(it) },
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,

            focusedContainerColor = Color(0xffF1F1F1),
            unfocusedContainerColor = Color(0xffF1F1F1),
            disabledContainerColor = Color(0xffF1F1F1)
        )
    )
}

// Atomic Password Field
@Composable
private fun PasswordField(password: String,
                          isPasswordVisible: Boolean,
                          onPasswordFieldChange: (String) -> Unit,
                          onVisibilityClic: () -> Unit){
    TextField(
        label = { Text("Password") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp)),
        placeholder = { Text("Password") },
        value = password,
        onValueChange = { onPasswordFieldChange(it) },
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,

            focusedContainerColor = Color(0xffF1F1F1),
            unfocusedContainerColor = Color(0xffF1F1F1),
            disabledContainerColor = Color(0xffF1F1F1)
        ),

        visualTransformation = if (isPasswordVisible){
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },

        trailingIcon = {
            val icon = if (isPasswordVisible){
                painterResource(R.drawable.ic_eye)
            } else {
                painterResource(R.drawable.ic_visibility_off)
            }

            IconButton(onClick = onVisibilityClic) {
                Icon(painter = icon, contentDescription = "")
            }
        }

    )
}

// Log In Button
@Composable
fun RegisterButton(email: String,
                   password: String,
                   onRegClic: () -> Unit ){

    val gradiant = Brush.verticalGradient(
        listOf(Color(0xff8ECAFE), Color(0xff4A90E2))
    )

    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = {
            onRegClic()
        },
        shape = CircleShape,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 25.dp)
            .background(gradiant, shape = CircleShape)
    )
    {
        Text(text = "Register",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp)
    }
}


// Google Apple Logos
@Composable
fun OtherLogsIcons(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        GoogleIcon()
        Spacer(Modifier.padding(25.dp))
        AppleIcon()
    }
}

// Google Icon
@Composable
private fun GoogleIcon(){

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.ic_google_logo),
            contentDescription = "",
            modifier = Modifier
                .size(60.dp)
                .background(Color.White, shape = CircleShape)
                .shadow(elevation = 2.dp, shape = CircleShape)
        )
        Spacer(Modifier.padding(2.dp))
        Text(text = "Log in with Google", fontSize = 12.sp)
    }

}

// Apple Icon
@Composable
private fun AppleIcon(){

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween) {
        Image(painter = painterResource(R.drawable.ic_apple_logo),
            contentDescription = "",
            modifier = Modifier
                .size(60.dp)
                .background(Color.White, shape = CircleShape)
                .shadow(elevation = 2.dp, shape = CircleShape)
        )
        Spacer(Modifier.padding(2.dp))
        Text(text = "Log in with Apple", fontSize = 12.sp) }
}


