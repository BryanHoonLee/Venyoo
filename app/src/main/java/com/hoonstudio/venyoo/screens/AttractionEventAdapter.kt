package com.hoonstudio.venyoo.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ImageRequest
import coil.request.ImageResult
import com.hoonstudio.venyoo.R
import com.hoonstudio.venyoo.databinding.FragmentVenueResultListBinding
import com.hoonstudio.venyoo.databinding.ItemAttractionEventBinding
import com.hoonstudio.venyoo.venues.TicketMasterEventResponse

class AttractionEventAdapter(
    val itemClicked: (TicketMasterEventResponse) -> Unit
): RecyclerView.Adapter<AttractionEventAdapter.AttractionEventViewHolder>(){
    private var eventsList: List<TicketMasterEventResponse> = emptyList()

    fun bindData(events: List<TicketMasterEventResponse>){
        eventsList = events
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionEventViewHolder {
        val binding = ItemAttractionEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AttractionEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AttractionEventViewHolder, position: Int) {
        holder.bind(eventsList[position])

    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    inner class AttractionEventViewHolder(
        val binding: ItemAttractionEventBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(eventResponse: TicketMasterEventResponse){
            val venue = eventResponse._embedded.venues[0]

            binding.eventNameTextView.text = eventResponse.name
            binding.venueNameTextView.text = venue.name

            var address = venue.address.line1
            if(!venue.address.line2.isNullOrBlank()) address += ", ${venue.address.line2}"
            if(!venue.address.line3.isNullOrBlank()) address += ", ${venue.address.line3}"
            binding.addressTextView.text = address
            binding.cityStatePostalTextView.text = "${venue.city.name}, ${venue.state.stateCode} ${venue.postalCode}"

            if(eventResponse.images.isNotEmpty()){
                binding.venueImageView.scaleType = ImageView.ScaleType.CENTER_CROP
                binding.venueImageView.load(eventResponse.images[0].url){
                    listener(onSuccess = { request: ImageRequest, metadata: ImageResult.Metadata ->
                        binding.progressCircular.visibility = View.GONE
                    })
                }
            }else{
                binding.venueImageView.load(R.drawable.venyoo)
                binding.venueImageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                binding.progressCircular.visibility = View.GONE
            }

            if(!eventResponse.priceRanges.isNullOrEmpty()) {
                val priceRange = eventResponse.priceRanges[0]
                if (priceRange.min < 45) {
                    binding.priceRangeTextView.text = "$"
                } else if (priceRange.min >= 45 && priceRange.min < 90) {
                    binding.priceRangeTextView.text = "$$"
                } else {
                    binding.priceRangeTextView.text = "$$$"
                }
            }else{
                binding.priceRangeTextView.text = ""
            }

            val convertedDate = ticketMasterLocalDateConverter(eventResponse.dates.start.localDate)
            if(convertedDate.month.isNullOrBlank() || convertedDate.day.isNullOrBlank()){
                binding.eventDateTextView.text = "Date TBA"
            }else{
                binding.eventDateTextView.text = "${convertedDate.month} ${convertedDate.day}, ${convertedDate.year}"
            }

            itemView.setOnClickListener {
                itemClicked(eventResponse)
            }
        }

    }
}
