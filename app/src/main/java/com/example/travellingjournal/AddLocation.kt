package com.example.travellingjournal

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.travellingjournal.databinding.AddLocationBinding
import com.google.firebase.firestore.FirebaseFirestore

class AddLocation : Fragment() {
    private lateinit var binding: AddLocationBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddLocationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleField = binding.teAddTitleLocation

        // Get SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", null)

        // Save button to add location
        binding.saveButAddLocation.setOnClickListener {
            if (titleField.text == null || titleField.text!!.isEmpty()) {
                Toast.makeText(context, "Please input a title", Toast.LENGTH_SHORT).show()
            } else {
                val locationMap = hashMapOf(
                    "location_title" to titleField.text.toString(),
                    "notes_count" to 0
                )
                db.collection("users").document(userId!!).collection("locations")
                    .add(locationMap)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show()
                        titleField.text!!.clear()
                        findNavController().navigate(R.id.action_addLocation_to_locationsScreen)
                    }.addOnFailureListener {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        // Cancel button
        binding.cancelButAddLocation.setOnClickListener {
            titleField.text!!.clear()
            Toast.makeText(context, "Operation Cancelled", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addLocation_to_locationsScreen)
        }
    }
}
