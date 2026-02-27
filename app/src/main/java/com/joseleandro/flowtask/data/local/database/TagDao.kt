package com.joseleandro.flowtask.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Insert
    suspend fun save(tag: TagEntity)

    @Query("SELECT * FROM TagEntity")
    fun getAll(): Flow<List<TagEntity>>

}