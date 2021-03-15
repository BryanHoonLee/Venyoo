package com.example.venyoo.screens

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ImageRequest
import coil.request.ImageResult
import com.example.venyoo.R
import com.example.venyoo.venues.TicketMasterVenueResponse
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView

class VenueResponseAdapter(
        private val onItemClicked: (TicketMasterVenueResponse) -> Unit
) : RecyclerView.Adapter<VenueResponseAdapter.VenueViewHolder>() {

    private var venueList: List<TicketMasterVenueResponse> = ArrayList(0)

    fun bindData(venues: List<TicketMasterVenueResponse>){
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

        var address = currentVenue.address.line1
        if(!currentVenue.address.line2.isNullOrBlank()) address += ", ${currentVenue.address.line2}"
        if(!currentVenue.address.line3.isNullOrBlank()) address += ", ${currentVenue.address.line3}"
        holder.address.text = address

        holder.cityStatePostal.text = "${currentVenue.city.name}, ${currentVenue.state.stateCode} ${currentVenue.postalCode}"

        if(currentVenue.distance == null || currentVenue.distance <= 0.0) holder.distance.text = "" else holder.distance.text = "${currentVenue.distance} mi"

        if(currentVenue.images.isNotEmpty()){
            holder.venueImage.load(currentVenue.images[0].url){
                listener(onSuccess = { request: ImageRequest, metadata: ImageResult.Metadata ->
                    holder.progressBar.visibility = View.GONE
                })
            }
        }else{
            holder.venueImage.load(R.drawable.ic_launcher_foreground)
            holder.progressBar.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            onItemClicked(venueList[position])
        }
    }

    override fun getItemCount(): Int {
        return venueList.size
    }

//    override fun getItemId(position: Int): Long {
//        val venue = venueList[position]
//        return venue.id.hashCode().toLong()
//    }

    inner class VenueViewHolder(
            view: View
    ) : RecyclerView.ViewHolder(view) {
        val name: MaterialTextView = view.findViewById(R.id.name_text_view)
        val address: MaterialTextView = view.findViewById(R.id.address_text_view)
        val cityStatePostal: MaterialTextView = view.findViewById(R.id.city_state_postal_text_view)
        val distance: MaterialTextView = view.findViewById(R.id.distance_text_view)
        val venueImage: ImageView = view.findViewById(R.id.venue_image_view)
        val progressBar: CircularProgressIndicator = view.findViewById(R.id.progress_circular)
    }
}