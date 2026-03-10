package com.joseleandro.flowtask.data.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.fromColorLong
import com.joseleandro.flowtask.core.util.LocalDateTimeUtil.getHour
import com.joseleandro.flowtask.core.util.LocalDateTimeUtil.getMinute
import com.joseleandro.flowtask.core.util.LocalDateUtil.toLocalDate
import com.joseleandro.flowtask.core.util.LocalDateUtil.toLong
import com.joseleandro.flowtask.data.local.database.SubTaskEntity
import com.joseleandro.flowtask.data.local.database.TaskEntity
import com.joseleandro.flowtask.data.local.database.TaskWithDetails
import com.joseleandro.flowtask.domain.model.RepeatConfig
import com.joseleandro.flowtask.domain.model.RepeatType
import com.joseleandro.flowtask.domain.model.Task
import com.joseleandro.flowtask.domain.model.TaskIcon
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

data class RepeatEntityFields(
    val repeatType: Int,
    val repeatInterval: Int?,
    val repeatDaysOfWeek: Int?,
    val repeatDaysOfMonth: String?
)

fun TaskWithDetails.toDomain(): Task {

    val hour = task.reminderAt?.getHour()
    val minute = task.reminderAt?.getMinute()

    val icon = when (task.iconType) {

        "EMOJI" -> task.iconEmoji?.let {
            TaskIcon.Emoji(it)
        }

        "DRAWABLE" -> task.iconResId?.let {
            TaskIcon.Drawable(it)
        }

        else -> null
    }


    return Task(
        id = task.id,
        colorSelected = Color.fromColorLong(task.color),
        title = task.title,
        subtasks = subtasks.toDomain(),
        dueDate = task.dueDate?.toLocalDate(),
        selectIcon = icon,
        tagSelected = tag?.toDomain(),
        isReminderEnabled = task.reminderAt != null,
        reminderHour = hour ?: 0,
        reminderMinute = minute ?: 0,
        repeatConfig = toRepeatConfig(),
        isDone = task.isDone
    )

}

fun List<TaskWithDetails>.toDomain(): List<Task> {
    return this.map { tasks -> tasks.toDomain() }
}


fun Task.toEntity(): Pair<TaskEntity, List<SubTaskEntity>> {

    val repeatFields = repeatConfig.toEntityFields()

    val iconType: String?
    val iconEmoji: String?
    val iconResId: Int?

    when (val icon = selectIcon) {

        is TaskIcon.Emoji -> {
            iconType = "EMOJI"
            iconEmoji = icon.value
            iconResId = null
        }

        is TaskIcon.Drawable -> {
            iconType = "DRAWABLE"
            iconEmoji = null
            iconResId = icon.resId
        }

        null -> {
            iconType = null
            iconEmoji = null
            iconResId = null
        }
    }

    val reminderAt = if (isReminderEnabled) {
        toReminderMillis(reminderHour, reminderMinute)
    } else {
        null
    }

    val taskEntity = TaskEntity(
        id = id,
        title = title,
        description = null,
        color = colorSelected.value.toLong(),
        isDone = false,
        createdAt = if (id == 0L) {
            System.currentTimeMillis()
        } else 0,
        dueDate = dueDate?.toLong(),
        tagId = tagSelected?.id,
        reminderAt = reminderAt,
        repeatType = RepeatType.fromValue(repeatFields.repeatType),
        repeatInterval = repeatFields.repeatInterval,
        repeatDaysOfWeek = repeatFields.repeatDaysOfWeek,
        repeatDaysOfMonth = repeatFields.repeatDaysOfMonth,
        iconType = iconType,
        iconEmoji = iconEmoji,
        iconResId = iconResId
    )

    val subtasksEntity = subtasks.map {

        SubTaskEntity(
            id = it.id,
            taskId = id,
            title = it.title,
            isDone = it.done
        )
    }

    return taskEntity to subtasksEntity
}

fun toReminderMillis(
    hour: Int,
    minute: Int
): Long {

    val now = LocalDate.now()

    val dateTime = LocalDateTime.of(
        now.year,
        now.month,
        now.dayOfMonth,
        hour,
        minute
    )

    return dateTime
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}


fun TaskWithDetails.toRepeatConfig(): RepeatConfig {
    return toRepeatConfig(
        repeatType = task.repeatType.value,
        repeatInterval = task.repeatInterval,
        repeatDaysOfWeek = task.repeatDaysOfWeek,
        repeatDaysOfMonth = task.repeatDaysOfMonth
    )
}

fun toRepeatConfig(
    repeatType: Int,
    repeatInterval: Int?,
    repeatDaysOfWeek: Int?,
    repeatDaysOfMonth: String?
): RepeatConfig {

    return when (repeatType) {

        RepeatType.DAILY.value -> {
            RepeatConfig.Daily(
                interval = repeatInterval ?: 1
            )
        }

        RepeatType.WEEKLY.value -> {
            RepeatConfig.Weekly(
                daysOfWeek = repeatDaysOfWeek
                    ?.toDaysOfWeek()
                    ?: emptySet()
            )
        }

        RepeatType.MONTHLY.value -> {
            RepeatConfig.Monthly(
                daysOfMonth = repeatDaysOfMonth
                    ?.toDaysOfMonth()
                    ?: emptySet()
            )
        }

        else -> RepeatConfig.None
    }
}


fun RepeatConfig.toEntityFields(): RepeatEntityFields {

    return when (this) {

        RepeatConfig.None ->
            RepeatEntityFields(
                RepeatType.NONE.value,
                null,
                null,
                null
            )

        is RepeatConfig.Daily ->
            RepeatEntityFields(
                RepeatType.DAILY.value,
                interval,
                null,
                null
            )

        is RepeatConfig.Weekly ->
            RepeatEntityFields(
                RepeatType.WEEKLY.value,
                null,
                daysOfWeek.toBitMask(),
                null
            )

        is RepeatConfig.Monthly ->
            RepeatEntityFields(
                RepeatType.MONTHLY.value,
                null,
                null,
                daysOfMonth.toDbString()
            )
    }
}

fun Set<DayOfWeek>.toBitMask(): Int {
    var mask = 0
    for (day in this) {
        mask = mask or (1 shl day.ordinal)
    }
    return mask
}

fun Int.toDaysOfWeek(): Set<DayOfWeek> {
    val days = mutableSetOf<DayOfWeek>()

    DayOfWeek.entries.forEach {
        if (this and (1 shl it.ordinal) != 0) {
            days.add(it)
        }
    }

    return days
}

fun Set<Int>.toDbString(): String =
    joinToString(",")

fun String.toDaysOfMonth(): Set<Int> =
    split(",").map { it.toInt() }.toSet()