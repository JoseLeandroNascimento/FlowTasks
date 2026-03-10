package com.joseleandro.flowtask.ui.event

import androidx.compose.ui.graphics.Color
import com.joseleandro.flowtask.domain.model.RepeatConfig
import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.domain.model.TaskIcon
import java.time.LocalDate

sealed interface CreateTaskEvent {

    data class ChangeVisibilityColorPickerBottomSheet(val visibility: Boolean? = null) :
        CreateTaskEvent

    data class OnChangeColor(val color: Color) : CreateTaskEvent

    data class OnChangeTag(val tag: Tag?) : CreateTaskEvent

    data class ChangeVisibilityRepeatSheet(val visibility: Boolean? = null) : CreateTaskEvent

    data class ChangeVisibilityDueDateSheet(val visibility: Boolean? = null) : CreateTaskEvent

    data class ChangeRepeat(val repeatConfig: RepeatConfig) : CreateTaskEvent

    data class ChangeVisibilityReminderSheet(val visibility: Boolean? = null) : CreateTaskEvent

    data class ChangeDueDate(val date: LocalDate?) : CreateTaskEvent


    data class ToggleReminder(val enabled: Boolean) : CreateTaskEvent

    data class ChangeReminderHour(val hour: Int) : CreateTaskEvent

    data class ChangeReminderMinute(val minute: Int) : CreateTaskEvent

    data class AddSubtask(val title: String) : CreateTaskEvent

    data class ToggleSubtask(val id: String) : CreateTaskEvent

    data class RemoveSubtask(val id: String) : CreateTaskEvent

    data class ChangeVisibilityEmojiPicker(val visibility: Boolean? = null) : CreateTaskEvent

    data class SelectIcon(
        val icon: TaskIcon
    ) : CreateTaskEvent

    data object OnSave : CreateTaskEvent

    data class OnChangeTitle(val text: String) : CreateTaskEvent

    data object OnResetUiState : CreateTaskEvent

}