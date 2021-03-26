package com.example.venyoo.screens

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venyoo.databinding.ItemEventsBinding
import com.example.venyoo.venues.TicketMasterEventResponse

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


            binding.eventDateTextView.text = event.dates.start.localDate
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