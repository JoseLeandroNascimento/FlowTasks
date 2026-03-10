package com.joseleandro.flowtask.domain.model

data class SubtaskItem(
    val id: Long = 0,
    val code: String,
    val title: String,
    val done: Boolean = false
)