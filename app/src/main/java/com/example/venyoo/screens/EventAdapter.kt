package com.example.venyoo.screens

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venyoo.databinding.ItemEventsBinding
import com.example.venyoo.venues.TicketMasterConvertedLocalData
import com.example.venyoo.venues.TicketMasterEventDateStart
import com.example.venyoo.venues.TicketMasterEventResponse

fun ticketMasterLocalDateConverter(localDate: String): TicketMasterConvertedLocalData{
    if(localDate.isNullOrBlank()){
        return TicketMasterConvertedLocalData()
    }else{
        val localDateSplit = localDate.split("-")
        val year = localDateSplit[0]
        val month = localDateSplit[1]
        val day = localDateSplit[2]
        var monthConverted = ""
        when(month){
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

class EventAdapter(
    private val itemClicked: (TicketMasterEventResponse) -> Unit
): RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    private var eventsList: List<TicketMasterEventResponse> = emptyList()

    fun bindData(events: List<TicketMasterEventResponse>){
        eventsList = ArrayList(events)
        notifyDataSetChanged()
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
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(event: TicketMasterEventResponse){


            val convertedDate = ticketMasterLocalDateConverter(event.dates.start.localDate)
            binding.eventMonthTextView.text = convertedDate.month
            binding.eventDayTextView.text = convertedDate.day
            binding.attractionNameTextView.text = event._embedded.attractions[0].name
            val eventStatus = event.dates.status.code
            if(eventStatus != null) {
                binding.eventStatusTextView.text = event.dates.status.code.codeName
            }else{
                binding.eventStatusTextView.text = ""
            }
            Log.d("EVENT", "attractions: ${event._embedded.attractions.size}")
            Log.d("Event", "Null?: ${event.dates.status}")

            itemView.setOnClickListener {
                itemClicked(event)
            }
        }
    }
}