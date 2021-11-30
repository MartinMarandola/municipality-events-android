package com.ar.municipalityevents.service.login

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ar.municipalityevents.LoginFragment
import org.json.JSONException
import org.json.JSONObject

class LoginService{

    private var view: LoginFragment? = null
    private var context: Context? = null

    fun attachView(view: LoginFragment, context: Context) {
        this.view = view
        this.context = context
    }


    fun signInWithEmailAndPassword(email: String, password: String) {
        view?.showProgressBar()
        this.loginUser(email, password)
        if(view!= null){
          //  view?.hideProgressBar()

        }
    }

    fun checkEmptyFields(email: String, password: String): Boolean{
        return email.isEmpty() || password.isEmpty()
    }

    private fun loginUser(email: String, password: String) {
        val postUrl = "http://10.0.2.2:3000/users/login"
        val requestQueue = Volley.newRequestQueue(context)
        val postData = JSONObject()
        try {
            postData.put("email", email)
            postData.put("password", password)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, postUrl, postData,
            { response ->
                view?.saveToken(response.getString("token"))
                view?.navigateToCalendar()
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