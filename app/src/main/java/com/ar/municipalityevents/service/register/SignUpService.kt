package com.ar.municipalityevents.service.register

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ar.municipalityevents.SignUpFragment
import com.ar.municipalityevents.dto.SignUp
import com.ar.municipalityevents.translator.SignUpTranslator
import org.json.JSONException
import org.json.JSONObject

class SignUpService{

    private var view: SignUpFragment? = null
    private var context: Context? = null

    fun attachView(view: SignUpFragment, context: Context) {
       this.view = view
        this.context = context
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

    fun signUp(signUpDto: SignUp) {
        this.signupUser(signUpDto)
    }


    private fun signupUser(signUpDto: SignUp) {
        val postUrl = "http://10.0.2.2:3000/users/signup"
        val requestQueue = Volley.newRequestQueue(context)
        var postData = JSONObject()
        try {
            postData = SignUpTranslator.toRequest(signUpDto)
        } catch (e: JSONException) {
            view?.showMessage("Ocurrió un error. Intente nuevamente.")
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, postUrl, postData,
            { response ->
                view?.saveToken(response.getString("token"))
                view?.navigateToLogin()
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