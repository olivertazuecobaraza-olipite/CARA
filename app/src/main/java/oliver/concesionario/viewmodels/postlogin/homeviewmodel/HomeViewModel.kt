package oliver.concesionario.viewmodels.postlogin.homeviewmodel

import android.util.Log
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import oliver.concesionario.db
import oliver.concesionario.model.Car

class HomeViewModel(db: FirebaseFirestore) {

    // [Start when initialize viewmodel]
    init {
        GetAllCarConcesionario()
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
    private fun GetAllCarConcesionario(){

        db.collection("concesionario").get()
            .addOnSuccessListener { result ->
                _carList.value = result.toObjects(Car::class.java)
            }
            .addOnFailureListener {
                print("Cannot get the cars from the database")
            }

    }


    // [End find Cars]

}