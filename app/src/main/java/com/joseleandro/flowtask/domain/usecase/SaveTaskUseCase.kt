package com.joseleandro.flowtask.domain.usecase

import com.joseleandro.flowtask.domain.model.Task
import com.joseleandro.flowtask.domain.repositoty.TaskRepository

class SaveTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.save(task = task)
    }
}