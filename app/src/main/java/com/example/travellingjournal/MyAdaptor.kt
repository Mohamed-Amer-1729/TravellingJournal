package com.example.apiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.travellingjournal.Album
import com.example.travellingjournal.R

class MyAbapter(private val list: MutableList<Album>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val TextViewTitle: TextView = itemView.findViewById(R.id.location)
        val TextViewNoteCount: TextView = itemView.findViewById(R.id.description)

        fun bindData(album: Album){
            TextViewTitle.text = album.title
            TextViewNoteCount.text = "Note Count: ${album.note_count.toString()}"
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding=LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount()= list.size

    override  fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item=list.get(position)
        (holder as MyViewHolder).bindData(item)

    }
}




