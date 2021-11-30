package com.ar.municipalityevents

import android.os.Build
import android.os.Bundle
import android.widget.CalendarView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ar.municipalityevents.adapter.EventAdapter
import com.ar.municipalityevents.dto.Event
import com.ar.municipalityevents.service.calendar.CalendarService
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var service: CalendarService
    private lateinit var adapter: EventAdapter
    private lateinit var linearLayout: LinearLayout
    private lateinit var calendar: CalendarView
    private lateinit var recycler: RecyclerView
    private lateinit var eventDataList: MutableList<Event>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        this.init()
        service.setEventsAboutToday()
        this.getEventsAboutDaySelected()
    }

    private fun init() {
        service = CalendarService()
        service.attachView(this)
        linearLayout = findViewById(R.id.calendar)
        calendar = linearLayout.findViewById(R.id.calendarView)
        recycler = linearLayout.findViewById(R.id.rvEvent)
        eventDataList = ArrayList()
        recycler.layoutManager = LinearLayoutManager(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getEventsAboutDaySelected() {
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            if (!this.eventDataList.isNullOrEmpty()) this.eventDataList = arrayListOf()
            service.getApiData(month.plus(1).toString(), year.toString(), dayOfMonth)
        }
    }

    fun setAdapter(eventList: MutableList<Event>){
        eventDataList = eventList
        adapter = EventAdapter(eventDataList)
        recycler.adapter = adapter
    }
}