package com.joseleandro.flowtask.data.local.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: TaskEntity)
}