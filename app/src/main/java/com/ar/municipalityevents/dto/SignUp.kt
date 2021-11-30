package com.ar.municipalityevents.dto

class SignUp (val email: String?,
              val name: String?,
              val surname: String?,
              val date: String?,
              val country: String?,
              val password: String?){

    data class Builder(var email: String?= null,
                       var name: String?= null,
                       var surname: String?= null,
                       var date: String?= null,
                       var country: String?= null,
                       var password: String?= null) {

        fun email(email: String) = apply { this.email = email }
        fun name(name: String) = apply { this.name = name }
        fun surname(surname: String) = apply { this.surname = surname }
        fun date(date: String) = apply { this.date = date }
        fun country(country: String) = apply { this.country = country }
        fun password(password: String) = apply { this.password = password }

        fun build() = SignUp(email, name, surname, date, country, password)
    }
}