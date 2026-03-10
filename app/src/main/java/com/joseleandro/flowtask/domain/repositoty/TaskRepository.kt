package com.joseleandro.flowtask.domain.repositoty

import com.joseleandro.flowtask.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    val tasksAll: Flow<List<Task>>

    suspend fun save(task: Task)
}