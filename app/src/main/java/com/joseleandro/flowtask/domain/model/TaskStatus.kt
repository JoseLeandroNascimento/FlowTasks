package com.joseleandro.flowtask.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.joseleandro.flowtask.R

enum class TaskStatus(
    @get:StringRes val label: Int,
    @get:DrawableRes val icon: Int,
) {
    TODO(R.string.label_pendente, R.drawable.time),
    DONE(R.string.label_concluidas, R.drawable.check_outline)
}