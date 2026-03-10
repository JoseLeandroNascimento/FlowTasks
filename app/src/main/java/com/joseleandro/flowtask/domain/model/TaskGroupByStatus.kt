package com.joseleandro.flowtask.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class TaskGroupByStatus(
    val status: TaskStatus,
    val tasks: List<Task>
)
