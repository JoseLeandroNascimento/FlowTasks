package com.joseleandro.flowtask.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavKey
import com.joseleandro.flowtask.R
import kotlinx.serialization.Serializable

sealed interface TabScreen : NavKey {

    @get:StringRes
    val label: Int

    @get:DrawableRes
    val icon: Int

    @Serializable
    data object Tasks : TabScreen {
        override val label: Int
            get() = R.string.tab_tarefas
        override val icon: Int
            get() = R.drawable.check_fill
    }

    @Serializable
    data object Agenda : TabScreen {
        override val label: Int
            get() = R.string.tab_agenda
        override val icon: Int
            get() = R.drawable.calendar
    }

    @Serializable
    data object Statistic : TabScreen {
        override val label: Int
            get() = R.string.tab_estatisticas
        override val icon: Int
            get() = R.drawable.chart_fill
    }

    companion object {
        val tabs = listOf(Tasks, Agenda, Statistic)
    }

}