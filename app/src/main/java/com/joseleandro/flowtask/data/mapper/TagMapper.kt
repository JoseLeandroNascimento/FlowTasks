package com.joseleandro.flowtask.data.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.fromColorLong
import com.joseleandro.flowtask.data.local.database.TagEntity
import com.joseleandro.flowtask.domain.model.Tag

fun TagEntity.toDomain() =
    with(this) {
        Tag(
            id = id,
            name = name,
            color = Color.fromColorLong(color)
        )
    }

fun List<TagEntity>.toDomain() =
    map { entity ->
        entity.toDomain()
    }

