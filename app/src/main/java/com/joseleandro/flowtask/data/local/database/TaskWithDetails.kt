package com.joseleandro.flowtask.data.local.database

import androidx.room.Embedded
import androidx.room.Relation

data class TaskWithDetails(

    @Embedded
    val task: TaskEntity,

    @Relation(
        parentColumn = "tagId",
        entityColumn = "id"
    )
    val tag: TagEntity?,

    @Relation(
        parentColumn = "id",
        entityColumn = "taskId"
    )
    val subtasks: List<SubTaskEntity>
)