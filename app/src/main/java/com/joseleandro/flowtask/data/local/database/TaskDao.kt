package com.joseleandro.flowtask.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskEntity): Long

    @Insert
    suspend fun insertSubtasks(subtasks: List<SubTaskEntity>)

    @Transaction
    @Query("SELECT * FROM taskentity")
    fun getTasks(): Flow<List<TaskWithDetails>>

}