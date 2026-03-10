package com.joseleandro.flowtask.core.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

object LocalDateUtil {

    fun Long.toLocalDate(): LocalDate {
        return Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    fun LocalDate.toLong(): Long {
        return atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}