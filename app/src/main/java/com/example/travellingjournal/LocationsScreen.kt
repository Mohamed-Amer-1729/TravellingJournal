package com.example.travellingjournal

import ModelViews.LocationsViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apiapp.MyAbapter
import com.example.travellingjournal.databinding.LocationsScreenBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LocationsScreen : Fragment() {

    lateinit var binding: LocationsScreenBinding
    lateinit var Albums: MutableList<Album>
    lateinit var recyclerView: RecyclerView
    lateinit var locationsViewModel: LocationsViewModel
    lateinit var adapter: MyAbapter
    var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding=LocationsScreenBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Albums = mutableListOf<Album>()
        recyclerView = binding.locationsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(false)
        adapter = MyAbapter(Albums)
        recyclerView.adapter = adapter
        lifecycleScope.launch(Dispatchers.IO){
            getLocations()
            Log.d("Dispatcher", "Stugg")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    suspend fun getLocations(){
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
            adapter.notifyDataSetChanged()
            Log.d("Final Data", "Dataset Changed")
        }.addOnFailureListener {exception->
            Log.d("Failure", "$exception")
        }
        Log.d("After stuff", "Stuff finished")

    }
}

