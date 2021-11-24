package com.ar.municipalityevents

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ar.municipalityevents.databinding.ItemEventBinding
import com.ar.municipalityevents.dto.Event
import java.io.Serializable

class EventAdapter(val events: List<Event>):RecyclerView.Adapter<EventAdapter.EventHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventHolder(layoutInflater.inflate(R.layout.item_event, parent, false))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.render(events[position])
    }

    class EventHolder(private val view:View):RecyclerView.ViewHolder(view), Serializable{

        private val binding = ItemEventBinding.bind(view)

        fun render(event: Event){
            binding.eventName.text = event.name
            binding.eventDescription.text = event.description
            binding.eventPrice.text = event.price.toString()
            view.setOnClickListener { view.context.startActivity(Intent(view.context, ProfileEventActivity::class.java).putExtra("event", event))
            }
        }
    }

}