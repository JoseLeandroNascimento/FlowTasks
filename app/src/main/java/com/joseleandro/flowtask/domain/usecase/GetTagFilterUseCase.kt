package com.joseleandro.flowtask.domain.usecase

import com.joseleandro.flowtask.domain.repositoty.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTagFilterUseCase(
    private val taskRepository: TaskRepository
) {

    operator fun invoke(): Flow<Int?> {
        return taskRepository.tagFilter
    }
}