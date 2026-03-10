package com.joseleandro.flowtask.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

const val FLOW_TASK_DATABASE_NAME = "flow_task_database"

@Database(
    entities = [
        TaskEntity::class,
        TagEntity::class,
        SubTaskEntity::class
    ], version = 6
)
abstract class FlowTaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    abstract fun tagDao(): TagDao

}