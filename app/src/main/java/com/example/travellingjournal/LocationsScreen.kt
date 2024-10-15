package com.example.travellingjournal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apiapp.MyAbapter
import com.example.travellingjournal.databinding.LocationsScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LocationsScreen : Fragment() {

    lateinit var binding: LocationsScreenBinding
    lateinit var Albums: MutableList<Album>
    lateinit var recyclerView: RecyclerView

    lateinit var adapter: MyAbapter
    var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding=LocationsScreenBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Albums = mutableListOf<Album>()
        //val AlbumsList = mutableListOf<Album>()
        val ref = db.collection("users").document("user_id").collection("locations")
        ref.get().addOnSuccessListener { locations->
            for (location in locations){
                Albums.add(Album(
                    location.id,
                    location.data["location_title"] as String,
                    location.data["notes_count"] as Long
                ))
                Log.d("Data FireStore", "${location.data["location_title"]} and ${location.data["notes_count"]}")
            }
        }.addOnFailureListener {exception->
            Log.d("Failure", "$exception")
        }

        recyclerView = binding.locationsRecyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)

        recyclerView.setHasFixedSize(false)
        Log.d("Data", "$Albums")
        adapter = MyAbapter(Albums)
        recyclerView.adapter = adapter
    }


}