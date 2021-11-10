package com.example.municipalityevents

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.municipalityevents.databinding.FragmentLoginBinding
import org.json.JSONException
import org.json.JSONObject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            if (checkDataEntered()){
                loginUser()
            }
        }

        binding.navButtonSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_SignUpFragment)
        }
    }

    private fun checkDataEntered(): Boolean{
        var result = true

        if (isEmpty(binding.email)) {
            binding.email.error = "Campo requerido"
            result = false
        }

        if(isEmpty(binding.password)) {
            binding.password.error = "Campo requerido"
            result = false
        }

        return result
    }

    private fun isEmpty(text: EditText): Boolean {
        val str: CharSequence = text.text.toString()
        return TextUtils.isEmpty(str)
    }

    private fun loginUser() {
        val postUrl = "http://10.0.2.2:3000/users/login"
        val requestQueue = Volley.newRequestQueue(activity as Context)
        val postData = JSONObject()
        try {
            postData.put("email", binding.email.text.toString() )
            postData.put("password", binding.password.text.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, postUrl, postData,
            { response ->
                Log.e("RESPONSE", response.toString())
                Toast.makeText(activity as Context,"LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show() }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}