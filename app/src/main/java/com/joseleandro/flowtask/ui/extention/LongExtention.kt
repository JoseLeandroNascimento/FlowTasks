package com.joseleandro.flowtask.ui.extention

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long?.toDueDateText(): String {

    if (this == null) return "Sem prazo"

    val formatter = DateTimeFormatter.ofPattern("dd MMM")

    val date = Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val today = LocalDate.now()

    return when (date) {
        today -> "Hoje"
        today.plusDays(1) -> "Amanhã"
        else -> date.format(formatter)
    }
}