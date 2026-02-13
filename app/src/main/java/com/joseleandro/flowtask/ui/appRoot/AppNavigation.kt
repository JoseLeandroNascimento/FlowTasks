package com.joseleandro.flowtask.ui.appRoot

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.joseleandro.flowtask.core.navigation.Screen
import com.joseleandro.flowtask.ui.MainScreen
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun AppNavigation() {

    val navViewModel: NavigationViewModel = koinViewModel()
    val backStack = navViewModel.mainBackStack

    NavDisplay(
        backStack = backStack,
        onBack = { navViewModel.pop() },
        entryProvider = entryProvider {
            entry<Screen.Main> {
                MainScreen()
            }
        }
    )
}