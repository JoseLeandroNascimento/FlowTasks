package com.joseleandro.flowtask.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Insert
    suspend fun save(tag: TagEntity)

    @Update
    suspend fun update(tag: TagEntity)

    @Query("SELECT * FROM TagEntity")
    fun getAll(): Flow<List<TagEntity>>

    @Query("SELECT * FROM TagEntity WHERE id = :id")
    fun getFlowById(id: Int): Flow<TagEntity>

    @Query("SELECT * FROM TagEntity WHERE id = :id")
    suspend fun getById(id: Int): TagEntity?

    @Query("DELETE FROM TagEntity WHERE id IN (:ids)")
    suspend fun delete(vararg ids: Int)

}