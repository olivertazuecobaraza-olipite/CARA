package oliver.concesionario.viewmodels.postlogin.homeviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import oliver.concesionario.db
import oliver.concesionario.model.Car
import oliver.concesionario.viewmodels.postlogin.garageviewmodel.GarageViewModel

class HomeViewModel(db: FirebaseFirestore,
                    val garageViewModel: GarageViewModel) : ViewModel(){

    // [Start when initialize viewmodel]
    init {
        getCars()
        //addcars()
    }
    // [End when initialize viewmodel


    // [Start Search bar]

    // Observable property
    private var _search = MutableLiveData<String>()
    var Search: LiveData<String> = _search

    // Change the value as osbervable
    fun OnSeachValueChange(newString: String){
        _search.value = newString
    }

    // [End Search Bar]

    // [Start find Cars]

    // Observable property listCars
    private var _carList = MutableLiveData<List<Car>>()
    var CarList: LiveData<List<Car>> = _carList

    // Get Cars from concesionaro method
    private suspend fun GetAllCarConcesionario(): List<Car>{
        return try {
            val snapshot = db.collection("concesionario").get().await()
            // toObjects -> Generate a list automatically
            snapshot.toObjects(Car::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
    // [End find Cars]

    private fun getCars(){
        viewModelScope.launch {
            val resutl : List<Car> = withContext(Dispatchers.IO){
                GetAllCarConcesionario()
            }
            _carList.value = resutl
        }
    }

    // Method to do a test
    private fun addcars(){
        db.collection("concesionario").add(
            Car(name = "Prueba", type = "Prueba", price = "123$")
        )
    }

}