package com.ar.municipalityevents.translator

import com.ar.municipalityevents.dto.SignUp
import org.json.JSONObject

object SignUpTranslator {

    fun toDto(email: String, password: String, name: String,
        surname: String, country: String, date: String): SignUp {

        return SignUp.Builder()
            .email(email)
            .name(name)
            .surname(surname)
            .date(date)
            .password(password)
            .country(country)
            .build()
    }

    fun toRequest(signUp: SignUp): JSONObject {
        return JSONObject()
            .put("email", signUp.email)
            .put("password", signUp.password)
            .put("name", signUp.name)
            .put("surname", signUp.surname)
            .put("date", signUp.date)
            .put("country", signUp.country)
    }
}