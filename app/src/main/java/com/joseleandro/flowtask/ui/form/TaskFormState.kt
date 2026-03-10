package com.joseleandro.flowtask.ui.form

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.joseleandro.flowtask.domain.model.RepeatConfig
import com.joseleandro.flowtask.domain.model.SubtaskItem
import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.domain.model.TaskIcon
import com.joseleandro.flowtask.ui.form.validators.NotBlankValidator
import com.joseleandro.flowtask.ui.screen.task.components.solid.solidIcons
import com.joseleandro.flowtask.ui.theme.ColorPickerPallet
import java.time.LocalDate

@Immutable
data class TaskFormState(
    val title: Field<String> = Field(
        value = "",
        validators = listOf(NotBlankValidator())
    ),
    val colorSelected: Field<Color> = Field(ColorPickerPallet.colorDefault),
    val tagSelected: Field<Tag?> = Field(null),
    val dueDate: LocalDate? = null,
    val repeatConfig: RepeatConfig = RepeatConfig.None,
    val reminderHour: Int = 0,
    val reminderMinute: Int = 0,
    val isReminderEnabled: Boolean = false,
    val subtasks: List<SubtaskItem> = emptyList(),
    val selectIcon: TaskIcon = TaskIcon.Drawable(
        resId = solidIcons.get(0).resId
    ),
)