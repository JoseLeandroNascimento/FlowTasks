package com.joseleandro.flowtask.domain.usecase

import androidx.compose.ui.graphics.Color
import com.joseleandro.flowtask.domain.repositoty.TagRepository

class InsertTagUseCase(
    private val tagRepository: TagRepository
) {

    suspend operator fun invoke(
        id: Int? = null,
        name: String,
        color: Color
    ) {
        tagRepository.save(
            id = id,
            name = name,
            color = color
        )
    }

}