package com.example.travellingjournal

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.set
import androidx.navigation.fragment.findNavController
import com.example.travellingjournal.databinding.AddLocationBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class AddLocation : Fragment() {
    lateinit var binding:AddLocationBinding
    var db = Firebase.firestore
    val sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val userId = sharedPreferences.getString("USER_ID", null)
    lateinit var location: String
    lateinit var ID: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=AddLocationBinding.inflate(layoutInflater,container,false)
        location = requireArguments().getString("Location").toString()
        ID = requireArguments().getString("ID").toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleField = binding.teAddTitleLocation
        titleField.setText(location)
        //Save button to add location
        binding.saveButAddLocation.setOnClickListener{
            if(titleField.text == null){
                Toast.makeText(context, "Please input a title", Toast.LENGTH_SHORT).show()
            }else{
                val locationMap = hashMapOf(
                    "location_title" to titleField.text,
                    "notes_count" to 0
                )

                val ref = userId?.let { it1 ->
                    if (ID.isEmpty()){
                        db.collection("users").document(it1).collection("locations")
                            .add(locationMap)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show()
                                titleField.text!!.clear()
                                findNavController().navigate(R.id.action_addLocation_to_locationsScreen)
                            }.addOnFailureListener{
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                            }
                    }else{
                        db.collection("users").document(it1).collection("locations").document(ID)
                            .update(locationMap)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show()
                                titleField.text!!.clear()
                                findNavController().navigate(R.id.action_addLocation_to_locationsScreen)
                            }.addOnFailureListener{
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                            }
                    }

                }
            }
        }

        //cancel button
        binding.cancelButAddLocation.setOnClickListener{
            titleField.text!!.clear()
            Toast.makeText(context, "Operation Cancelled", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addLocation_to_locationsScreen)
        }
    }


}