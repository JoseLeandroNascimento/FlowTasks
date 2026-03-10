package com.joseleandro.flowtask.ui.state

import androidx.compose.runtime.Immutable
import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.domain.model.TaskGroupByStatus

@Immutable
data class TasksUiState(
    val tags: List<Tag> = emptyList(),
    val tasks: List<TaskGroupByStatus> = emptyList(),
    val tagFilterSelected: Tag? = null
)
