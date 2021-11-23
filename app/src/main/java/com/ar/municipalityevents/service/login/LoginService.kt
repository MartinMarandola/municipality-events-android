package com.ar.municipalityevents.service.login

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class LoginService() : LoginContract.Service{

    var view: LoginContract.View? = null

    override fun attachView(view: LoginContract.View) {
        this.view = view
    }

    override fun signInWithEmailAndPassword(email: String, password: String, context: Context) {
        view?.showProgressBar()
        this.loginUser(email, password, context)
        if(view!= null){
          //  view?.hideProgressBar()

        }
    }

    override fun checkEmptyFields(email: String, password: String): Boolean{
        return email.isEmpty() || password.isEmpty()
    }

    private fun loginUser(email: String, password: String, context: Context) {
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
                Log.e("RESPONSE", response.toString())
                view?.saveToken(response.getString("token"))
                view?.showMessage("LOGIN SUCCESSFUL")
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