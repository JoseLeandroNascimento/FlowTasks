package com.joseleandro.flowtask.domain.usecase

import com.joseleandro.flowtask.domain.model.TaskGroupByStatus
import com.joseleandro.flowtask.domain.model.TaskStatus
import com.joseleandro.flowtask.domain.repositoty.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TasksGroupByStatusUseCase(
    private val taskRepository: TaskRepository
) {

    operator fun invoke(): Flow<List<TaskGroupByStatus>> {
        return taskRepository.tasksAll.map { tasks ->
            tasks.groupBy { task -> task.isDone }
                .map { (isDone, tasksList) ->
                    val status = if (isDone) {
                        TaskStatus.DONE
                    } else {
                        TaskStatus.TODO
                    }

                    TaskGroupByStatus(
                        status = status,
                        tasks = tasksList
                    )
                }
        }
    }

}