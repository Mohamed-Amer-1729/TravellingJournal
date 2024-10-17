package com.example.apiapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.apiapp.databinding.ItemLayoutBinding

class MyAbapter(private val list: LiveData<List<Album>>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MyViewHolder( val binding:ItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bindData(album: Album){

                binding.apply {
                    userIdText.text=album.userId.toString()
                    IdText.text=album.id.toString()
                    titleText.text=album.title
            }

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

val binding=ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount()= list.value?.size?:0

    override  fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item=list.value!!.get(position)
        (holder as MyViewHolder).bindData(item)

    }
}




