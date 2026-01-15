package oliver.concesionario.viewmodels.postlogin.homeviewmodel

import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import oliver.concesionario.model.Car

class HomeViewModel(db: FirebaseFirestore) {
    private var _search = MutableLiveData<String>()
    var Search: LiveData<String> = _search

    fun OnSeachValueChange(newString: String){
        _search.value = newString
    }
}