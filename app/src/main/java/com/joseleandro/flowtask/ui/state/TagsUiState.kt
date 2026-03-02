package com.joseleandro.flowtask.ui.state

import com.joseleandro.flowtask.domain.model.Tag

data class TagsUiState(
    val tags: List<Tag> = emptyList(),
    val showTagCreateBottomSheet: Boolean = false,
)
