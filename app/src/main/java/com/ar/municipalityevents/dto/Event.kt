package com.ar.municipalityevents.dto

import java.io.Serializable
import java.math.BigDecimal

data class Event(val name: String, val dateTime: String, val image: String, val description: String, val price: BigDecimal, val url: String? = null) : Serializable
