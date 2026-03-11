package com.joseleandro.flowtask.ui.viewmodel

import com.joseleandro.flowtask.domain.repositoty.TaskRepository

class FilterTasksUseCase(
    private val taskRepository: TaskRepository
) {

    suspend fun invoke(tagId: Int?) {
        taskRepository.setTagFilter(tagId = tagId)
    }
}