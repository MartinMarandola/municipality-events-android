package com.ar.municipalityevents.service.register

import android.content.Context
import android.widget.EditText

interface SignUpContract {

    interface View{
        fun signUp()
        fun showProgress()
        fun hideProgress()
        fun showMessage(msg: String)
        fun saveToken(token: String)
        fun navigateToCalendar()
    }

    interface Service{
        fun attachView(view:View)
        fun checkEmail(email: EditText): Boolean
        fun checkString(str: String): Boolean
        fun checkLength(str: EditText): Boolean
        fun signUp(email: String, password: String, name: String, surname:String,
                   date: String, country: String, context: Context)
    }
}