package com.example.municipalityevents

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.municipalityevents.databinding.FragmentCalendarBinding
import org.json.JSONException
import org.json.JSONObject

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)

        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //i año | i2 mes | i3 dia
        binding.calendarId.setOnDateChangeListener { calendarView, i, i2, i3 ->

            //llamar endpoint para traer eventos.
            getEvents(i2, i)
        }


    }

//hay que testear
    private fun getEvents(month : Int, year : Int) {
        val postUrl = "http://10.0.2.2:3000/events?month=" + month + "&year=" + year
        val requestQueue = Volley.newRequestQueue(activity as Context)
        val postData = JSONObject()
        try {

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, postUrl, postData,
            { response ->
                Log.e("RESPONSE", response.toString())
            }
        ) { error ->
            error.printStackTrace()
            Toast.makeText(activity as Context,"Error", Toast.LENGTH_SHORT).show()
        }

        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        requestQueue.add(jsonObjectRequest)
    }

}