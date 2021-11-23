package com.ar.municipalityevents

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ar.municipalityevents.databinding.ActivityCalendarBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class CalendarActivity : AppCompatActivity()/*, DatePickerDialog.OnDateSetListener*/ {

    //private var eventList: ListView
    //private var calendar: CalendarView
    //private var events: List<Event>()
    private lateinit var binding: ActivityCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getEvents()

        binding.calendarView.setOnDateChangeListener { calendarView, i, i2, i3 ->  }
    }

    private fun getEvents(){
        val url = "http://10.0.2.2:3000/events?year=2025&month=11"
        val requestQueue = Volley.newRequestQueue(this)


        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response -> Log.e("RESPONSE", response.toString())}
        ) { error -> error.printStackTrace() }

        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        requestQueue.add(jsonObjectRequest)
    }

    // attach to an onclick handler to show the date picker
    //fun showDatePickerDialog(v: View?) {
        //val newFragment = DatePickerFragment()
        //newFragment.show(supportFragmentManager, "datePicker")
    //}

    // handle the date selected
    /*override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        // store the values selected into a Calendar instance
        val c = Calendar.getInstance()
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = monthOfYear
        c[Calendar.DAY_OF_MONTH] = dayOfMonth
    }*/

    //val datePicker: DialogFragment = DatePickerFragment()

    /*override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
      val c = Calendar.getInstance();
      c.set(Calendar.YEAR, year)
      c.set(Calendar.MONTH, month)
      c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

      val currentDateString = DateFormat.getDateInstance().format(c.time) // date selected

  }*/
}