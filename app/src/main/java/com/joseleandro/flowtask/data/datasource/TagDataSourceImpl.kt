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

    override suspend fun save(tag: TagEntity) {

        val updatedAt = System.currentTimeMillis()
        var createdAt = tag.createdAt

        if (tag.id == 0) {
            createdAt = System.currentTimeMillis()
        }

        val tagNew = tag.copy(
            updatedAt = updatedAt,
            createdAt = createdAt
        )

        withContext(Dispatchers.IO) {

            if (tag.id == 0) {
                tagDao.save(
                    tag = tagNew
                )
            } else {
                tagDao.update(
                    tag = tagNew
                )
            }
        }
    }

    override suspend fun delete(vararg ids: Int) {
        withContext(Dispatchers.IO) {
            tagDao.delete(*ids)
        }
    }

    override suspend fun getTagById(id: Int): TagEntity? {
        return tagDao.getById(id)
    }

}