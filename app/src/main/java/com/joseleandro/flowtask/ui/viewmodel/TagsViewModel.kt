package com.joseleandro.flowtask.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.domain.usecase.DeleteTagByIdUseCase
import com.joseleandro.flowtask.domain.usecase.TagsAllUseCase
import com.joseleandro.flowtask.ui.event.TagsEvent
import com.joseleandro.flowtask.ui.state.TagsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TagsViewModel(
    private val tagsAllUseCase: TagsAllUseCase,
    private val deleteTagByIdUseCase: DeleteTagByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TagsUiState())
    val uiState: StateFlow<TagsUiState> = _uiState.asStateFlow()

    init {
        loadTags()
    }

    fun onEvent(event: TagsEvent) {

        when (event) {

            is TagsEvent.ChangeVisibilityTagsBottomSheet -> {
                changeVisibilityTagsBottomSheet(event.visibility)
            }

            is TagsEvent.OnDeleteTag -> {
                deleteTag(event.id)
            }

            is TagsEvent.OnSelectedTag -> {
                selectedTag(event.tag)
            }

            is TagsEvent.OnEditTag -> {
                selectedTag(event.tag)
                changeVisibilityTagsBottomSheet(true)
            }

            is TagsEvent.OnConfirmDeleteTag -> {
                confirmDelete(event.tag)
            }
        }
    }

    private fun loadTags() {

        viewModelScope.launch {
            changeLoading(true)
            tagsAllUseCase().collect { tags ->
                _uiState.update { uiState ->
                    uiState.copy(
                        tags = tags
                    )
                }
                changeLoading(false)
            }
        }
    }

    private fun confirmDelete(tag: Tag?){
        _uiState.update { state ->
            state.copy(
                tagDelete = tag
            )
        }
    }

    private fun deleteTag(id: Int) {

        viewModelScope.launch {
            confirmDelete(null)
            deleteTagByIdUseCase(id)
        }
    }

    private fun changeLoading(show: Boolean) {
        _uiState.update { state ->
            state.copy(
                isLoading = show
            )
        }
    }

    private fun selectedTag(tag: Tag?) {
        _uiState.update { state ->
            state.copy(
                selectedTag = tag
            )
        }
    }

    private fun changeVisibilityTagsBottomSheet(visibility: Boolean?) {

        with(_uiState) {

            update { uiState ->

                uiState.copy(
                    showTagCreateBottomSheet = visibility ?: uiState.showTagCreateBottomSheet.not()
                )
            }

        }
    }

}