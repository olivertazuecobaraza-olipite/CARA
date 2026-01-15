package oliver.concesionario.nav3

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import oliver.concesionario.R
import oliver.concesionario.model.Car
import oliver.concesionario.pages.postlogin.garage.GarageScreen
import oliver.concesionario.pages.postlogin.home.HomeScreen
import oliver.concesionario.pages.postlogin.info.InfoCarScreen
import oliver.concesionario.pages.postlogin.profile.ProfileScreen
import oliver.concesionario.pages.postlogin.settings.SettingsScreen
import oliver.concesionario.pages.prelogin.init.InitScreen
import oliver.concesionario.pages.prelogin.login.LoginScreen
import oliver.concesionario.pages.prelogin.register.RegisterScreen
import oliver.concesionario.viewmodels.postlogin.homeviewmodel.HomeViewModel
import oliver.concesionario.viewmodels.postlogin.profileviewmodel.ProfileViewModel
import oliver.concesionario.viewmodels.prelogin.initviewmodel.InitViewModel
import oliver.concesionario.viewmodels.prelogin.loginviewmodel.LoginViewModel
import oliver.concesionario.viewmodels.prelogin.registerviewmodel.RegisterViewModel

// Data objects or class, by screens/ pages created in the app
// [Start, data object/classes
data object LoginScreenO

data object RegisterScreenO

data object InitScreenO

data object HomeScreenO

data object GarageScreenO
data object ProfileScreenO

data object SettingsScreenO
data class InfoCarScreenC(
    val car: Car
)
// [End, data object/classes



// Navigation bar buttons screens
val bottonNavItems = listOf(HomeScreenO, GarageScreenO, ProfileScreenO, SettingsScreenO)

// Set the Stack where the screens will be later
lateinit var backStack: SnapshotStateList<Any>


