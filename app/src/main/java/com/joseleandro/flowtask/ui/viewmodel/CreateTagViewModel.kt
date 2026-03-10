package com.joseleandro.flowtask.ui.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.domain.usecase.SaveTagUseCase
import com.joseleandro.flowtask.ui.event.CreateTagEvent
import com.joseleandro.flowtask.ui.form.TagForm
import com.joseleandro.flowtask.ui.state.CreateTagUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateTagViewModel(
    private val saveTagUseCase: SaveTagUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTagUiState())
    val uiState: StateFlow<CreateTagUiState> = _uiState.asStateFlow()

    fun onEvent(event: CreateTagEvent) {

        when (event) {

            is CreateTagEvent.OnSave -> {
                save()
            }

            is CreateTagEvent.OnChangeName -> {
                updateName(event.name)
            }

            is CreateTagEvent.ChangeVisibilityColorPickerBottomSheet -> {
                changeVisibilityColorPickerBottomSheet(event.visibility)
            }

            is CreateTagEvent.OnChangeColor -> {
                updateColor(event.color)
            }

            is CreateTagEvent.OnReset -> {
                reset()
            }

            is CreateTagEvent.OnDismiss -> {
                onDismiss()
            }

            is CreateTagEvent.OnLoadTag -> {
                loadTag(event.tag)
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

    private fun updateId(id: Int) {
        _uiState.update { uiState ->
            uiState.copy(
                form = uiState.form.copy(
                    id = uiState.form.id.updateValue(id)
                )
            )
        }
    }

    private fun changeVisibilityColorPickerBottomSheet(visibility: Boolean?) {
        _uiState.update { uiState ->
            uiState.copy(
                showColorPickerBottomSheet = visibility ?: uiState.showColorPickerBottomSheet.not()
            )
        }
    }

    private fun onDismiss() {
        _uiState.update { uiState ->
            uiState.copy(
                isDismissRequest = true
            )
        }
    }

    private fun reset() {
        _uiState.update { uiState ->
            uiState.copy(
                form = TagForm(),
                isDismissRequest = false
            )
        }
    }

    private fun loadTag(tag: Tag) {
        updateName(tag.name)
        updateColor(tag.color)
        updateId(tag.id)
    }


    private fun save() {

        viewModelScope.launch {

            _uiState.update { uiState ->
                uiState.copy(
                    form = uiState.form.validate()
                )
            }

            with(_uiState.value) {

                if (form.isValid) {

                    val nameTag = form.name.value
                    val colorTag = form.color.value
                    val id = form.id.value ?: 0

                    saveTagUseCase(
                        id = id,
                        name = nameTag,
                        color = colorTag
                    )

                    onDismiss()
                }
            }
        }
    }
}