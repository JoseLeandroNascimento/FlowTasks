package com.joseleandro.flowtask.ui.screen.task.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.domain.model.TaskIcon

@Composable
fun TaskIconSelect(
    modifier: Modifier = Modifier,
    color: Color,
    icon: TaskIcon,
    onClick: () -> Unit
) {

    val colorSelectedAnimation by animateColorAsState(
        targetValue = color,
        animationSpec = tween(
            durationMillis = 280,
            easing = FastOutSlowInEasing
        ),
        label = "color"
    )

    val backgroundColor by animateColorAsState(
        targetValue = colorSelectedAnimation.copy(alpha = 0.2f),
        animationSpec = tween(
            durationMillis = 350,
            delayMillis = 40,
            easing = LinearOutSlowInEasing
        ),
        label = "backgroundColor"
    )

    Box(
        modifier = modifier
            .size(80.dp)
            .clip(shape = MaterialTheme.shapes.extraLarge)
            .background(
                color = backgroundColor,
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        when (icon) {
            is TaskIcon.Drawable -> {
                Image(
                    modifier = Modifier.size(34.dp),
                    painter = painterResource(
                        id = icon.resId
                    ),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = colorSelectedAnimation)
                )
            }

            is TaskIcon.Emoji -> {

                Text(
                    text = icon.value,
                    fontSize = 32.sp
                )

            }
        }
    }

}