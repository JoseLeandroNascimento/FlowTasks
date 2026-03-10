package com.joseleandro.flowtask.ui.event

import com.joseleandro.flowtask.domain.model.Tag

sealed interface TagsEvent {

    data class ChangeVisibilityTagsBottomSheet(val visibility: Boolean? = null) : TagsEvent

    data class OnDeleteTag(val id: Int) : TagsEvent

    data class OnSelectedTag(val tag: Tag?) : TagsEvent

    data class OnEditTag(val tag: Tag) : TagsEvent

    data class OnConfirmDeleteTag(val tag: Tag?) : TagsEvent

}