package com.joseleandro.flowtask.ui.screen.task

import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.ui.components.LabelFilter
import com.joseleandro.flowtask.ui.event.TasksEvent
import com.joseleandro.flowtask.ui.screen.task.components.CardTask
import com.joseleandro.flowtask.ui.state.TasksUiState
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme
import com.joseleandro.flowtask.ui.viewmodel.TasksViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun TasksScreen(modifier: Modifier = Modifier) {

    val viewModel: TasksViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TasksScreen(
        modifier = modifier,
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    uiState: TasksUiState,
    onEvent: (TasksEvent) -> Unit
) {

    val tasksListState = rememberLazyListState()

    Box(
        modifier = modifier.fillMaxSize(),
    ) {

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                if (uiState.tags.isNotEmpty()) {
                    TasksScreenFilter(
                        tags = uiState.tags,
                        tagSelected = uiState.tagFilterSelected,
                        onFilter = { tag ->
                            onEvent(TasksEvent.OnSelectedTagFilter(tag))
                        }
                    )
                }

                if (uiState.tasks.isEmpty()) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        EmptyTasks()
                    }

                } else {

                    LazyColumn(
                        state = tasksListState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        uiState.tasks.forEach { tasksGroup ->

                            item {
                                LabelSection(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    label = stringResource(tasksGroup.status.label),
                                    icon = painterResource(tasksGroup.status.icon)
                                )
                            }

                            items(
                                items = tasksGroup.tasks,
                                key = { it.id }
                            ) { task ->

                                CardTask(
                                    modifier = Modifier.animateItem(
                                        placementSpec = spring(
                                            stiffness = 120f,
                                            dampingRatio = 0.9f
                                        )
                                    ),
                                    icon = task.selectIcon!!,
                                    color = task.colorSelected,
                                    title = task.title,
                                    category = task.tagSelected?.name,
                                    subtasks = task.subtasks.size,
                                    completed = task.isDone,
                                    onCompleted = {
                                        onEvent(
                                            TasksEvent.OnCompletedTask(
                                                taskId = task.id,
                                                isDone = !task.isDone
                                            )
                                        )
                                    }
                                )
                            }
                        }


                        item {
                            Spacer(modifier = Modifier.height(120.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyTasks(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            modifier = Modifier.size(180.dp),
            painter = painterResource(R.drawable.task_list_empty),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.nenhuma_tarefa_ainda),
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF9DABB9)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.crie_sua_primeira_tarefa_para_comecar),
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF6B7280)
        )
    }
}


@Composable
private fun TasksScreenFilter(
    modifier: Modifier = Modifier,
    tags: List<Tag>,
    tagSelected: Int? = null,
    onFilter: (Tag?) -> Unit
) {

    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        LazyRow(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
        ) {


            item {
                LabelFilter(
                    label = stringResource(R.string.filter_tudo),
                    isSelected = tagSelected == null,
                    onClick = {
                        onFilter(null)
                    }
                )
            }

            items(
                items = tags,
                key = { it.id }
            ) { tag ->
                LabelFilter(
                    label = tag.name,
                    isSelected = tagSelected == tag.id,
                    onClick = {
                        onFilter(tag)
                    }
                )
            }

        }
    }
}

@Composable
fun LabelSection(
    modifier: Modifier = Modifier,
    label: String,
    icon: Painter
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = stringResource(R.string.label_section_pendente)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 14.sp,
                lineHeight = 14.sp
            ),
            color = Color(0xFF999DAD)
        )
    }

}


@Preview
@Composable
private fun TasksScreenPreview() {
    FlowTaskTheme(
        dynamicColor = false, darkTheme = true
    ) {
        TasksScreen(
            uiState = TasksUiState(),
            onEvent = {}
        )
    }
}