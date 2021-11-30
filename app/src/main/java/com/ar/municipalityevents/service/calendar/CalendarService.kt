package com.ar.municipalityevents.service.calendar

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ar.municipalityevents.CalendarActivity
import com.ar.municipalityevents.dto.Event
import com.ar.municipalityevents.translator.EventTranslator
import org.json.JSONException
import org.json.JSONObject
import java.time.ZonedDateTime

class CalendarService {

    private val url = "http://10.0.2.2:3000/events"
    private var view: CalendarActivity? = null
    private lateinit var queue: RequestQueue

    fun attachView(view: CalendarActivity) {
        this.view = view
    }

    @RequiresApi(Build.VERSION_CODES.O)
     fun setEventsAboutToday() {
        val today = ZonedDateTime.now()
        this.getApiData(today.monthValue.toString(), today.year.toString(), today.dayOfMonth)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getApiData(month: String, year: String, dayOfMonth: Int) {
        queue = Volley.newRequestQueue(view as Context)
        val getEventsUrl: String = getFormat(month, year)
        val request = JsonObjectRequest(
            getEventsUrl,
            { response: JSONObject? ->
                if (response!!.length() > 0) {
                    val resultList = this.setEvents(response, dayOfMonth)
                    view?.setAdapter(resultList.toMutableList())
                }
            }
        ) { error -> error.printStackTrace() }

        request.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setEvents(apiResponse: JSONObject, dayOfMonth: Int): List<Event> {
        val resultList = apiResponse.getJSONArray("events")
        val eventDataList: MutableList<Event> = ArrayList()
        for (i in 0 until resultList.length()) {
            try {
                val e = resultList.getJSONObject(i)
                val event = EventTranslator.toDto(e)

                if (event.dateTime?.dayOfMonth == dayOfMonth) {
                    eventDataList.add(event)

                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return eventDataList
    }

    private fun getFormat(month: String, year: String): String {
        return String.format("$url?year=%s&month=%s", year, month)
    }
}