package com.ar.municipalityevents.service.login

import android.content.Context


interface LoginContract {

    interface View {
        fun showMessage(msg: String)
        fun signIn()
        fun showProgressBar()
        fun hideProgressBar()
        fun navigateToCalendar()
        fun navigateToRegister()
        fun saveToken(token: String)
    }

    interface Service{
        fun attachView(view:View)
        fun checkEmptyFields(email:String, password: String): Boolean
        fun signInWithEmailAndPassword(email:String, password:String, context: Context)
    }
}