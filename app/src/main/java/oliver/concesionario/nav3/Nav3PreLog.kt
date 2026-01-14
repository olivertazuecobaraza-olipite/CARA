package oliver.concesionario.nav3

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.google.firebase.auth.FirebaseAuth
import oliver.concesionario.model.Car
import oliver.concesionario.pages.postlogin.garage.GarageScreen
import oliver.concesionario.pages.postlogin.home.HomeScreen
import oliver.concesionario.pages.postlogin.info.InfoCarScreen
import oliver.concesionario.pages.postlogin.profile.ProfileScreen
import oliver.concesionario.pages.postlogin.settings.SettingsScreen
import oliver.concesionario.pages.prelogin.init.InitScreen
import oliver.concesionario.pages.prelogin.login.LoginScreen
import oliver.concesionario.pages.prelogin.register.RegisterScreen
import oliver.concesionario.viewmodels.postlogin.profileviewmodel.ProfileViewModel
import oliver.concesionario.viewmodels.prelogin.initviewmodel.InitViewModel
import oliver.concesionario.viewmodels.prelogin.loginviewmodel.LoginViewModel
import oliver.concesionario.viewmodels.prelogin.registerviewmodel.RegisterViewModel

// Pages
data object LoginScreen_O

data object RegisterScreen_O

data object InitScreen_O

data object HomeScreen_O

data object GarageScreen_O
data object ProfileScreen_O

data object SettingsScreen_O
data class InfoCarScreen_C(
    val car: Car
)

val bottonNavItems = listOf(HomeScreen_O, GarageScreen_O, ProfileScreen_O, SettingsScreen_O)



@Composable
fun Navigation(
    auth: FirebaseAuth,
    isUserLoggin: Boolean,

    initViewModel: InitViewModel,
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    profileViewModel: ProfileViewModel
){
    // Tab Bar items


    val startingScreen = if (isUserLoggin) HomeScreen_O else InitScreen_O
    var backStack = remember { mutableStateListOf(startingScreen) }

    val showBottomBar = backStack.last() in listOf(
        HomeScreen_O,
        GarageScreen_O,
        ProfileScreen_O,
        SettingsScreen_O
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar){
                BottomNavigationBar(backStack)
            }
        }
    ) { paddingValues ->
        NavDisplay(
            backStack = backStack,
            onBack = {
                if (backStack.size > 1) {
                    backStack.removeLastOrNull()
                }
            },
            modifier = Modifier.padding(paddingValues),
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
                            navToProfile = {
                                backStack.add(ProfileScreen_O)
                            },
                            navToDetailCar = { car ->
                                backStack.add(InfoCarScreen_C(car))
                            }
                        )
                    }

                    is GarageScreen_O -> NavEntry(key){
                        GarageScreen(
                            listCars = listOf(Car(name = "NOMBRE", type = "Sport", price = "price"),
                                Car(name = "NOMBRE", type = "Sport", price = "price"),
                                Car(name = "NOMBRE", type = "Sport", price = "price"),
                                Car(name = "NOMBRE", type = "Sport", price = "price"),
                                Car(name = "NOMBRE", type = "Sport", price = "price"),
                                Car(name = "NOMBRE", type = "Sport", price = "price"),
                                Car(name = "NOMBRE", type = "Sport", price = "price"),
                                Car(name = "NOMBRE", type = "Sport", price = "price"),
                                Car(name = "NOMBRE", type = "Sport", price = "price"),
                                Car(name = "NOMBRE", type = "Sport", price = "price"),
                                Car(name = "NOMBRE", type = "Sport", price = "price")),
                            navToDetailCar = { car ->
                                backStack.add(InfoCarScreen_C(car))
                            }
                        )
                    }

                    is ProfileScreen_O -> NavEntry(key) {
                        ProfileScreen(
                            auth = auth,
                            navToSettings = {
                                backStack.add(SettingsScreen_O)
                            },
                            navToInit = {
                                backStack.clear()
                                backStack.add(InitScreen_O)
                            },
                            profileViewModel = profileViewModel
                        )
                    }

                    is SettingsScreen_O -> NavEntry(key) {
                        SettingsScreen(
                            auth = auth,
                            keepLogg= {

                            }
                        )
                    }

                    is InfoCarScreen_C -> NavEntry(key) {
                        InfoCarScreen(
                            car = key.car,
                            goBack = {
                                backStack.removeLastOrNull()
                            }
                        )

                    }

                    // Error
                    else -> NavEntry(key = Unit) {
                        Text("Error runing")
                    }
                }
            }
        )
    }
}

// NAVIGATION BAR
@Composable
fun BottomNavigationBar(backStack: MutableList<Any>){
    val currenScreen = backStack.last()

    NavigationBar() {
        bottonNavItems.forEach { screen ->
            NavigationBarItem(
                selected = currenScreen::class == screen::class,
                onClick = {
                    if (currenScreen::class != screen::class){
                        backStack.removeAll {it::class == screen::class}
                        backStack.add(screen)
                    }
                },
                icon = {
                    Text(
                        text = when (screen) {
                            HomeScreen_O -> "🏠"
                            GarageScreen_O -> "🚗"
                            ProfileScreen_O -> "👤"
                            SettingsScreen_O -> "⚙️"
                            else -> ""
                        }
                    )
                }
            )
        }
    }


}