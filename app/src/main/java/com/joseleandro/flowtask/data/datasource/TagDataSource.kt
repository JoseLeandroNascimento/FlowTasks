package com.joseleandro.flowtask.data.datasource

import com.joseleandro.flowtask.data.local.database.TagEntity
import kotlinx.coroutines.flow.Flow

interface TagDataSource {

    val tags: Flow<List<TagEntity>>

    suspend fun insert(tag: TagEntity)

}