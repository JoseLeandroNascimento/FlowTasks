package com.joseleandro.flowtask.ui.state

import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.ui.form.TaskFormState


data class CreateTaskUiState(
    val tags: List<Tag> = emptyList(),
    val form: TaskFormState = TaskFormState(),
    val isBack: Boolean = false,
    val showEmojiPicker: Boolean = false,
    val showRepeatSheet: Boolean = false,
    val showDueDateSheet: Boolean = false,
    val showReminderSheetVisible: Boolean = false,
    val showColorPickerBottomSheet: Boolean = false
)