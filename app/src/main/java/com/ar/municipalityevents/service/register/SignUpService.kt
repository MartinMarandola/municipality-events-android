package com.ar.municipalityevents.service.register

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ar.municipalityevents.SignUpFragment
import com.ar.municipalityevents.service.login.LoginService
import org.json.JSONException
import org.json.JSONObject

class SignUpService{

    private var view: SignUpFragment? = null
    private lateinit var loginService: LoginService

    fun attachView(view: SignUpFragment) {
       this.view = view
    }

    fun checkEmail(email: EditText): Boolean {
        val stringMail: CharSequence = email.text.toString()
        return stringMail.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(stringMail).matches()
    }

    fun checkLength(str: EditText): Boolean {
        val min = 6
        val max = 20
        return str.length() < min || str.length() > max
    }

    fun checkString(str: String): Boolean {
        return str.isEmpty()
    }

    fun signUp(
        email: String,
        password: String,
        name: String,
        surname: String,
        date: String,
        country: String,
        context: Context
    ) {
        this.signupUser(email, password, name, surname, date, country, context)
    }


    private fun signupUser(email: String, password: String, name: String , surname: String, date: String,
                           country: String, context: Context) {
        val postUrl = "http://10.0.2.2:3000/users/signup"
        val requestQueue = Volley.newRequestQueue(context)
        val postData = JSONObject()
        try {
            postData.put("email", email )
            postData.put("password", password)
            postData.put("name", name)
            postData.put("surname", surname)
            postData.put("date", date)
            postData.put("country", country)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, postUrl, postData,
            { response ->
                view?.saveToken(response.getString("token"))
                loginService.signInWithEmailAndPassword(email, password, context);
            }
        ) { error ->
            error.printStackTrace()
            view?.showMessage("Error")
        }

        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        requestQueue.add(jsonObjectRequest)
    }
}