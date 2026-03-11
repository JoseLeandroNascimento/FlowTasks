package com.joseleandro.flowtask.data.datasource

import com.joseleandro.flowtask.data.local.database.SubTaskEntity
import com.joseleandro.flowtask.data.local.database.TaskEntity
import com.joseleandro.flowtask.data.local.database.TaskWithDetails
import kotlinx.coroutines.flow.Flow

interface TaskDataSource {

    val tasksAll: Flow<List<TaskWithDetails>>

    val tasksFilter: Flow<List<TaskWithDetails>>

    val tagFilter: Flow<Int?>

    suspend fun insertTaskWithSubtasks(
        task: TaskEntity,
        subtasks: List<SubTaskEntity>
    )

    suspend fun setTagFilter(tagId: Int?)

    suspend fun completedTasks(taskId: Long, isDone: Boolean)

}