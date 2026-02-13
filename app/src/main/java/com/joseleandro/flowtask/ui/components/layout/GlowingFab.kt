package com.joseleandro.flowtask.ui.components.layout

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.R

@Composable
fun GlowingFab(
    onClick: () -> Unit
) {

    val infiniteTransition = rememberInfiniteTransition(label = "glow")

    val outerAlpha by infiniteTransition.animateFloat(
        initialValue = 0.08f, targetValue = 0.16f, animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse
        ), label = "outerAlpha"
    )

    val outerRadiusFactor by infiniteTransition.animateFloat(
        initialValue = 0.68f, targetValue = 0.74f, animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse
        ), label = "outerRadius"
    )

    val innerAlpha by infiniteTransition.animateFloat(
        initialValue = 0.22f, targetValue = 0.34f, animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse
        ), label = "innerAlpha"
    )

    val innerRadiusFactor by infiniteTransition.animateFloat(
        initialValue = 0.48f, targetValue = 0.56f, animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse
        ), label = "innerRadius"
    )

    val color = MaterialTheme.colorScheme.primary

    FloatingActionButton(
        modifier = Modifier
            .padding(end = 16.dp, bottom = 24.dp)
            .drawBehind {

                drawCircle(
                    color = color.copy(alpha = outerAlpha),
                    radius = size.minDimension * outerRadiusFactor,
                    center = center
                )

                drawCircle(
                    color = color.copy(alpha = innerAlpha),
                    radius = size.minDimension * innerRadiusFactor,
                    center = center
                )
            },
        shape = CircleShape,
        containerColor = color,
        contentColor = Color.White,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.icon_add)
        )
    }

}