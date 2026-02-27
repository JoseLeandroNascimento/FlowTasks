package com.joseleandro.flowtask.domain.usecase

import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.domain.repositoty.TagRepository
import kotlinx.coroutines.flow.Flow

class TagsAllUseCase(
    private val tagRepository: TagRepository
) {

    operator fun invoke(): Flow<List<Tag>> {
        return tagRepository.tags
    }
}