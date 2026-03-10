package com.joseleandro.flowtask.ui.state

import com.joseleandro.flowtask.domain.model.Tag

data class TagsUiState(
    val tags: List<Tag> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTag: Tag? = null,
    val tagDelete: Tag? = null,
    val showTagCreateBottomSheet: Boolean = false,
)
