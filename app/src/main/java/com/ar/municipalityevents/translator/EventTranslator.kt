package com.ar.municipalityevents.translator

import android.os.Build
import androidx.annotation.RequiresApi
import com.ar.municipalityevents.dto.Event
import org.json.JSONArray
import kotlin.Throws
import org.json.JSONException
import org.json.JSONObject
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

object EventTranslator {

    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(JSONException::class)
    fun toDto(event: JSONObject): Event {
        return Event.Builder()
            .id(event.getString("id"))
            .name(event.getString("eventName"))
            .price(BigDecimal(event.getString("price")))
            .dateTime(this.parseDateTime(event.getString("eventDateTime")))
            .description(event.getString("eventDescription"))
            .imageName(event.getString("imageName"))
            .imageUrl(event.getString("image"))
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun parseDateTime(date: String): ZonedDateTime {
        var instant = Instant.ofEpochMilli(date.toLong())
        return ZonedDateTime.ofInstant(instant, ZoneId.of("UTC-0300"))
    }
}