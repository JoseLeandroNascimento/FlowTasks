package com.joseleandro.flowtask.domain.repositoty

import com.joseleandro.flowtask.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    val tasksAll: Flow<List<Task>>

    val tasksFilter: Flow<List<Task>>

    val tagFilter: Flow<Int?>

    suspend fun save(task: Task)

    suspend fun setTagFilter(tagId: Int?)

    suspend fun completedTasks(taskId: Long, isDone: Boolean)
}