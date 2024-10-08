package com.ar.municipalityevents.service.calendar

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ar.municipalityevents.view.CalendarActivity
import com.ar.municipalityevents.dto.Event
import com.ar.municipalityevents.translator.EventTranslator
import com.ar.municipalityevents.utils.DateUtils
import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*
import kotlin.collections.ArrayList

class CalendarService {

    private val url = "http://10.0.2.2:3000/events"
    private var view: CalendarActivity? = null
    private lateinit var queue: RequestQueue

    fun attachView(view: CalendarActivity) {
        this.view = view
    }

    @RequiresApi(Build.VERSION_CODES.O)
     fun setEventsAboutToday() {
        this.getApiData(DateUtils.dateNow())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getApiData(date: LocalDate) {
        queue = Volley.newRequestQueue(view as Context)
        val getEventsUrl: String = getFormat(date)
        val request = JsonObjectRequest(
            getEventsUrl,
            { response: JSONObject? ->
                if (response!!.length() > 0) {
                    val resultList = this.setEvents(response)
                    if(resultList.isEmpty())
                        view?.showMessage("No hay eventos")
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
    private fun setEvents(apiResponse: JSONObject): List<Event> {
        val resultList = apiResponse.getJSONArray("events")
        val eventDataList: MutableList<Event> = ArrayList()
        for (i in 0 until resultList.length()) {
            try {
                val e = resultList.getJSONObject(i)
                val event = EventTranslator.toDto(e)
                eventDataList.add(event)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return eventDataList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getFormat(date: LocalDate): String {
        val day = DateUtils.getNumberString(date.dayOfMonth)
        val month = DateUtils.getNumberString(date.monthValue)
        val year = date.year.toString()
        return String.format("%s?year=%s&month=%s&day=%s", url, year, month, day)
    }
}