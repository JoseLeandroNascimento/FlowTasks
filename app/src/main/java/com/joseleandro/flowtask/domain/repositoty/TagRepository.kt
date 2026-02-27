package com.joseleandro.flowtask.domain.repositoty

import androidx.compose.ui.graphics.Color
import com.joseleandro.flowtask.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {

    val tags: Flow<List<Tag>>

    suspend fun save(
        id: Int? = null,
        name: String,
        color: Color,
    )


}