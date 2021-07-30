package com.hoonstudio.venyoo.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hoonstudio.venyoo.R
import com.hoonstudio.venyoo.databinding.ItemAttractionBinding
import com.hoonstudio.venyoo.venues.TicketMasterAttractionResponse

class AttractionAdapter(
    private val itemClicked: (TicketMasterAttractionResponse) -> Unit
): RecyclerView.Adapter<AttractionAdapter.AttractionViewHolder>() {

    private var attractionList: List<TicketMasterAttractionResponse> = emptyList()

    fun bindData(attractions: List<TicketMasterAttractionResponse>){
        attractionList = attractions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionViewHolder {
        val binding = ItemAttractionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AttractionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AttractionViewHolder, position: Int) {
        holder.bind(attractionList[position])
    }

    override fun getItemCount(): Int {
        return attractionList.size
    }

    inner class AttractionViewHolder(
        val binding: ItemAttractionBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(attraction: TicketMasterAttractionResponse){
            binding.attractionNameTextView.text = attraction.name
            if(attraction.images.isNotEmpty()){
                binding.attractionImageView.load(attraction.images[0].url)
            }else{
                binding.attractionImageView.load(R.drawable.venyoo)
            }

            itemView.setOnClickListener {
                itemClicked(attraction)
            }
        }
    }
}