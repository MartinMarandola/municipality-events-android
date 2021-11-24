package com.ar.municipalityevents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ar.municipalityevents.databinding.ActivityProfileEventBinding
import com.ar.municipalityevents.dto.Event

class ProfileEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileEventBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent: Intent = intent
        val event: Event = intent.getSerializableExtra("event") as Event
        render(event)
        binding.buttonUrl.setOnClickListener {
            val viewIntent = Intent(
                "android.intent.action.VIEW",
                Uri.parse(event.url)
            )
            startActivity(viewIntent)
        }
    }

    private fun render(event: Event){
        binding.eventName.text = event.name
        binding.eventDate.text = event.dateTime
        binding.eventDescription.text = event.description
        binding.eventPrice.text = "$${event.price}"
    }

}