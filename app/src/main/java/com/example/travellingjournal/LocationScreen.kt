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
import com.example.travellingjournal.databinding.LocationScreenBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationScreen : Fragment() {
    private lateinit var binding: LocationScreenBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterNotes: MyAdapterNotes
    private lateinit var notes: MutableList<Note>
    private lateinit var locationID: String
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LocationScreenBinding.inflate(layoutInflater, container, false)
        locationID = requireArguments().getString("Location").toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            notes = mutableListOf()
            adapterNotes = MyAdapterNotes(notes)
            notesRecyclerView.adapter = adapterNotes

            // Get SharedPreferences
            val sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getString("USER_ID", null)

            if (userId != null) {
                lifecycleScope.launch(Dispatchers.IO) {
                    getNotes(userId)
                    Log.d("Dispatcher", "Fetching notes...")
                }
            }

            addNoteButton.setOnClickListener {
                val bundle = bundleOf("Location" to locationID)
                findNavController().navigate(R.id.action_locationScreen_to_addNote, bundle)
            }
        }
    }

    private suspend fun getNotes(userId: String) {
        val ref = db.collection("users").document(userId).collection("locations").document(locationID).collection("notes")
        ref.get().addOnSuccessListener { notesDB ->
            for (note in notesDB) {
                notes.add(
                    Note(
                        note.id.toInt(),
                        note.getString("note_title") ?: "",
                        note.getString("note_content") ?: "",
                        note.getString("create_date") ?: ""
                    )
                )
                Log.d("Data FireStore", "${note.getString("note_title")} created on ${note.getString("create_date")}")
            }
            adapterNotes.notifyDataSetChanged()
            Log.d("Final Data", "Dataset Changed")
        }.addOnFailureListener { exception ->
            Log.d("Failure", "$exception")
        }
        Log.d("After stuff", "Finished fetching notes")
    }
}
