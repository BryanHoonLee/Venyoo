package com.example.venyoo.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.venyoo.R
import com.example.venyoo.venues.VenueResponse
import com.google.android.material.textview.MaterialTextView

class VenueResponseAdapter(
        private val onItemClicked: (VenueResponse) -> Unit
) : RecyclerView.Adapter<VenueResponseAdapter.VenueViewHolder>() {

    private var venueList: List<VenueResponse> = ArrayList(0)

    fun bindData(venues: List<VenueResponse>){
        venueList = ArrayList(venues)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_venue, parent, false)
        return VenueViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        val currentVenue = venueList[position]
        holder.name.text = currentVenue.name
        holder.description.text = currentVenue.name

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
        val name: MaterialTextView = view.findViewById(R.id.name_text_view)
        val description: MaterialTextView = view.findViewById(R.id.description_text_view)

    }
}