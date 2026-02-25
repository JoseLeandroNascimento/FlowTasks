package com.joseleandro.flowtask.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Screen : NavKey {

    @Serializable
    data object Main : Screen

    @Serializable
    data object CreateTask : Screen

    @Serializable
    data object Tags: Screen
}