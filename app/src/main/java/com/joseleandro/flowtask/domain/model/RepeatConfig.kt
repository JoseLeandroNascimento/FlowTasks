package com.joseleandro.flowtask.domain.model

import java.time.DayOfWeek

sealed class RepeatConfig {
    object None : RepeatConfig()

    data class Daily(
        val interval: Int = 1
    ) : RepeatConfig()

    data class Weekly(
        val daysOfWeek: Set<DayOfWeek> = setOf()
    ) : RepeatConfig()

    data class Monthly(
        val daysOfMonth: Set<Int> = setOf()
    ) : RepeatConfig()
}