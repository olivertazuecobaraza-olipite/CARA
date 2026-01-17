package oliver.concesionario.viewmodels.postlogin.infoviewmodel

import androidx.lifecycle.ViewModel
import oliver.concesionario.model.Car
import oliver.concesionario.viewmodels.postlogin.garageviewmodel.GarageViewModel

class InfoViewModel(val garageViewModel: GarageViewModel) : ViewModel(){
    // [Start, AddCar]
    fun AddCar(car: Car){
        garageViewModel.addCar(car)
    }
    // [End, AddCar]
}