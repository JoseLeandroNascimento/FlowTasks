package com.joseleandro.flowtask.domain.usecase

import com.joseleandro.flowtask.domain.repositoty.TagRepository

class DeleteTagByIdUseCase(
    private val tagRepository: TagRepository
) {

    suspend operator fun invoke(id: Int) {
        tagRepository.delete(id)
    }
}