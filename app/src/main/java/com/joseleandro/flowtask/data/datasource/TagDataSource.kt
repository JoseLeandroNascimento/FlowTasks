package com.joseleandro.flowtask.data.datasource

import com.joseleandro.flowtask.data.local.database.TagEntity
import kotlinx.coroutines.flow.Flow

interface TagDataSource {

    val tags: Flow<List<TagEntity>>

    suspend fun save(tag: TagEntity)

    suspend fun delete(vararg ids: Int)

    suspend fun getTagById(id: Int): TagEntity?

}