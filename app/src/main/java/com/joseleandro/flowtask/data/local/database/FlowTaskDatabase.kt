package com.joseleandro.flowtask.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

const val FLOW_TASK_DATABASE_NAME = "flow_task_database"

@Database(entities = [Task::class], version = 1)
abstract class FlowTaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

}