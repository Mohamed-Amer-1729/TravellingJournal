package com.example.travellingjournal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapterNotes(val list: MutableList<Note>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var onClickListener: OnItemClickListener
    interface OnItemClickListener{
        fun onClick(position: Int)
    }

    inner class MyViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView){
        val titleView: TextView = itemView.findViewById(R.id.title_note)
        val contentView: TextView = itemView.findViewById(R.id.content_note)
        val dateView: TextView = itemView.findViewById(R.id.date_note)

        fun bindData(note: Note){
            titleView.text = note.title
            contentView.text = note.content
            dateView.text = note.creationDate
        }

        init{
            itemView.setOnClickListener {
                onClickListener.onClick(adapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false), onClickListener)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).bindData(list[position])
    }
}