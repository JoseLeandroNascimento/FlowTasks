package com.joseleandro.flowtask.domain.model

enum class RepeatType(val label: String, val value: Int) {
    NONE("Não repete", 0),
    DAILY("Diário", 1),
    WEEKLY("Semanal", 2),
    MONTHLY("Mensal", 3);

    companion object {

        fun fromValue(value: Int): RepeatType {
            return entries.firstOrNull { it.value == value }
                ?: NONE
        }

    }
}
