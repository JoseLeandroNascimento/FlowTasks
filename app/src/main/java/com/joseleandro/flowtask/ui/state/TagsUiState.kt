package com.joseleandro.flowtask.ui.state

import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.ui.form.TagForm

data class TagsUiState(
    val tags: List<Tag> = emptyList(),
    val form: TagForm = TagForm(),
    val showTagCreateBottomSheet: Boolean = false,
    val showColorPickerBottomSheet: Boolean = false
)
