package com.joseleandro.flowtask.data.local.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.joseleandro.flowtask.domain.model.RepeatType

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["id"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("tagId")]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String,

    val description: String? = null,

    val color: Long,

    val isDone: Boolean = false,

    val createdAt: Long,

    val dueDate: Long? = null,

    val tagId: Int? = null,

    val reminderAt: Long? = null,

    val iconType: String? = null,

    val iconEmoji: String? = null,

    val iconResId: Int? = null,

    val repeatType: RepeatType = RepeatType.NONE,

    val repeatInterval: Int? = null,

    val repeatDaysOfWeek: Int? = null,

    val repeatDaysOfMonth: String? = null,

    val priority: Int = 0
)
