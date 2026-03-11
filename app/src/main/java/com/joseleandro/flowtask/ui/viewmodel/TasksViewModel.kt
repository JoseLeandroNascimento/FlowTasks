package com.joseleandro.flowtask.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.domain.usecase.CompletedTaskUseCase
import com.joseleandro.flowtask.domain.usecase.GetTagFilterUseCase
import com.joseleandro.flowtask.domain.usecase.TagsAllUseCase
import com.joseleandro.flowtask.domain.usecase.TasksGroupByStatusUseCase
import com.joseleandro.flowtask.ui.event.TasksEvent
import com.joseleandro.flowtask.ui.state.TasksUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksViewModel(
    private val tagsAllUseCase: TagsAllUseCase,
    private val tasksGroupByStatusUseCase: TasksGroupByStatusUseCase,
    private val completedTaskUseCase: CompletedTaskUseCase,
    private val filterTasksUseCase: FilterTasksUseCase,
    private val getTagFilterUseCase: GetTagFilterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    init {

        viewModelScope.launch {

            combine(
                tagsAllUseCase(),
                tasksGroupByStatusUseCase(),
                getTagFilterUseCase()
            ) { tags, tasks, filter ->

                Triple(tags, tasks, filter)

            }.collect { (tags, tasks, filter) ->

                _uiState.update { state ->
                    state.copy(
                        tags = tags,
                        tasks = tasks,
                        tagFilterSelected = filter,
                        isLoading = false
                    )
                }

            }

        }
    }

    fun onEvent(event: TasksEvent) {

        when (event) {

            is TasksEvent.OnSelectedTagFilter -> selectedTagFilter(event.tag)

            is TasksEvent.OnCompletedTask -> completedTask(
                taskId = event.taskId,
                isDone = event.isDone
            )
        }
    }


    private fun completedTask(taskId: Long, isDone: Boolean) {

        viewModelScope.launch {
            completedTaskUseCase(taskId = taskId, isDone = isDone)
        }

    }


    private fun selectedTagFilter(tag: Tag?) {

        viewModelScope.launch {
            filterTasksUseCase.invoke(tagId = tag?.id)
        }

    }

    private fun changeLoading(isLoading: Boolean) {
        _uiState.update { state ->
            state.copy(
                isLoading = isLoading
            )
        }
    }
}