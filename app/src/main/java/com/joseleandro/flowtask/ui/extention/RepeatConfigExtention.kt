package com.joseleandro.flowtask.ui.extention

import com.joseleandro.flowtask.domain.model.RepeatConfig

fun RepeatConfig.toReadableText(): String {
    return when (this) {
        RepeatConfig.None -> "Não repetir"
        is RepeatConfig.Daily -> "Diariamente"
        is RepeatConfig.Weekly -> "Semanal"
        is RepeatConfig.Monthly -> "Mensalmente"
    }
}