package com.joseleandro.flowtask.ui.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joseleandro.flowtask.domain.model.RepeatConfig
import com.joseleandro.flowtask.domain.model.SubtaskItem
import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.domain.model.Task
import com.joseleandro.flowtask.domain.model.TaskIcon
import com.joseleandro.flowtask.domain.usecase.SaveTaskUseCase
import com.joseleandro.flowtask.domain.usecase.TagsAllUseCase
import com.joseleandro.flowtask.ui.event.CreateTaskEvent
import com.joseleandro.flowtask.ui.state.CreateTaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID


class CreateTaskViewModel(
    private val tagsAllUseCase: TagsAllUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTaskUiState())
    val uiState: StateFlow<CreateTaskUiState> = _uiState.asStateFlow()

    init {
        loadTags()
    }

    fun onEvent(event: CreateTaskEvent) {

        when (event) {
            is CreateTaskEvent.ChangeVisibilityColorPickerBottomSheet -> changeVisibilityColorPickerBottomSheet(
                event.visibility
            )

            is CreateTaskEvent.OnChangeColor -> updateColor(event.color)

            is CreateTaskEvent.OnChangeTag -> updateTag(event.tag)

            is CreateTaskEvent.ChangeRepeat -> updateRepeatConfig(event.repeatConfig)

            is CreateTaskEvent.ChangeVisibilityRepeatSheet -> changeVisibilityRepeatBottomSheet(
                event.visibility
            )

            is CreateTaskEvent.ChangeVisibilityDueDateSheet -> changeVisibilityDueDateBottomSheet(
                event.visibility
            )

            is CreateTaskEvent.ChangeDueDate -> updateDueDate(event.date)

            is CreateTaskEvent.ChangeVisibilityReminderSheet -> changeVisibilityReminderSheet(event.visibility)

            is CreateTaskEvent.ToggleReminder -> updateToggleReminder(event.enabled)

            is CreateTaskEvent.ChangeReminderHour -> updateReminderHour(event.hour)

            is CreateTaskEvent.ChangeReminderMinute -> updateReminderMinute(event.minute)

            is CreateTaskEvent.AddSubtask -> addSubtask(event.title)

            is CreateTaskEvent.RemoveSubtask -> removeSubtask(event.id)

            is CreateTaskEvent.ToggleSubtask -> toggleSubtask(event.id)

            is CreateTaskEvent.SelectIcon -> updateSelectIcon(event.icon)

            is CreateTaskEvent.ChangeVisibilityEmojiPicker -> changeVisibilityEmojiPicker(event.visibility)

            CreateTaskEvent.OnSave -> saveTask()
            is CreateTaskEvent.OnChangeTitle -> updateTitle(event.text)

            is CreateTaskEvent.OnResetUiState -> resetUiState()
        }
    }

    private fun loadTags() {

        viewModelScope.launch {
            tagsAllUseCase().collect { tags ->
                _uiState.value = _uiState.value.copy(
                    tags = tags
                )
            }
        }
    }

    private fun validateForm(): Boolean {


        return with(_uiState.value) {

            val validatedTitle = form.title.updateValue(form.title.value)

            _uiState.update {
                it.copy(
                    form = form.copy(title = validatedTitle)
                )
            }

            validatedTitle.isValid
        }
    }

    private fun CreateTaskUiState.toTask(): Task {

        return with(form) {
            Task(
                id = 0,
                title = title.value,
                dueDate = dueDate,
                colorSelected = colorSelected.value,
                priority = 0,
                subtasks = subtasks,
                tagSelected = tagSelected.value,
                selectIcon = selectIcon,
                repeatConfig = repeatConfig,
                reminderHour = reminderHour,
                reminderMinute = reminderMinute,
                isDone = false,
                isReminderEnabled = isReminderEnabled
            )
        }
    }

    private fun saveTask() {

        if (!validateForm()) return

        viewModelScope.launch {
            saveTaskUseCase(_uiState.value.toTask())

            _uiState.update { state ->
                state.copy(
                    isBack = true
                )
            }
        }
    }

    private fun resetUiState() {
        _uiState.value = CreateTaskUiState()
    }

    private fun toggleSubtask(id: String) {

        _uiState.update { state ->

            val updated = state.form.subtasks.map { subtask ->

                if (subtask.code == id) {
                    subtask.copy(done = !subtask.done)
                } else {
                    subtask
                }
            }

            state.copy(
                form = state.form.copy(
                    subtasks = updated
                )
            )
        }
    }

    private fun removeSubtask(code: String) {
        _uiState.update { state ->
            state.copy(
                form = state.form.copy(
                    subtasks = state.form.subtasks.filterNot { it.code == code }
                )
            )
        }
    }

    private fun addSubtask(title: String) {

        val newSubtask = SubtaskItem(
            code = UUID.randomUUID().toString(),
            title = title
        )

        _uiState.update {
            it.copy(
                form = it.form.copy(
                    subtasks = it.form.subtasks + newSubtask
                )
            )
        }
    }

    private fun updateTitle(text: String) {

        _uiState.update {
            it.copy(
                form = it.form.copy(
                    title = it.form.title.updateValue(text)
                )
            )
        }
    }


    private fun updateSelectIcon(icon: TaskIcon) {
        _uiState.update {
            it.copy(
                form = it.form.copy(selectIcon = icon),
                showEmojiPicker = false
            )
        }
    }

    private fun updateReminderHour(hour: Int) {
        _uiState.update {
            it.copy(
                form = it.form.copy(
                    reminderHour = hour
                )
            )
        }
    }

    private fun updateReminderMinute(minute: Int) {
        _uiState.update {
            it.copy(
                form = it.form.copy(
                    reminderMinute = minute
                )
            )
        }
    }

    private fun updateToggleReminder(enabled: Boolean) {
        _uiState.update {
            it.copy(
                form = it.form.copy(
                    isReminderEnabled = enabled
                )
            )
        }
    }

    private fun updateDueDate(date: LocalDate?) {
        _uiState.update {
            it.copy(
                form = it.form.copy(
                    dueDate = date
                )
            )
        }

        changeVisibilityDueDateBottomSheet(false)
    }

    private fun updateRepeatConfig(repeatConfig: RepeatConfig) {
        _uiState.update {
            it.copy(
                form = it.form.copy(
                    repeatConfig = repeatConfig,
                )
            )
        }
        changeVisibilityRepeatBottomSheet(false)
    }

    private fun updateColor(color: Color) {
        _uiState.update { state ->
            state.copy(
                form = state.form.copy(
                    colorSelected = state.form.colorSelected.copy(value = color)
                )
            )
        }
    }

    private fun updateTag(tag: Tag?) {
        _uiState.update { state ->

            with(state) {

                val tagSelected =
                    if (state.form.tagSelected.value == tag) {
                        null
                    } else {
                        tag
                    }

                copy(
                    form = state.form.copy(
                        tagSelected = state.form.tagSelected.copy(value = tagSelected)
                    )
                )
            }
        }
    }

    private fun changeVisibilityDueDateBottomSheet(visibility: Boolean? = null) {
        _uiState.update { state ->
            state.copy(
                showDueDateSheet = visibility ?: !state.showDueDateSheet
            )
        }
    }

    private fun changeVisibilityColorPickerBottomSheet(visibility: Boolean? = null) {
        _uiState.update { state ->
            state.copy(
                showColorPickerBottomSheet = visibility ?: !state.showColorPickerBottomSheet
            )
        }
    }

    private fun changeVisibilityRepeatBottomSheet(visibility: Boolean? = null) {
        _uiState.update { state ->
            state.copy(
                showRepeatSheet = visibility ?: !state.showRepeatSheet
            )
        }
    }

    private fun changeVisibilityReminderSheet(visibility: Boolean? = null) {
        _uiState.update { state ->
            state.copy(
                showReminderSheetVisible = visibility ?: !state.showReminderSheetVisible
            )
        }
    }

    private fun changeVisibilityEmojiPicker(visibility: Boolean? = null) {
        _uiState.update { state ->
            state.copy(
                showEmojiPicker = visibility ?: !state.showEmojiPicker
            )
        }
    }


}