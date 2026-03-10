package com.joseleandro.flowtask.ui.extention

import android.content.Context
import com.joseleandro.flowtask.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

fun LocalDate?.toDueDateText(context: Context): String {

    if (this == null) {
        return context.getString(R.string.due_date_none)
    }

    val today = LocalDate.now()

    return when (this) {
        today -> context.getString(R.string.due_date_today)

        today.plusDays(1) ->
            context.getString(R.string.due_date_tomorrow)

        else -> {

            val diff = ChronoUnit.DAYS.between(today, this)

            if (diff in 2..6) {
                this.dayOfWeek.toLocalizedString(context)
            } else {
                val formatter =
                    DateTimeFormatter.ofPattern("dd MMM", Locale.getDefault())
                this.format(formatter)
            }
        }
    }
}

fun DayOfWeek.toLocalizedString(context: Context): String {
    return when (this) {
        DayOfWeek.MONDAY -> context.getString(R.string.weekday_monday)
        DayOfWeek.TUESDAY -> context.getString(R.string.weekday_tuesday)
        DayOfWeek.WEDNESDAY -> context.getString(R.string.weekday_wednesday)
        DayOfWeek.THURSDAY -> context.getString(R.string.weekday_thursday)
        DayOfWeek.FRIDAY -> context.getString(R.string.weekday_friday)
        DayOfWeek.SATURDAY -> context.getString(R.string.weekday_saturday)
        DayOfWeek.SUNDAY -> context.getString(R.string.weekday_sunday)
    }
}