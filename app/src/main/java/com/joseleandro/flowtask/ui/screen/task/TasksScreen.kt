package com.joseleandro.flowtask.ui.screen.task

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.domain.model.TaskIcon
import com.joseleandro.flowtask.ui.components.LabelFilter
import com.joseleandro.flowtask.ui.event.TasksEvent
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
    val isFilterVisible = !tasksListState.isScrollInProgress

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        if (uiState.tasks.isEmpty()) {

            EmptyTasks(
                modifier = Modifier.align(Alignment.Center)
            )

        } else {

            LazyColumn(
                state = tasksListState,
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 74.dp,
                    bottom = 24.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {


                uiState.tasks.forEach { tasksGroup ->

                    item {
                        LabelSection(
                            modifier = Modifier.padding(
                                bottom = 8.dp
                            ),
                            label = stringResource(tasksGroup.status.label),
                            icon = painterResource(tasksGroup.status.icon)
                        )
                    }

                    items(items = tasksGroup.tasks) { task ->
                        CardTask(
                            icon = task.selectIcon!!,
                            color = task.colorSelected,
                            title = task.title,
                            category = task.tagSelected?.name,
                            subtasks = task.subtasks.size
                        )
                    }

                }

                item {

                    Spacer(
                        modifier = Modifier.height(85.dp)
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = isFilterVisible,
            enter = slideInVertically(
                initialOffsetY = { -it / 2 }
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { -it / 2 }
            ) + fadeOut()
        ) {
            TasksScreenFilter(
                tags = uiState.tags,
                tagSelected = uiState.tagFilterSelected,
                onFilter = { tag ->
                    onEvent(TasksEvent.OnSelectedTagFilter(tag))
                }
            )
        }

    }
}

@Composable
fun EmptyTasks(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 120.dp),
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
            text = "Nenhuma tarefa ainda",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF9DABB9)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Crie sua primeira tarefa para começar",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF6B7280)
        )
    }
}

@Composable
fun Check(
    modifier: Modifier = Modifier,
    select: Boolean = false,
    onSelected: () -> Unit
) {

    Box(
        modifier = modifier
            .size(30.dp)
            .border(
                width = 2.dp,
                color = if (select) Color(0xFF06AC2A) else Color(0xFFB8B7B9),
                shape = CircleShape,
            )
            .background(
                color = Color(0xFFFFFFFF),
                shape = CircleShape,
            )
            .clip(shape = CircleShape)
            .clickable {
                onSelected()
            },
        contentAlignment = Alignment.Center
    ) {

        if (select) {
            Image(
                modifier = Modifier.fillMaxSize(fraction = .9f),
                painter = painterResource(R.drawable.check_fill),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color(0xFF06AC2A))
            )
        }
    }

}

@Composable
fun CardTask(
    modifier: Modifier = Modifier,
    icon: TaskIcon,
    color: Color,
    title: String,
    subtasks: Int = 0,
    completed: Boolean = false,
    category: String? = null
) {

    val hasMeta = subtasks > 0 || category != null

    Card(
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(width = 1.dp, color = Color(0xFF1F2A3C)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151F2C),
        ),
        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Box(
                    modifier = Modifier
                        .background(
                            color = color.copy(alpha = .23f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .size(45.dp),
                    contentAlignment = Alignment.Center
                ) {

                    when (icon) {

                        is TaskIcon.Emoji -> {
                            Text(
                                text = icon.value,
                                fontSize = 18.sp
                            )
                        }

                        is TaskIcon.Drawable -> {
                            Image(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(icon.resId),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(color)
                            )
                        }
                    }

                }

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                    Column(
                        modifier = Modifier.graphicsLayer {
                            alpha = if (completed) .3f else 1f
                        },
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 16.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        if (hasMeta) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {

                                if (subtasks > 0) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Image(
                                            modifier = Modifier.size(11.dp),
                                            painter = painterResource(id = R.drawable.tree_structure_icon_task),
                                            contentDescription = null
                                        )
                                        Text(
                                            text = "0/$subtasks",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color(0xFF9DABB9)
                                            )
                                        )
                                    }
                                }

                                category?.let {

                                    Image(
                                        modifier = Modifier.size(8.dp),
                                        painter = painterResource(id = R.drawable.point_icon_task),
                                        contentDescription = null
                                    )

                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF9DABB9)
                                        )
                                    )
                                }
                            }
                        }
                    }

                    Check(
                        select = completed,
                        onSelected = {}
                    )
                }
            }

        }

    }

}

@Composable
private fun TasksScreenFilter(
    modifier: Modifier = Modifier,
    tags: List<Tag>,
    tagSelected: Tag? = null,
    onFilter: (Tag?) -> Unit
) {

    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        LazyRow(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {


            item {
                LabelFilter(
                    label = "Tudo",
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
                    isSelected = tagSelected == tag,
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