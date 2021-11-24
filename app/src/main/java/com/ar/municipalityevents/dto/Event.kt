package com.ar.municipalityevents.dto

import java.io.Serializable
import java.math.BigDecimal
import java.time.ZonedDateTime

/*
class Event {

    val id: String? = null
    val name: String? = null
    val description: String? = null
    val dateTime: ZonedDateTime? = null
    val price: BigDecimal? = null
    val imageName: String? = null
    val imageUrl: String? = null
    val url: String? = null
}*/

data class Event(val name: String, val dateTime: String, val image: String, val description: String, val price: BigDecimal, val url: String) : Serializable
