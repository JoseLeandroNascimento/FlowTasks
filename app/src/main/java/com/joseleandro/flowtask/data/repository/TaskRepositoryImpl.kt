package com.joseleandro.flowtask.data.repository

import com.joseleandro.flowtask.data.datasource.TaskDataSource
import com.joseleandro.flowtask.data.mapper.toDomain
import com.joseleandro.flowtask.data.mapper.toEntity
import com.joseleandro.flowtask.domain.model.Task
import com.joseleandro.flowtask.domain.repositoty.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val taskDataSource: TaskDataSource
) : TaskRepository {

    override val tasksAll: Flow<List<Task>>
        get() = taskDataSource.tasksAll.map { tasks -> tasks.toDomain() }

    override val tasksFilter: Flow<List<Task>>
        get() = taskDataSource.tasksFilter.map { tasks -> tasks.toDomain() }

    override val tagFilter: Flow<Int?>
        get() = taskDataSource.tagFilter


    override suspend fun save(task: Task) {

        val (taskEntity, subtasksEntity) = task.toEntity()

        taskDataSource.insertTaskWithSubtasks(
            task = taskEntity,
            subtasks = subtasksEntity
        )
    }

    override suspend fun setTagFilter(tagId: Int?) {
        taskDataSource.setTagFilter(tagId = tagId)
    }

    override suspend fun completedTasks(taskId: Long, isDone: Boolean) {

        taskDataSource.completedTasks(taskId = taskId, isDone = isDone)
    }
}