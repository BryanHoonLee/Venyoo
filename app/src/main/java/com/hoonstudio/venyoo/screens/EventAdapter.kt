package com.hoonstudio.venyoo.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hoonstudio.venyoo.R
import com.hoonstudio.venyoo.databinding.ItemEventsBinding
import com.hoonstudio.venyoo.venues.TicketMasterConvertedLocalData
import com.hoonstudio.venyoo.venues.TicketMasterEventResponse


class EventAdapter(
    private val itemClicked: (TicketMasterEventResponse) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    private var eventsList: List<TicketMasterEventResponse> = emptyList()

    fun bindData(events: List<TicketMasterEventResponse>) {
        eventsList = ArrayList(sortListByDate(events))
        notifyDataSetChanged()
    }

    private fun sortListByDate(events: List<TicketMasterEventResponse>): List<TicketMasterEventResponse> {
        val sortedList: MutableList<TicketMasterEventResponse> = events.toMutableList()
        return sortedList.sortedBy { event ->
            val localDateSplit = event.dates.start.localDate.split("-")
            val year = localDateSplit[0]
            val month = localDateSplit[1]
            val day = localDateSplit[2]
            val date = year + month + day
            date.toInt()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(eventsList[position])
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    inner class EventViewHolder(
        val binding: ItemEventsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: TicketMasterEventResponse) {

            if (event.images.isNotEmpty()) {
                binding.eventImageView.load(event.images[0].url)
            } else {
                binding.eventImageView.load(R.drawable.venyoo)
            }

            val convertedDate = ticketMasterLocalDateConverter(event.dates.start.localDate)
            binding.eventDateTextView.text = "${convertedDate.month} ${convertedDate.day}"
            if (event._embedded.attractions.isNotEmpty()) {
                binding.attractionNameTextView.text = event._embedded.attractions[0].name
            }

            if(!event.priceRanges.isNullOrEmpty()) {
                val priceRange = event.priceRanges[0]
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

            val eventStatus = event.dates.status.code
            if (eventStatus != null) {
                binding.eventStatusTextView.text = eventStatus.codeName
            } else {
                binding.eventStatusTextView.text = ""
            }

            itemView.setOnClickListener {
                itemClicked(event)
            }
        }
    }
}

fun ticketMasterLocalDateConverter(localDate: String): TicketMasterConvertedLocalData {
    if (localDate.isNullOrBlank()) {
        return TicketMasterConvertedLocalData()
    } else {
        val localDateSplit = localDate.split("-")
        val year = localDateSplit[0]
        val month = localDateSplit[1]
        val day = localDateSplit[2]
        var monthConverted = ""
        when (month) {
            "01" -> monthConverted = "Jan"
            "02" -> monthConverted = "Feb"
            "03" -> monthConverted = "Mar"
            "04" -> monthConverted = "Apr"
            "05" -> monthConverted = "May"
            "06" -> monthConverted = "Jun"
            "07" -> monthConverted = "Jul"
            "08" -> monthConverted = "Aug"
            "09" -> monthConverted = "Sep"
            "10" -> monthConverted = "Oct"
            "11" -> monthConverted = "Nov"
            "12" -> monthConverted = "Dec"
        }

        val convertedDate = TicketMasterConvertedLocalData(monthConverted, day, year)
        return convertedDate
    }
}