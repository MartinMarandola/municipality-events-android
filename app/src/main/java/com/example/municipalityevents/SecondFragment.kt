package com.example.municipalityevents

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.municipalityevents.databinding.FragmentSecondBinding
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.util.Patterns
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.date.setOnClickListener {showDatePickerDialog()}

        binding.buttonSignup.setOnClickListener {
            if (checkDataEntered()){
                signupUser()
            }
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(parentFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int){
        if(day < 10 && month+1 < 10){
            binding.date.setText("$year-0${month+1}-0$day")
        }else if (day < 10){
            binding.date.setText("$year-${month+1}-0$day")
        }else if (month+1 < 10){
            binding.date.setText("$year-0${month+1}-$day")
        }else{
            binding.date.setText("$year-${month+1}-$day")
        }

    }

    private fun checkDataEntered(): Boolean{
        var result = true

        if (!isEmail(binding.email)) {
            binding.email.error = "Campo requerido"
            result = false
        }

        if(isEmpty(binding.password)) {
            binding.password.error = "Campo requerido"
            result = false
        } else if(checkLength(binding.password, 6, 20)){
            binding.password.error = "La contraseña debe estar compuesta entre 6 y 20 carácteres"
            result = false
        }

        if (isEmpty(binding.name)) {
            binding.name.error = "Campo requerido"
            result = false
        }

        if (isEmpty(binding.surname)) {
            binding.surname.error = "Campo requerido"
            result = false
        }

        if (isEmpty(binding.date)) {
            binding.date.error = "Campo requerido"
            result = false
        }

        return result
    }

    private fun checkLength(str: EditText, min: Int, max: Int): Boolean {
        return str.length() < min || str.length() > max
    }

    private fun isEmpty(text: EditText): Boolean {
        val str: CharSequence = text.text.toString()
        return TextUtils.isEmpty(str)
    }

    private fun isEmail(text: EditText): Boolean {
        val email: CharSequence = text.text.toString()
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun signupUser() {
        val postUrl = "http://10.0.2.2:3000/users/signup"
        val requestQueue = Volley.newRequestQueue(activity as Context)
        val postData = JSONObject()
        try {
            postData.put("email", binding.email.text.toString() )
            postData.put("password", binding.password.text.toString())
            postData.put("name", binding.name.text.toString())
            postData.put("surname", binding.surname.text.toString())
            postData.put("date", binding.date.text.toString())
            postData.put("country", binding.country.text.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, postUrl, postData,
            { response ->
                Log.e("RESPONSE", response.toString())
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment) }
        ) { error ->
            error.printStackTrace()
            Toast.makeText(activity as Context,"Error",Toast.LENGTH_SHORT).show();
        }

        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        requestQueue.add(jsonObjectRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}