package com.hoonstudio.venyoo.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hoonstudio.venyoo.databinding.ItemSetlistSongBinding
import com.hoonstudio.venyoo.venues.SetlistFMSong

class SetlistAdapter: RecyclerView.Adapter<SetlistAdapter.SetlistViewHolder>() {
    private var setlist: List<SetlistFMSong> = emptyList()

    fun bindData(set: List<SetlistFMSong>){
        setlist = ArrayList(set)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetlistViewHolder {
        val binding = ItemSetlistSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SetlistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SetlistViewHolder, position: Int) {
        holder.bind(setlist[position], position)
    }

    override fun getItemCount(): Int {
        return setlist.size
    }

    inner class SetlistViewHolder(
        val binding: ItemSetlistSongBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(song: SetlistFMSong, position: Int){

            val pos = position + 1
            if(pos < 10){
                binding.songPositionTextView.text = "0${pos}"
            }else{
                binding.songPositionTextView.text = "${pos}"
            }

            binding.songNameTextView.text = song.name
        }
    }
}