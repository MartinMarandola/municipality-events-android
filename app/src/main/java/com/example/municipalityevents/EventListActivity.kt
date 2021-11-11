package com.example.municipalityevents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.municipalityevents.entities.Event
import com.example.municipalityevents.entities.EventAdapter
import kotlinx.android.synthetic.main.activity_event_list.*
import kotlinx.android.synthetic.main.item_event.*

class EventListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)


        val event = Event("Primer evento", "descripcion del primero evento", R.drawable.ic_baseline_event_24)
        val event_2 = Event("Segundo evento", "descripcion del segundo evento", R.drawable.ic_baseline_event_24)


        val listaEventos = listOf(event, event_2)

        val adapter = EventAdapter(this, listaEventos)

        eventList.adapter = adapter
        eventList.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, EventActivity::class.java)
            intent.putExtra("evento", listaEventos[position])
            startActivity(intent)

        }


    }
}