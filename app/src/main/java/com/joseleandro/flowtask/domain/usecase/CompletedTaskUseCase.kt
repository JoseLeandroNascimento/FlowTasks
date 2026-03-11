package com.joseleandro.flowtask.domain.usecase

import com.joseleandro.flowtask.domain.repositoty.TaskRepository

class CompletedTaskUseCase(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(taskId: Long, isDone: Boolean) {
        taskRepository.completedTasks(taskId = taskId, isDone = isDone)
    }
}