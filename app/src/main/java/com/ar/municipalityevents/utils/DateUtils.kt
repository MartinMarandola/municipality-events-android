package com.ar.municipalityevents.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime

object DateUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateNow(): LocalDate {
        return ZonedDateTime.now().toLocalDate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToLocalDate(year: Int, month: Int, day: Int): LocalDate {
        return LocalDate.of(year, month.plus(1), day)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDate(zonedDateTime: ZonedDateTime): LocalDate {
        return zonedDateTime.toLocalDate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTime(zonedDateTime: ZonedDateTime): LocalTime {
        return zonedDateTime.toLocalTime()
    }

    fun getNumberString(number: Int): String {
        if (number < 10) return String.format("0%s", number.toString());
        return number.toString()
    }

}