package com.example.travellingjournal

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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationsScreen : Fragment() {

    private lateinit var binding: LocationsScreenBinding
    private lateinit var locations: MutableList<Location>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdaptorLocations
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LocationsScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", null)

        // Setup the adapter
        locations = mutableListOf()
        recyclerView = binding.locationsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(false)
        adapter = MyAdaptorLocations(locations)
        recyclerView.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            if (userId != null) {
                getLocations(userId)
                Log.d("Dispatcher", "Fetching locations...")
            }
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

        // Way to move to add location
        binding.addLocationButton.setOnClickListener {
            findNavController().navigate(R.id.action_locationsScreen_to_addLocation)
        }
    }

    private suspend fun getLocations(userId: String) {
        val ref = db.collection("users").document(userId).collection("locations")
        ref.get().addOnSuccessListener { locationsSnapshot ->
            for (location in locationsSnapshot) {
                locations.add(
                    Location(
                        location.id,
                        location.getString("location_title") ?: "",
                        location.getLong("notes_count") ?: 0L
                    )
                )
                Log.d("Data FireStore", "${location.getString("location_title")} and ${location.getLong("notes_count")}")
            }
            adapter.notifyDataSetChanged()
            Log.d("Final Data", "Dataset Changed")
        }.addOnFailureListener { exception ->
            Log.d("Failure", "$exception")
        }
        Log.d("After stuff", "Finished fetching locations")
    }
}
