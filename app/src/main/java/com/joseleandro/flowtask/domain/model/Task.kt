package com.joseleandro.flowtask.domain.model

import androidx.compose.ui.graphics.Color
import java.time.LocalDate


data class Task(
    val id: Long,
    val title: String,
    val subtasks: List<SubtaskItem>,
    val selectIcon: TaskIcon?,
    val isReminderEnabled: Boolean,
    val reminderHour: Int,
    val reminderMinute: Int,
    val dueDate: LocalDate?,
    val repeatConfig: RepeatConfig,
    val colorSelected: Color,
    val tagSelected: Tag?,
    val isDone: Boolean,
    val priority: Int = 0


)
