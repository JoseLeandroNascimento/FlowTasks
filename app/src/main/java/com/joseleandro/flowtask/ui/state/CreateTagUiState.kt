package com.joseleandro.flowtask.ui.state

import com.joseleandro.flowtask.ui.form.TagForm

data class CreateTagUiState(
    val form: TagForm = TagForm(),
    val showColorPickerBottomSheet: Boolean = false,
    val isDismissRequest: Boolean = false
)
