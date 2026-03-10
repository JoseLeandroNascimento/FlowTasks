package com.joseleandro.flowtask.domain.model

import androidx.annotation.DrawableRes

sealed class TaskIcon {

    data class Emoji(
        val value: String
    ) : TaskIcon()

    data class Drawable(
        @get:DrawableRes
        val resId: Int
    ) : TaskIcon()

}