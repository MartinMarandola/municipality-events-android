package com.ar.municipalityevents.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ar.municipalityevents.databinding.ActivityProfileEventBinding
import com.ar.municipalityevents.dto.Event
import com.ar.municipalityevents.utils.DateUtils
import com.squareup.picasso.Picasso
import java.util.*

class ProfileEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileEventBinding


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent: Intent = intent
        val event: Event = intent.getSerializableExtra("event") as Event
        render(event)

        if(event.url.isNullOrBlank()){
            binding.buttonUrl.visibility = View.GONE
        } else {
            binding.buttonUrl.visibility = View.VISIBLE
            binding.buttonUrl.setOnClickListener {
                val viewIntent = Intent(
                    "android.intent.action.VIEW",
                    Uri.parse(event.url)
                )
                startActivity(viewIntent)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun render(event: Event){
        val date = event.dateTime?.let { DateUtils.getDate(it)}
        val time = event.dateTime?.let { DateUtils.getTime(it) }

        binding.eventName.text = event.name
        binding.eventDate.text = String.format("%s %shs", date, time)
        binding.eventDescription.text = event.description
        if(event.price != null) {
            binding.eventPrice.text = String.format("Valor $%s", event.price.toString())
        }
        Picasso.get().load(event.imageUrl).into(binding.imageView)
    }

}