package repository

import android.location.Location
import androidx.appcompat.view.ActionMode.Callback
import com.example.travellingjournal.Album
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class LocationsRepository {
    private val firestore = FirebaseFirestore.getInstance()

    fun getLocations(callback: (List<Album>) -> Unit){
        firestore.collection("users").document("user_id").collection("locations").get()
            .addOnSuccessListener {documents->
                val locations = documents.map{document->
                    document.toObject(Album::class.java)
                }
                callback(locations)
            }.addOnFailureListener{
                callback(emptyList())
            }
    }
}