package com.example.municipalityevents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.municipalityevents.entities.Event
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)


        val evento = intent.getSerializableExtra("evento") as Event
        nombre_evento.text = evento.nombre
        descripcion_evento.text = evento.descripcion
        imagen_evento.setImageResource(evento.imagen)
    }
}