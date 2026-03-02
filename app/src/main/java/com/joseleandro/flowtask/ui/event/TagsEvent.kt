package com.joseleandro.flowtask.ui.event

sealed interface TagsEvent {

    data class ChangeVisibilityTagsBottomSheet(val visibility: Boolean? = null) : TagsEvent



    data class OnDeleteTag(val id: Int) : TagsEvent

}