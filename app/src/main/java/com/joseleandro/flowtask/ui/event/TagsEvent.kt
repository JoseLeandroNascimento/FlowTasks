package com.joseleandro.flowtask.ui.event

import androidx.compose.ui.graphics.Color

sealed interface TagsEvent {

    data object OnSave : TagsEvent

    data class ChangeVisibilityTagsBottomSheet(val visibility: Boolean? = null) : TagsEvent

    data class ChangeVisibilityColorPickerBottomSheet(val visibility: Boolean? = null) : TagsEvent

    data class OnChangeName(val name: String) : TagsEvent

    data class OnChangeColor(val color: Color) : TagsEvent

}