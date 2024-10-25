package com.example.travellingjournal

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apiapp.MyAdaptorLocations
import com.example.travellingjournal.databinding.LocationsScreenBinding
import com.google.firebase.firestore.firestore
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationsScreen : Fragment() {

    lateinit var binding: LocationsScreenBinding
    lateinit var Locations: MutableList<Location>
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MyAdaptorLocations
    var db = Firebase.firestore
    val sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val userId = sharedPreferences.getString("USER_ID", null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=LocationsScreenBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        super.onViewCreated(view, savedInstanceState)

        //Sets up the adapter
        Locations = mutableListOf<Location>()
        recyclerView = binding.locationsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(false)
        adapter = MyAdaptorLocations(Locations)
        recyclerView.adapter = adapter
        lifecycleScope.launch(Dispatchers.IO){
            getLocations()
            Log.d("Dispatcher", "Stugg")
        }

        //Sets up way to move to notes
        adapter.onShowNotesClickListener(object : MyAdaptorLocations.OnShowNotesListener{
            override fun onShowNotes(position: Int) {
                val bundle = bundleOf(
                    "Location" to adapter.list[position].ID
                )
                findNavController().navigate(R.id.action_locationsScreen_to_locationScreen, bundle)
            }
        })

        //Sets up way to edit location
        adapter.onEditLocationClickListener(object : MyAdaptorLocations.OnEditLocationListener{
            override fun onEditLocation(position: Int) {
                val bundle = bundleOf(
                    "Title" to adapter.list[position].title,
                    "ID" to adapter.list[position].ID
                )
                findNavController().navigate(R.id.action_locationsScreen_to_addLocation, bundle)
            }
        })

        //Sets up way to delete location
        adapter.onDeleteLocationClickListener(object : MyAdaptorLocations.OnDeleteLocationListener {
            override fun onDeleteLocation(position: Int) {
                val ref = userId?.let { db.collection("users").document(it).collection("locations") }
                if (ref != null) {
                    ref.document(adapter.list[position].ID).delete()
                }

                adapter.deleteLocation(position)
            }
        })
        //Way to move to add location
        binding.addLocationButton.setOnClickListener {
            findNavController().navigate(R.id.action_locationsScreen_to_addLocation)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    suspend fun getLocations(){
        val ref = userId?.let { db.collection("users").document(it).collection("locations") }
        ref?.get()?.addOnSuccessListener { locations->
            for (location in locations){
                Locations.add(Location(
                    location.id,
                    location.data["location_title"] as String,
                    location.data["notes_count"] as Long
                ))
                Log.d("Data FireStore", "${location.data["location_title"]} and ${location.data["notes_count"]}")
            }
            adapter.notifyDataSetChanged()
        }?.addOnFailureListener { exception->
            Log.d("Failure", "$exception")
        }
    }
}