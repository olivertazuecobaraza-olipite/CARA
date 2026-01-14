package oliver.concesionario.pages.postlogin.garage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import oliver.concesionario.model.Car

@Composable
fun GarageScreen(listCars: List<Car>){
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // Image

            // Contents
        }
    }
}

// Image
@Composable
fun CarImage(){

}