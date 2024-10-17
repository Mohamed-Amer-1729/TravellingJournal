package repository

import android.location.Location
import com.example.travellingjournal.Location
import com.google.firebase.firestore.FirebaseFirestore

class LocationsRepository {
    private val firestore = FirebaseFirestore.getInstance()

    fun getLocations(callback: (List<com.example.travellingjournal.Location>) -> Unit){
        firestore.collection("users").document("user_id").collection("locations").get()
            .addOnSuccessListener {documents->
                val locations = documents.map{document->
                    document.toObject(Location::class.java)
                }
                callback(locations)
            }.addOnFailureListener{
                callback(emptyList())
            }
    }
}