// [Start, Navigation, Navigation tool]
@Composable
fun Navigation(
    // [Start, params]
    auth: FirebaseAuth,
    database: FirebaseFirestore,
    isUserLogg: Boolean,

    initViewModel: InitViewModel,
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    profileViewModel: ProfileViewModel,
    homeViewModel: HomeViewModel

    // [End, Params]
){
    // [Start, Tools,
    // Set the starting screen depends on user is logged or not,
    // Set the BackStack with all screens needed
    val startingScreen = if (isUserLogg) HomeScreenO else InitScreenO
    backStack = remember { mutableStateListOf(startingScreen) }
    // [End, Tools]
    // [Start, can Show NavBar?,
    // if the last screen of the stack(Screen where the user is) is in the list of the screens then is true
    // so after can show the NavBar
    val showBottomBar = backStack.last() in listOf(
        HomeScreenO,
        GarageScreenO,
        ProfileScreenO,
        SettingsScreenO
    )
    // [End, can show NavBar]

    Scaffold(
        bottomBar = {
            if (showBottomBar){
                BottomNavigationBar(backStack)
            }
        }
    ) { paddingValues ->

        // [Start, NavDisplay]
        NavDisplay(
            backStack = backStack, // Set BackStack
            onBack = { // Set back button arrow, remove last screen if exists
                if (backStack.size > 1) {
                    backStack.removeLastOrNull()
                }
            },
            modifier = Modifier.padding(paddingValues),
            entryProvider = { key -> // Set Rest of Navigation with Lambdas
                when (key){
                    // [Start, PreLogin]

                    // [Start, InitScreen]
                    is InitScreenO -> NavEntry(key){
                        InitScreen( // Set params
                            navToLogin = { navTo(LoginScreenO) },// Add new screen to the top(LoginScreen)
                            navToRegister = { navTo(RegisterScreenO) },// Add new screen to the top(RegisterScreen)
                            initViewModel = initViewModel, // Set ViewModel(InitViewModel)
                        )
                    }
                    // [End, InitScreen]
                    // [Start, LoginScreen]
                    is LoginScreenO -> NavEntry(key) {
                        LoginScreen( // Set params
                            navToRegister = { navTo(RegisterScreenO) },// Add new Screen to the top(RegisterScreen)
                            navToHome = { navTo(HomeScreenO) },// Add new Screen to the top(HomeScreen)
                            navToback = { navBack() },// Delete Last/current Screen from the stack, so the previous is the new CurrentScreen
                            loginViewModel = loginViewModel, // Set ViewModel(LoginViewModel)
                        )
                    }
                    // [End, LoginScreen]
                    // [Start, RegisterScreen]
                    is RegisterScreenO -> NavEntry(key) {
                        RegisterScreen(
                            navToLogin = { navTo(LoginScreenO) }, // Add new Screen to the top(LoginScreen)
                            navToback = { navBack() }, // Delete Last/current Screen from the stack, so the previous is the new CurrentScreen
                            registerViewModel = registerViewModel // Set ViewModel(RegisterViewModel)
                        )
                    }
                    // [End, RegisterScreen]
                    // [End, PreLogin]

                    // [Start, PostLogin]
                    // [Start, HomeScreen]
                    is HomeScreenO -> NavEntry(key) {
                        HomeScreen(
                            navToProfile = { navTo(ProfileScreenO) }, // Add new Screen to the top(ProfileScreen)
                            navToDetailCar = { car -> // Param by the car clicked
                                navTo(InfoCarScreenC(car)) }, // Add new Screen to the top(InfoCarScreen)
                            homeViewModel = homeViewModel // Set ViewModel(HomeViewModel)
                        )
                    }
                    // [End, HomeScreen]
                    // [Start, GarageScreen]
                    is GarageScreenO -> NavEntry(key){
                        GarageScreen(
                            listCars = listOf(), // List of cars in owner Garage
                            navToDetailCar = { car -> // Param by the car clicked
                                navTo(InfoCarScreenC(car)) } // Add new Screen to the top(InfoCarScreen)
                        )
                    }
                    // [End, GarageScreen]
                    // [Start, ProfileScreen]
                    is ProfileScreenO -> NavEntry(key) {
                        ProfileScreen(
                            auth = auth, // Set param Auth from FireBase, to print some user info
                            navToSettings = { navTo(SettingsScreenO) }, // Add new Screen to the top(SettingsScreen)
                            navToInit = { // Nav to Init
                                clearBackStack() // Firs, how user is navigating to Init, lets clear the back stack to do not use back button
                                navTo(InitScreenO) }, // Now Add new Screen to the top when is empty so now Init is like user would reset the app
                            profileViewModel = profileViewModel // Set ViewModel(ProfileViewModel)
                        )
                    }
                    // [End, ProfileScreen]
                    // [Start, SettingsScreen]
                    is SettingsScreenO -> NavEntry(key) {
                        SettingsScreen(
                            auth = auth, // Set Param Auth from FireBase, to sign out
                            keepLogg= { //

                            }
                        )
                    }
                    // [End, SettingsScreen]
                    // [Start, InfoCarScreen]
                    is InfoCarScreenC -> NavEntry(key) {
                        InfoCarScreen(
                            car = key.car,// Set Param Car's clicked
                            goBack = { navBack() } // Delete Last/current Screen from the stack, so the previous is the new CurrentScreen
                        )
                    }
                    // [End, InfoCarScreen]

                    // Error
                    else -> NavEntry(key = Unit) {
                        Text("Error running")
                    }
                }
            }
        )
        // [End, NavDisplay]
    }
}

// [End, Navigation, Navigation tool]


// [Start, Navigation Bar]
@Composable
fun BottomNavigationBar(backStack: MutableList<Any>){
    // Set current screen is the last item from the backstack
    val currentScreen = backStack.last()

    // [Start, Set Navigation Bar]
    NavigationBar {
        bottonNavItems.forEach { screen ->
            NavigationBarItem(
                selected = currentScreen::class == screen::class,
                onClick = { // Onclick add to top's stack the screen tapped
                    if (currentScreen::class != screen::class) {
                        backStack.removeAll { it::class == screen::class }
                        backStack.add(screen)
                    }
                },
                // Set painter for each icon of screen, I want in my Navigation bar
                icon = {
                    val icon: Int = when (screen) {
                        HomeScreenO -> R.drawable.ic_home
                        GarageScreenO -> R.drawable.ic_car
                        ProfileScreenO -> R.drawable.account_circle_24px
                        SettingsScreenO -> R.drawable.settings_24px
                        else -> {}
                    } as Int
                    // Set icon with the previous Painter created
                    Icon(painter = painterResource(icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp))
                }
            )
        }
    }
    // [End, Set Navigation Bar]
}

// [End, Navigation Bar]

// [Start, NavTo tool, Tool to set the current screen which you want by param
private fun navTo(screen: Any){
    backStack.add(screen)
}
// [End, NavTo]
// [Start, NavBack, Remove last screen at the stack/current screen, then it goes back]
private fun navBack(){
    if (backStack.size > 1){
        backStack.removeLastOrNull()
    }
}
// [End, NavBack]
// [Start, ClearBackstack]
private fun clearBackStack(){
    backStack.clear()
}
// [End, ClearBackStack]