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
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationScreen : Fragment() {
    lateinit var binding: LocationScreenBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapterNotes: MyAdapterNotes
    lateinit var notes: MutableList<Note>
    lateinit var locationID: String
    var db = Firebase.firestore
    val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val userId = sharedPreferences.getString("USER_ID", null)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding=LocationScreenBinding.inflate(layoutInflater,container,false)
        locationID = requireArguments().getString("Location").toString()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            notesRecyclerView.layoutManager=LinearLayoutManager(requireContext())
            notes = mutableListOf<Note>()
            adapterNotes = MyAdapterNotes(notes)
            notesRecyclerView.adapter = adapterNotes

            lifecycleScope.launch(Dispatchers.IO){
                getNotes()
                Log.d("Dispatcher", "Stugg")
            }

            addNoteButton.setOnClickListener{
                var bundle = bundleOf("Location" to locationID)
                findNavController().navigate(R.id.action_locationScreen_to_addNote, bundle)
            }
        }
    }

    suspend fun getNotes(){
        val ref = db.collection("users").document(userId).collection("locations").document(locationID).collection("notes")
        ref.get().addOnSuccessListener { notesDB->
            for (note in notesDB){
                notes.add(Note(
                    note.id.toInt(),
                    note.data["note_title"] as String,
                    note.data["note_content"] as String,
                    note.data["create_date"] as String
                ))
                Log.d("Data FireStore", "${note.data["note_title"]} created on ${note.data["create_date"]}")
            }
            adapterNotes.notifyDataSetChanged()
            Log.d("Final Data", "Dataset Changed")
        }.addOnFailureListener {exception->
            Log.d("Failure", "$exception")
        }
        Log.d("After stuff", "Stuff finished")
    }


}