package ModelViews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travellingjournal.Location
import repository.LocationsRepository

class LocationsViewModel:ViewModel() {
    private val locationsRepository = LocationsRepository()
    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> get() = _locations

    fun fetchLocations(){
        locationsRepository.getLocations { locationsList->
            _locations.postValue(locationsList)
        }
    }

}