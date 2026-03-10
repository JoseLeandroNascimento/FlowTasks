package com.joseleandro.flowtask.data.datasource

import androidx.room.Transaction
import com.joseleandro.flowtask.data.local.database.SubTaskEntity
import com.joseleandro.flowtask.data.local.database.TaskDao
import com.joseleandro.flowtask.data.local.database.TaskEntity
import com.joseleandro.flowtask.data.local.database.TaskWithDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class TaskDataSourceImpl(
    private val taskDao: TaskDao
) : TaskDataSource {

    override val tasksAll: Flow<List<TaskWithDetails>>
        get() = taskDao.getTasks().flowOn(Dispatchers.IO)

    @Transaction
    override suspend fun insertTaskWithSubtasks(
        task: TaskEntity,
        subtasks: List<SubTaskEntity>
    ) {

        val taskId = taskDao.insert(task)

        val subtasksWithTaskId = subtasks.map {
            it.copy(taskId = taskId)
        }

        taskDao.insertSubtasks(subtasksWithTaskId)
    }

}