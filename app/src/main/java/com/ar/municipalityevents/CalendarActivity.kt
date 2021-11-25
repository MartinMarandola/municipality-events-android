package com.ar.municipalityevents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ar.municipalityevents.databinding.ActivityCalendarBinding
import com.ar.municipalityevents.dto.Event
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var query: String
    private lateinit var adapter: EventAdapter
    private val events = mutableListOf<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        val dateFormat = SimpleDateFormat("yyyy/MM/dd")
        val currentDate = dateFormat.format(Date())
        query = "year=${currentDate.slice(0..3)}&month=${currentDate.slice(5..6)}&day=${currentDate.slice(8..9)}"
        getEvents(query)

        binding.calendarView.setOnDateChangeListener { calendarView, year, month, day ->
            query = if(day < 10 && month+1 < 10){
                "year=$year&month=0${month+1}&day=0$day"
            }else if (day < 10){
                "year=$year&month=${month+1}&day=0$day"
            }else if (month+1 < 10){
                "year=$year&month=0${month+1}&day=$day"
            }else{
                "year=$year&month=${month+1}&day=$day"
            }
            getEvents(query)
        }
    }

    private fun getEvents(query: String){
        val url = "http://10.0.2.2:3000/events?$query"
        val requestQueue = Volley.newRequestQueue(this)


        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val jsonArray = response.getJSONArray("events")
                events.clear()
                for(i in 0 until jsonArray.length()){
                    try {
                        val responseObj = jsonArray.getJSONObject(i)

                        val name = responseObj.getString("eventName")
                        val dateTime = getDateTime(responseObj.getString("eventDateTime"))
                        val image = responseObj.getString("image")
                        val description = responseObj.getString("eventDescription")
                        val price = responseObj.getString("price").toBigDecimal()
                        val url = responseObj.getString("url")

                        events.add(Event(name, dateTime, image, description, price, url))
                    } catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
                adapter.notifyDataSetChanged()
            }
        ) { error -> error.printStackTrace() }

        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        requestQueue.add(jsonObjectRequest)
    }

    private fun getDateTime(s: String): String {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy, HH:mm")
            val netDate = Date(s.toLong())
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    private fun initRecyclerView(){
        adapter = EventAdapter(events)
        binding.rvEvent.layoutManager = LinearLayoutManager(this)
        binding.rvEvent.adapter = adapter
    }

}