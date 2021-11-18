package com.ar.municipalityevents

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.ar.municipalityevents.R
import java.util.*

class CalendarActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    //private var eventList: ListView
    //private var calendar: CalendarView
    //private var events: List<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
    }

    // attach to an onclick handler to show the date picker
    //fun showDatePickerDialog(v: View?) {
        //val newFragment = DatePickerFragment()
        //newFragment.show(supportFragmentManager, "datePicker")
    //}

    // handle the date selected
    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        // store the values selected into a Calendar instance
        val c = Calendar.getInstance()
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = monthOfYear
        c[Calendar.DAY_OF_MONTH] = dayOfMonth
    }
}