package com.joseleandro.flowtask.data.datasource

import com.joseleandro.flowtask.data.local.database.SubTaskEntity
import com.joseleandro.flowtask.data.local.database.TaskEntity
import com.joseleandro.flowtask.data.local.database.TaskWithDetails
import kotlinx.coroutines.flow.Flow

interface TaskDataSource {

    val tasksAll: Flow<List<TaskWithDetails>>

    suspend fun insertTaskWithSubtasks(
        task: TaskEntity,
        subtasks: List<SubTaskEntity>
    )

}