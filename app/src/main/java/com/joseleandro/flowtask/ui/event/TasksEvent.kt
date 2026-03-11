package com.joseleandro.flowtask.ui.event

import com.joseleandro.flowtask.domain.model.Tag

sealed interface TasksEvent {

    data class OnSelectedTagFilter(val tag: Tag?) : TasksEvent

    data class OnCompletedTask(val taskId: Long, val isDone: Boolean) : TasksEvent

}