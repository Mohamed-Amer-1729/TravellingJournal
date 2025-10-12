package com.example.travellingjournal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travellingjournal.databinding.NoteDetailsBinding

class NoteDetails : Fragment() {

    lateinit var binding: NoteDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=NoteDetailsBinding.inflate(layoutInflater,container,false)

        return binding.root
    }



}