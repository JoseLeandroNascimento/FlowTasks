package com.joseleandro.flowtask.core.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object LocalDateTimeUtil {

    fun Long.toLocalDateTime(): LocalDateTime {
        return Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    fun Long.getHour(): Int {
        return this.toLocalDateTime().hour
    }

    fun Long.getMinute(): Int {
        return this.toLocalDateTime().minute
    }
}