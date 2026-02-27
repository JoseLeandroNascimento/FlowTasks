package com.joseleandro.flowtask.ui.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joseleandro.flowtask.domain.usecase.InsertTagUseCase
import com.joseleandro.flowtask.domain.usecase.TagsAllUseCase
import com.joseleandro.flowtask.ui.event.TagsEvent
import com.joseleandro.flowtask.ui.form.TagForm
import com.joseleandro.flowtask.ui.state.TagsUiState
import com.joseleandro.flowtask.ui.theme.ColorPickerPallet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TagsViewModel(
    private val insertTagUseCase: InsertTagUseCase,
    private val tagsAllUseCase: TagsAllUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TagsUiState())
    val uiState: StateFlow<TagsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            tagsAllUseCase().collect { tags ->
                _uiState.update { uiState ->
                    uiState.copy(
                        tags = tags
                    )
                }
            }
        }
    }

    fun onEvent(event: TagsEvent) {

        when (event) {

            is TagsEvent.OnSave -> {
                save()
            }

            is TagsEvent.ChangeVisibilityTagsBottomSheet -> {
                changeVisibilityTagsBottomSheet(event.visibility)
            }

            is TagsEvent.ChangeVisibilityColorPickerBottomSheet -> {
                changeVisibilityColorPickerBottomSheet(event.visibility)
            }

            is TagsEvent.OnChangeName -> {
                updateName(event.name)
            }

            is TagsEvent.OnChangeColor -> {
                updateColor(event.color)
            }
        }
    }

    private fun updateName(name: String) {
        _uiState.update { uiState ->
            uiState.copy(
                form = uiState.form.copy(
                    name = uiState.form.name.updateValue(name)
                )
            )
        }
    }

    private fun updateColor(color: Color) {
        _uiState.update { uiState ->
            uiState.copy(
                form = uiState.form.copy(
                    color = uiState.form.color.updateValue(color)
                )
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

            if (!value.showTagCreateBottomSheet) {
                resetFormTag()
            }
        }


    }

    private fun changeVisibilityColorPickerBottomSheet(visibility: Boolean?) {
        _uiState.update { uiState ->
            uiState.copy(
                showColorPickerBottomSheet = visibility ?: uiState.showColorPickerBottomSheet.not()
            )
        }
    }

    private fun resetFormTag() {
        _uiState.update { uiState ->
            uiState.copy(
                form = TagForm()
            )
        }
    }

    private fun save() {

        viewModelScope.launch {
            with(_uiState.value) {

                if (form.isValid) {

                    val nameTag = form.name.value ?: ""
                    val colorTag = form.color.value ?: ColorPickerPallet.colorDefault

                    insertTagUseCase(
                        name = nameTag,
                        color = colorTag
                    )
                    changeVisibilityTagsBottomSheet(false)

                }
            }
        }
    }
}