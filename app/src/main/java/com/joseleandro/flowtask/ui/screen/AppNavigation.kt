package com.joseleandro.flowtask.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.joseleandro.flowtask.core.navigation.Screen
import com.joseleandro.flowtask.ui.viewmodel.NavigationViewModel
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

            entry<Screen.CreateTask> {
                CreateTaskScreen()
            }

            entry<Screen.Tags> {
                TagsScreen()
            }
        }
    )
}