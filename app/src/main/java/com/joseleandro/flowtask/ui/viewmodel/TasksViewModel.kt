package com.joseleandro.flowtask.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.domain.usecase.TagsAllUseCase
import com.joseleandro.flowtask.domain.usecase.TasksGroupByStatusUseCase
import com.joseleandro.flowtask.ui.event.TasksEvent
import com.joseleandro.flowtask.ui.state.TasksUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksViewModel(
    private val tagsAllUseCase: TagsAllUseCase,
    private val tasksGroupByStatusUseCase: TasksGroupByStatusUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    init {
        loadTags()
        loadTasks()
    }

    fun onEvent(event: TasksEvent) {

        when (event) {
            is TasksEvent.OnSelectedTagFilter -> selectedTagFilter(event.tag)
        }
    }

    private fun loadTasks() {

        viewModelScope.launch {
            tasksGroupByStatusUseCase().collect { tasks ->
                _uiState.value = _uiState.value.copy(
                    tasks = tasks
                )
            }
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

    private fun selectedTagFilter(tag: Tag?) {

        _uiState.update { state ->
            state.copy(
                tagFilterSelected = tag
            )
        }

    }
}