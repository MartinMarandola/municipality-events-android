package com.ar.municipalityevents.translator

import android.os.Build
import androidx.annotation.RequiresApi
import com.ar.municipalityevents.dto.Event
import kotlin.Throws
import org.json.JSONException
import org.json.JSONObject
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

object EventTranslator {

    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(JSONException::class)
    fun toDto(event: JSONObject): Event {

        val result = Event.Builder()
            .id(event.getString("id"))
            .name(event.getString("eventName"))
            .dateTime(this.parseDateTime(event.getString("eventDateTime")))
            .description(event.getString("eventDescription"))

        if (event.optString("image").isNotEmpty()) result.imageUrl(event.getString("image"))
        if (event.optString("url").isNotEmpty()) result.url(event.getString("url"))
        if (event.optString("price").isNotEmpty()) result.price(BigDecimal(event.getString("price")))

        return result.build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun parseDateTime(date: String): ZonedDateTime {
        var instant = Instant.ofEpochMilli(date.toLong())
        return ZonedDateTime.ofInstant(instant, ZoneId.of("UTC-0300"))
    }
}