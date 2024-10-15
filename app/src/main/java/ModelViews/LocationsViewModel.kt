package ModelViews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travellingjournal.Album
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import repository.LocationsRepository

class LocationsViewModel:ViewModel() {
    private val locationsRepository = LocationsRepository()
    private val _locations = MutableLiveData<List<Album>>()
    val locations: LiveData<List<Album>> get() = _locations

    fun fetchLocations(){
        locationsRepository.getLocations { locationsList->
            _locations.postValue(locationsList)
        }
    }

}