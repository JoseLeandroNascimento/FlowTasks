package com.joseleandro.flowtask.ui.appRoot

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.joseleandro.flowtask.core.navigation.Screen
import com.joseleandro.flowtask.core.navigation.TabScreen

class NavigationViewModel : ViewModel() {

    val mainBackStack = mutableStateListOf<Screen>(Screen.Main)

    val tabBackStack = mutableStateListOf<TabScreen>(TabScreen.Tasks)

    val currentTab: TabScreen
        get() = tabBackStack.last()


    val currentMainScreen: Screen
        get() = mainBackStack.last()


    fun pop() {
        mainBackStack.remove(currentMainScreen)
    }

    fun navigate(screen: Screen) {
        mainBackStack.add(screen)
    }

    fun selectTab(tab: TabScreen) {
        tabBackStack.clear()
        tabBackStack.add(tab)
    }
}