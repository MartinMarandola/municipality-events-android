package com.ar.municipalityevents

import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.LinearLayout
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ar.municipalityevents.dto.Event
import com.ar.municipalityevents.service.calendar.CalendarService
import com.ar.municipalityevents.translator.EventTranslator
import org.json.JSONException
import org.json.JSONObject
import kotlin.collections.ArrayList

class CalendarActivity : AppCompatActivity() {

    lateinit var service: CalendarService
    private lateinit var eventList: ListView
    private lateinit var calendar: CalendarView
    private lateinit var queue: RequestQueue
    private val url = "http://10.0.2.2:3000/events"
    private lateinit var eventDataList: List<String>
    private lateinit var linearLayout: LinearLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        service = CalendarService()
        linearLayout = findViewById(R.id.calendar)
        calendar = linearLayout.findViewById(R.id.calendarView)
        eventList = linearLayout.findViewById(R.id.dailyView1)
        queue = Volley.newRequestQueue(this)
        eventDataList = ArrayList()

        this.getDaySelected()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDaySelected(){
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            if(!this.eventDataList.isNullOrEmpty()) this.eventDataList = arrayListOf()
            this.getApiData(month.plus(1).toString(), year.toString(), dayOfMonth)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getApiData(month: String, year: String, dayOfMonth: Int) {
        val getEventsUrl: String = getFormat(month, year)
        val request = JsonObjectRequest(
            getEventsUrl,
            { response: JSONObject? ->
                if (response!!.length() > 0) this.setEvents(response, dayOfMonth)
                this.setAdapter()
            }
        ) { error: VolleyError? ->

        }

        queue.add(request)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setEvents(apiResponse: JSONObject, dayOfMonth: Int): List<Event> {
        val result: MutableList<Event> = ArrayList()
        val resultList = apiResponse.getJSONArray("events")
        for (i in 0 until resultList.length()) {
            try {
                val e = resultList.getJSONObject(i)
                val event = EventTranslator.toDto(e)

                if (event.dateTime?.dayOfMonth == dayOfMonth) {
                    result.add(event)
                    event.name?.let { (eventDataList as ArrayList<String>).add(it) }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return result
    }

    private fun getFormat(month: String, year: String): String {
        return String.format("$url?year=%s&month=%s", year, month)
    }

    private fun setAdapter(){
        eventList.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, eventDataList)
    }
}