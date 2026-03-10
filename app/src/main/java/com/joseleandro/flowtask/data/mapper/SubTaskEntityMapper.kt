package com.joseleandro.flowtask.data.mapper

import com.joseleandro.flowtask.data.local.database.SubTaskEntity
import com.joseleandro.flowtask.domain.model.SubtaskItem
import java.util.UUID

fun SubTaskEntity.toDomain(): SubtaskItem {

    return SubtaskItem(
        id = id,
        title = title,
        done = isDone,
        code = UUID.randomUUID().toString()
    )
}

fun List<SubTaskEntity>.toDomain(): List<SubtaskItem> {
    return this.map { subtasks -> subtasks.toDomain() }
}
