package com.joseleandro.flowtask.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val color: Long,
    val createdAt: Long,
    val updatedAt: Long
)
