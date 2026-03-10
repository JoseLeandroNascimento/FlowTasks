package com.joseleandro.flowtask.data.repository

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toColorLong
import com.joseleandro.flowtask.data.datasource.TagDataSource
import com.joseleandro.flowtask.data.local.database.TagEntity
import com.joseleandro.flowtask.data.mapper.toDomain
import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.domain.repositoty.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TagRepositoryImpl(
    private val tagDataSource: TagDataSource
) : TagRepository {

    override val tags: Flow<List<Tag>>
        get() = tagDataSource.tags.map { entities ->
            entities.toDomain()
        }

    override suspend fun save(id: Int?, name: String, color: Color) {

        if (name.trim().isBlank()) {
            throw IllegalArgumentException("O nome da tag não pode ser vazio")
        }

        val tag = tagDataSource.getTagById(id ?: 0)

        val data = TagEntity(
            id = id ?: 0,
            name = name,
            color = color.toColorLong(),
            createdAt = tag?.createdAt ?: 0,
            updatedAt = tag?.createdAt ?: 0
        )

        tagDataSource.save(data)

    }

    override suspend fun delete(vararg ids: Int) {
        tagDataSource.delete(*ids)
    }

}