package com.ar.municipalityevents.dto

import java.math.BigDecimal
import java.time.Instant
import java.time.ZonedDateTime

class Event(val id: String?,
            val name: String?,
            val description: String?,
            val dateTime: ZonedDateTime?,
            val price: BigDecimal?,
            val imageName: String?,
            val imageUrl: String?){

    data class Builder(var id: String?= null,
                       var name: String?= null,
                       var description: String?= null,
                       var dateTime: ZonedDateTime?= null,
                       var price: BigDecimal?= null,
                       var imageName: String?= null,
                       var imageUrl: String?= null){

        fun id(id: String)= apply { this.id = id }
        fun name(name: String)= apply { this.name = name }
        fun description(description: String)= apply { this.description = description }
        fun dateTime(dateTime: ZonedDateTime)= apply { this.dateTime = dateTime }
        fun price(price: BigDecimal)= apply { this.price = price }
        fun imageName(imageName: String)= apply { this.imageName = imageName }
        fun imageUrl(imageUrl: String)= apply { this.imageUrl = imageUrl }

        fun build() = Event(id, name, description, dateTime, price, imageName, imageUrl)
    }
}