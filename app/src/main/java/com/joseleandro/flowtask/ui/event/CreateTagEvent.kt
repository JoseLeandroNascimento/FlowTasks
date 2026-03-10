package com.joseleandro.flowtask.ui.event

import androidx.compose.ui.graphics.Color
import com.joseleandro.flowtask.domain.model.Tag

interface CreateTagEvent {

    data object OnSave : CreateTagEvent

    data class OnChangeName(val name: String) : CreateTagEvent

    data class OnChangeColor(val color: Color) : CreateTagEvent

    data class ChangeVisibilityColorPickerBottomSheet(val visibility: Boolean? = null) :
        CreateTagEvent

    data object OnReset : CreateTagEvent

    data object OnDismiss : CreateTagEvent

    data class OnLoadTag(val tag: Tag) : CreateTagEvent

}