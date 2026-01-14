package oliver.concesionario.pages.prelogin.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oliver.concesionario.R
import oliver.concesionario.viewmodels.prelogin.loginviewmodel.LoginViewModel


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navToRegister: () -> Unit,
    navToHome: () -> Unit,
    navToback:() -> Unit
){

    val email by loginViewModel.Email.observeAsState(initial = "")
    val password by loginViewModel.Password.observeAsState(initial = "")
    val isLogged by loginViewModel.IsLogged.observeAsState(initial = false)
    val isPasswordVisible by loginViewModel.IspasswordVisible.observeAsState(initial = false)

    LaunchedEffect(isLogged) {
        if (isLogged) {
            navToHome()
            loginViewModel.ResetEmailPassw()
        }
    }


    Scaffold(containerColor = Color(0xffE2E3E3),
        modifier = Modifier.fillMaxSize())
    { paddingValues ->
        Column(Modifier.padding(paddingValues)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
            )
        {
            Spacer(Modifier.padding(5.dp))
            BackArrow {
                loginViewModel.ResetEmailPassw()
                navToback()
            }
            Spacer(Modifier.padding(30.dp))

            LogoImage()
            Spacer(Modifier.padding(20.dp))
            Texts()

            Spacer(Modifier.padding(20.dp))
            EmailPassFields(
                email,
                password,
                onEmailChange = { loginViewModel.isLoginChange(it, password) },
                onPasswordChange = {
                    loginViewModel.isLoginChange(email, it)
                },
                isPasswordVisible = isPasswordVisible,
                onVisibilityClic = { loginViewModel.onPasswordVisibilityChange() }
            )

            Spacer(Modifier.padding(10.dp))
            LoginButton(
                onLogClic = { loginViewModel.onLoginSelected() }
            )

            Spacer(Modifier.padding(5.dp))
            ForgotPassword {  }

            Spacer(Modifier.padding(10.dp))
            OtherLogsIcons()

            Spacer(Modifier.padding(40.dp))
            SignUp { navToRegister() }
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
        modifier = Modifier.clip(CircleShape).size(150.dp),
        contentScale = ContentScale.Crop)
}

// Text Welcome...
@Composable
fun Texts(){
    Text(text = "WELCOME", fontSize = 42.sp, fontWeight = FontWeight.Bold)
    Text(text = "Sign in to continue", fontSize = 18.sp)
}


// Writeables
@Composable
fun EmailPassFields(
    email: String,
    password: String,

    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,

    onVisibilityClic: () -> Unit,
    isPasswordVisible: Boolean
)
{

    EmailField(email) { eml->
        onEmailChange(eml)
    }

    Spacer(Modifier.padding(5.dp))
    PasswordField(password = password,
        isPasswordVisible = isPasswordVisible,
        onVisibilityClic = onVisibilityClic,
        onPasswordFieldChange = { onPasswordChange(it) }
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
                          onPasswordFieldChange: (String) -> Unit,
                          isPasswordVisible: Boolean,
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

        visualTransformation = if (isPasswordVisible){
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,

            focusedContainerColor = Color(0xffF1F1F1),
            unfocusedContainerColor = Color(0xffF1F1F1),
            disabledContainerColor = Color(0xffF1F1F1)
        ),
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
fun LoginButton(onLogClic: () -> Unit){

    val gradiant = Brush.verticalGradient(
        listOf(Color(0xff8ECAFE), Color(0xff4A90E2))
    )

    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = {
            onLogClic()
        },
        shape = CircleShape,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 25.dp)
            .background(gradiant, shape = CircleShape)
    )
    {
        Text(text = "Log In",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp)
    }
}

// Forgot password
@Composable
fun ForgotPassword(onForgotPassword: () -> Unit){
    Text(text = "Forgot your password?",
        color = Color.DarkGray,
        modifier = Modifier.clickable(onClick = { onForgotPassword() }))
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
    }

}

// Apple Icon
@Composable
private fun AppleIcon(){

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.ic_apple_logo),
        contentDescription = "",
        modifier = Modifier
            .size(60.dp)
            .background(Color.White, shape = CircleShape)
            .shadow(elevation = 2.dp, shape = CircleShape)
    )
         }
}

// Register
@Composable
fun SignUp(onSignUpClic: () -> Unit){
    Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
        .background(color = Color(0xffced4da)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        )
    {
        Text(text = "Don't have an account? ",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable(onClick = {
                    onSignUpClic()
                })
        )
        Text(text = "Sign Up",
            fontSize = 16.sp,
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable(onClick = {
                    onSignUpClic()
                })
        )
    }
}