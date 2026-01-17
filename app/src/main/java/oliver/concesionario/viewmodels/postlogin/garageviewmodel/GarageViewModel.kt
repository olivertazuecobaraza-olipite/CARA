package oliver.concesionario.viewmodels.postlogin.garageviewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import oliver.concesionario.model.Car

class GarageViewModel: ViewModel() {
    private val gson = Gson()

    private val _cars = MutableStateFlow<List<Car>>(mutableListOf())
    val Cars: StateFlow<List<Car>> = _cars

    fun addCar(newCar: Car) {
        val newList = _cars.value.toMutableList().apply { add(newCar) }
        _cars.value = newList
    }
}


