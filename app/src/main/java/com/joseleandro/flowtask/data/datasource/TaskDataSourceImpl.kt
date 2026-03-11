package com.joseleandro.flowtask.data.datasource

import androidx.room.Transaction
import com.joseleandro.flowtask.data.local.database.SubTaskEntity
import com.joseleandro.flowtask.data.local.database.TaskDao
import com.joseleandro.flowtask.data.local.database.TaskEntity
import com.joseleandro.flowtask.data.local.database.TaskWithDetails
import com.joseleandro.flowtask.data.local.datastore.TaskPreferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn

class TaskDataSourceImpl(
    private val taskDao: TaskDao,
    private val taskPreferencesDataStore: TaskPreferencesDataStore
) : TaskDataSource {

    override val tasksAll: Flow<List<TaskWithDetails>>
        get() = taskDao.getTasks().flowOn(Dispatchers.IO)

    override val tasksFilter: Flow<List<TaskWithDetails>>
        get() = this.tasksAll.combine(taskPreferencesDataStore.tagFilter) { tasks, tagId ->
            if (tagId == null) {
                tasks
            } else {
                tasks.filter { task ->
                    task.task.tagId == tagId
                }
            }
        }

    override val tagFilter: Flow<Int?>
        get() = taskPreferencesDataStore.tagFilter

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

    override suspend fun setTagFilter(tagId: Int?) {
        taskPreferencesDataStore.setTagFilter(tagId = tagId)
    }

    override suspend fun completedTasks(taskId: Long, isDone: Boolean) {
        taskDao.completedTasks(taskId = taskId, isDone = isDone)
    }

}