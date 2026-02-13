package com.joseleandro.flowtask.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.core.navigation.TabScreen
import com.joseleandro.flowtask.ui.appRoot.NavigationViewModel
import com.joseleandro.flowtask.ui.components.TasksTopBar
import com.joseleandro.flowtask.ui.components.layout.GlowingFab
import com.joseleandro.flowtask.ui.components.layout.MainDrawerContent
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val navigationViewModel: NavigationViewModel = koinViewModel()
    val currentTab = navigationViewModel.currentTab
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerState = drawerState, drawerContent = {
            MainDrawerContent()
        }
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TasksTopBar(
                    changeVisibilityDrawer = {
                        scope.launch {
                            if (drawerState.isOpen) {
                                drawerState.close()
                            } else {
                                drawerState.open()
                            }
                        }
                    })
            },
            bottomBar = {
                MainScreenBottomAppBar(
                    currentTabScreen = currentTab, selectTab = navigationViewModel::selectTab
                )
            },
            floatingActionButton = {
                GlowingFab { }
            },
            floatingActionButtonPosition = FabPosition.End,

            ) { innerPadding ->

            Surface(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                NavDisplay(
                    backStack = navigationViewModel.tabBackStack,
                    onBack = {},
                    entryProvider = entryProvider {

                        entry<TabScreen.Tasks> {
                            TasksScreen()
                        }

                        entry<TabScreen.Agenda> {
                            AgendaScreen()
                        }

                        entry<TabScreen.Statistic> {
                            StatisticScreen()
                        }

                    })
            }
        }
    }

}


@Composable
fun MainTopBarDrawer(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Surface(
                modifier = Modifier.size(30.dp),
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(R.string.logo_app)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text(
                    text = "FlowTasks",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp
                )
                Text(
                    text = "Domine o seu dia",
                    fontSize = 12.sp,
                    color = Color(0xFF8F96A2),
                    fontWeight = FontWeight.Medium,
                    lineHeight = 12.sp
                )
            }
        }

        HorizontalDivider(
            color = Color(0xFF1F252D)
        )
    }

}

@Composable
private fun MainScreenBottomAppBar(
    currentTabScreen: TabScreen, selectTab: (TabScreen) -> Unit
) {

    val navigationBarItem = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = Color(0xFF6B7280),
        unselectedTextColor = Color(0xFF6B7280),
        indicatorColor = Color.Transparent
    )

    BottomAppBar(
        containerColor = Color(0xFF151F2C), actions = {

            TabScreen.tabs.map { tab ->
                NavigationBarItem(colors = navigationBarItem, label = {
                    Text(
                        text = stringResource(id = tab.label),
                        style = MaterialTheme.typography.labelMedium
                    )
                }, icon = {
                    Icon(
                        painter = painterResource(
                            id = tab.icon,
                        ), contentDescription = null
                    )
                }, selected = currentTabScreen == tab, onClick = {
                    selectTab(tab)
                })
            }
        })
}

