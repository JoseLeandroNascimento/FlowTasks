package com.joseleandro.flowtask.domain.model

import androidx.annotation.StringRes
import com.joseleandro.flowtask.R

enum class IconCategory(
    @get:StringRes val title: Int
) {
    ICONS(R.string.icones),
    ANIMATED(R.string.animadas),
    EMOJI(R.string.emoji)
}