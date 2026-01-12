package oliver.concesionario.nav3

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.google.firebase.auth.FirebaseAuth
import oliver.concesionario.model.Car
import oliver.concesionario.pages.postlogin.home.HomeScreen
import oliver.concesionario.pages.postlogin.info.InfoCarScreen
import oliver.concesionario.pages.postlogin.profile.ProfileScreen
import oliver.concesionario.pages.prelogin.init.InitScreen
import oliver.concesionario.pages.prelogin.login.LoginScreen
import oliver.concesionario.pages.prelogin.register.RegisterScreen
import oliver.concesionario.viewmodels.prelogin.initviewmodel.InitViewModel
import oliver.concesionario.viewmodels.prelogin.loginviewmodel.LoginViewModel
import oliver.concesionario.viewmodels.prelogin.registerviewmodel.RegisterViewModel

// Pages
data object LoginScreen_O

data object RegisterScreen_O

data object InitScreen_O

data object HomeScreen_O
data object ProfileScreen_O

data class InfoCarScreen_C(
    val car: Car
)


@Composable
fun Navigation(
    isUserLoggin: Boolean,

    initViewModel: InitViewModel,
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel

){
    val startingScreen = if (isUserLoggin) HomeScreen_O else InitScreen_O
    val backStack = remember { mutableStateListOf(startingScreen) }

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },
        entryProvider = { key ->
            when (key){

                // Pre Login
                is InitScreen_O -> NavEntry(key){
                    InitScreen(
                        navToLogin = {
                            backStack.add(LoginScreen_O)
                        },

                        navToRegister = {
                            backStack.add(RegisterScreen_O)
                        },
                        initViewModel = initViewModel,

                    )
                }

                is LoginScreen_O -> NavEntry(key) {
                    LoginScreen(
                        navToRegister = {
                            backStack.add(RegisterScreen_O)
                        },
                        navToHome = {
                            backStack.add(HomeScreen_O)
                        },
                        loginViewModel = loginViewModel,
                        navToback = {
                            if (backStack.size > 1){
                                backStack.removeLastOrNull()
                            }
                        }
                    )
                }
                is RegisterScreen_O -> NavEntry(key) {
                    RegisterScreen(
                        navToLogin = {
                            backStack.add(LoginScreen_O)
                        },
                        navToback = {
                            if (backStack.size > 1){
                                backStack.removeLastOrNull()
                            }
                        },
                        registerViewModel = registerViewModel
                    )
                }

                // Post Login
                is HomeScreen_O -> NavEntry(key) {
                    HomeScreen(
                        navToInit = {
                            backStack.add(InitScreen_O)
                        },
                        navToProfile = {
                            backStack.add(ProfileScreen_O)
                        },
                        navToDetailCar = { car ->
                            backStack.add(InfoCarScreen_C(car))
                        }
                    )
                }

                is InfoCarScreen_C -> NavEntry(key) {
                    InfoCarScreen(
                        key.car
                    )
                }

                is ProfileScreen_O -> NavEntry(key) {
                    ProfileScreen()
                }


                // Error
                else -> NavEntry(key = Unit) {
                    Text("Error runing")
                }
            }



        }
    )
}