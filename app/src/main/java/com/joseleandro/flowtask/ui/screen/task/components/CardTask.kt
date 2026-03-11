package com.joseleandro.flowtask.ui.screen.task.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.domain.model.TaskIcon
import com.joseleandro.flowtask.ui.screen.task.components.solid.solidIcons
import com.joseleandro.flowtask.ui.theme.ColorPickerPallet
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme

@Composable
fun CardTask(
    modifier: Modifier = Modifier,
    icon: TaskIcon,
    color: Color,
    title: String,
    subtasks: Int = 0,
    completed: Boolean,
    category: String? = null,
    onCompleted: () -> Unit
) {

    val hasMeta = subtasks > 0 || category != null

    val alpha by animateFloatAsState(
        targetValue = if (completed) 0.35f else 1f,
        label = "taskAlpha"
    )

    val scale by animateFloatAsState(
        targetValue = if (completed) 0.98f else 1f,
        label = "taskScale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        border = BorderStroke(width = 1.dp, color = Color(0xFF1F2A3C)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151F2C),
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
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
                            this.alpha = alpha
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
                        onSelected = onCompleted
                    )
                }
            }

        }

    }
}

@Preview
@Composable
private fun CardTaskDarkPreview() {

    var check by remember { mutableStateOf(false) }

    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        CardTask(
            icon = TaskIcon.Drawable(resId = solidIcons.first().resId),
            onCompleted = { check = !check },
            completed = check,
            color = ColorPickerPallet.colorDefault,
            title = "Criar um novo projeto",
            subtasks = 4,
            category = "Pessoal"
        )
    }
}