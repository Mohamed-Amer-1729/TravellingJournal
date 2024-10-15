package com.example.travellingjournal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.travellingjournal.databinding.LocationItemBinding
import com.example.travellingjournal.models.Location

class MyAbapter(private val locationsList: LiveData<List<Location>>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MyViewHolder( val binding:LocationItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bindData(location: Location){

              /*  binding.apply {
                    userIdText.text=album.userId.toString()
                    IdText.text=album.id.toString()
                    titleText.text=album.title
            }*/

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

val binding=LocationItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount()= locationsList.value?.size?:0

    override  fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item=locationsList.value!![position]
        (holder as MyViewHolder).bindData(item)

    }
}




