package com.example.venyoo.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.venyoo.venues.VenueResponse

class VenueResponseAdapter(
        private val onItemClicked: (VenueResponse) -> Unit
) : RecyclerView.Adapter<VenueResponseAdapter.VenueViewHolder>() {

    private var venueList: List<VenueResponse> = ArrayList(0)

    fun bindData(venues: List<VenueResponse>){
        venueList = venues
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout, parent, false)
        return VenueViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            onItemClicked(venueList[position])
        }
    }

    override fun getItemCount(): Int {
        return venueList.size
    }

    inner class VenueViewHolder(
            view: View
    ) : RecyclerView.ViewHolder(view) {
//        val venueName: TextView = view.findViewById(R.id.)

    }
}