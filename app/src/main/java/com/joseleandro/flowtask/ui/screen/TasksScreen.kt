package com.joseleandro.flowtask.ui.screen

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.ui.components.LabelFilter
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme

@Composable
fun TasksScreen(modifier: Modifier = Modifier) {

    val tasksListState = rememberLazyListState()
    val isFilterVisible = !tasksListState.isScrollInProgress

    Box(
        modifier = modifier.fillMaxSize()
    ) {

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


            item {
                LabelSection(
                    modifier = Modifier.padding(
                        bottom = 8.dp
                    ),
                    label = stringResource(R.string.label_pendente),
                    icon = painterResource(id = R.drawable.time)
                )
            }

            item {
                CardTask(
                    icon = painterResource(
                        id = R.drawable.work_icon_task
                    ),
                    color = Color(0xFF60A5FA),
                    title = "Trabalho",
                    category = "Urgente",
                    subtasks = 10
                )
            }

            item {
                CardTask(
                    icon = painterResource(
                        id = R.drawable.cart_rounded_icon_task
                    ),
                    color = Color(0xFFFB923C),
                    title = "Compras",
                    category = "Supermercado",
                    subtasks = 10
                )
            }

            item {
                CardTask(
                    icon = painterResource(
                        id = R.drawable.person_icon_task
                    ),
                    color = Color(0xFF34D399),
                    title = "Pessoal",
                )
            }

            item {
                LabelSection(
                    modifier = Modifier.padding(
                        bottom = 8.dp,
                        top = 16.dp
                    ),
                    label = stringResource(R.string.label_concluidas),
                    icon = painterResource(id = R.drawable.check_outline)
                )
            }

            item {
                CardTask(
                    icon = painterResource(
                        id = R.drawable.idea_icon_task
                    ),
                    color = Color(0xFFFBBF24),
                    title = "Idéias",
                    category = "Pessoal",
                    subtasks = 10,
                    completed = true
                )
            }

            item {
                CardTask(
                    icon = painterResource(
                        id = R.drawable.weights_icon_task
                    ),
                    color = Color(0xFFFB7185),
                    title = "Academia",
                    category = "Supermercado",
                    subtasks = 10,
                    completed = true
                )
            }

            item {
                CardTask(
                    icon = painterResource(
                        id = R.drawable.ri_plane_icon_task
                    ),
                    color = Color(0xFF22D3EE),
                    title = "Viagem",
                    completed = true
                )
            }

            item {

                Spacer(
                    modifier = Modifier.height(85.dp)
                )
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
            TasksScreenFilter()
        }

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
    icon: Painter,
    color: Color,
    title: String,
    subtasks: Int = 0,
    completed: Boolean = false,
    category: String? = null
) {

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
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = icon,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color)
                    )
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
                                    text = category,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF9DABB9)
                                    )
                                )
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
private fun TasksScreenFilter(modifier: Modifier = Modifier) {

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
                    label = "Tudo", isSelected = true, onClick = {})
            }

            item {
                LabelFilter(
                    label = "Trabalho", isSelected = false, onClick = {})
            }

            item {
                LabelFilter(
                    label = "Escola", isSelected = false, onClick = {})
            }

            item {
                LabelFilter(
                    label = "Supermercado", isSelected = false, onClick = {})
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
        TasksScreen()
    }
}