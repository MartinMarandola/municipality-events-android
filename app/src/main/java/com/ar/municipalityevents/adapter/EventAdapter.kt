package com.ar.municipalityevents.adapter

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ar.municipalityevents.ProfileEventActivity
import com.ar.municipalityevents.R
import com.ar.municipalityevents.databinding.ItemEventBinding
import com.ar.municipalityevents.dto.Event
import java.io.Serializable
import java.time.temporal.ChronoField

class EventAdapter(private val events: List<Event>):RecyclerView.Adapter<EventAdapter.EventHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventHolder(layoutInflater.inflate(R.layout.item_event, parent, false))
    }

    override fun getItemCount(): Int = events.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.render(events[position])
    }

    class EventHolder(private val view:View):RecyclerView.ViewHolder(view), Serializable {

        private val binding = ItemEventBinding.bind(view)

        @RequiresApi(Build.VERSION_CODES.O)
        fun render(event: Event){
            binding.eventName.text = event.name
            binding.eventSchedule.text = event.dateTime?.let {
                String.format("Horario: %s hs.", it.get(ChronoField.HOUR_OF_DAY)) }
            view.setOnClickListener {
                view.context.startActivity(Intent(view.context, ProfileEventActivity::class.java)
                    .putExtra("event", event))
            }
        }
    }

}