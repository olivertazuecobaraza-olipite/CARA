package oliver.concesionario.viewmodels.postlogin.homeviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel(db: FirebaseFirestore) {
    private var _search = MutableLiveData<String>()
    var Search: LiveData<String> = _search

    fun OnSeachValueChange(newString: String){
        _search.value = newString
    }
}