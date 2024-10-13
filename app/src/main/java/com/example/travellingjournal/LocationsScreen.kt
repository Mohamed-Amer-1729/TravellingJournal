package com.example.travellingjournal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travellingjournal.databinding.LocationsScreenBinding

class LocationsScreen : Fragment() {

    lateinit var binding: LocationsScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=LocationsScreenBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            locationsRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        addLocationButton.setOnClickListener {

            findNavController().navigate(R.id.addLocation)

        }

        }

    }


}