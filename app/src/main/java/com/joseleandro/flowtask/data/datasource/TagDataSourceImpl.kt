package com.joseleandro.flowtask.data.datasource

import com.joseleandro.flowtask.data.local.database.TagDao
import com.joseleandro.flowtask.data.local.database.TagEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class TagDataSourceImpl(
    private val tagDao: TagDao
) : TagDataSource {

    override val tags: Flow<List<TagEntity>>
        get() = tagDao.getAll().flowOn(Dispatchers.IO)

    override suspend fun insert(tag: TagEntity) {
        withContext(Dispatchers.IO) {
            tagDao.save(tag)
        }
    }

    override suspend fun delete(vararg ids: Int) {
        withContext(Dispatchers.IO) {
            tagDao.delete(*ids)
        }
    }

}