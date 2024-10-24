package repository

import com.example.travellingjournal.Location // Import your custom Location class
import com.google.firebase.firestore.FirebaseFirestore

class LocationsRepository {
    private val firestore = FirebaseFirestore.getInstance()

    fun getLocations(userId: String, callback: (List<Location>) -> Unit) {
        firestore.collection("users").document(userId).collection("locations").get()
            .addOnSuccessListener { documents ->
                val locations = documents.map { document ->
                    document.toObject(Location::class.java)
                }
                callback(locations)
            }.addOnFailureListener {
                callback(emptyList())
            }
    }
}
