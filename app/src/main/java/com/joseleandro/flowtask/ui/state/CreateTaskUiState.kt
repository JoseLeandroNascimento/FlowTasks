package com.joseleandro.flowtask.ui.state

import androidx.compose.ui.graphics.Color
import com.joseleandro.flowtask.ui.screen.Tag

data class CreateTaskUiState(
    val nameTask: String? = null,
    val colorTask: Color? = null,
    val tagTask: Tag? = null,
)