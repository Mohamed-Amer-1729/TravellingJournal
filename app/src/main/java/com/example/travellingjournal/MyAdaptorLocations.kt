package com.example.apiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travellingjournal.Location
import com.example.travellingjournal.R

class MyAdaptorLocations(val list: MutableList<Location>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var showNotesListener: OnShowNotesListener

    interface OnShowNotesListener {
        fun onShowNotes(position: Int)
    }

    lateinit var editLocationListener: OnEditLocationListener

    interface OnEditLocationListener {
        fun onEditLocation(position: Int)
    }

    lateinit var deleteLocationListener: OnDeleteLocationListener

    interface OnDeleteLocationListener {
        fun onDeleteLocation(position: Int)
    }

    fun onShowNotesClickListener(listener: OnShowNotesListener){
        showNotesListener = listener
    }

    fun onEditLocationClickListener(listener: OnEditLocationListener){
        editLocationListener = listener
    }

    fun onDeleteLocationClickListener(listener: OnDeleteLocationListener){
        deleteLocationListener = listener
    }

    inner class MyViewHolder(itemView: View, listener: OnShowNotesListener):RecyclerView.ViewHolder(itemView){
        val TextViewTitle: TextView = itemView.findViewById(R.id.location)
        val TextViewNoteCount: TextView = itemView.findViewById(R.id.description)
        val ShowNotesButton: Button = itemView.findViewById(R.id.show_notes)
        val EditLocationButton: Button = itemView.findViewById(R.id.edit_location)
        val DeleteLocationButton: Button = itemView.findViewById(R.id.delete_location)

        fun bindData(location: Location){
            TextViewTitle.text = location.title
            TextViewNoteCount.text = "Note Count: ${location.note_count.toString()}"
        }

        init {
            ShowNotesButton.setOnClickListener {
                showNotesListener.onShowNotes(adapterPosition)
            }

            EditLocationButton.setOnClickListener{
                editLocationListener.onEditLocation(adapterPosition)
            }

            DeleteLocationButton.setOnClickListener {
                deleteLocationListener.onDeleteLocation(adapterPosition)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding=LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
        return MyViewHolder(binding, showNotesListener)
    }

    override fun getItemCount()= list.size

    override  fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item=list.get(position)
        (holder as MyViewHolder).bindData(item)

    }

    fun deleteLocation(position: Int){
        list.removeAt(position)
        notifyDataSetChanged()
    }


}




