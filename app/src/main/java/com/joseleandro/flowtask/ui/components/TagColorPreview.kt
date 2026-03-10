package com.joseleandro.flowtask.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TagColorPreview(
    modifier: Modifier = Modifier, color: Color?, iconRes: Int, size: Dp = 96.dp
) {
    val backgroundColor by animateColorAsState(
        targetValue = color?.copy(alpha = 0.15f) ?: MaterialTheme.colorScheme.surfaceVariant,
        label = "preview-bg"
    )

    val borderColor by animateColorAsState(
        targetValue = color ?: MaterialTheme.colorScheme.outlineVariant, label = "preview-border"
    )

    Box(
        modifier = modifier
            .size(size)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(backgroundColor)
            .border(
                width = 1.5.dp, color = borderColor, shape = MaterialTheme.shapes.extraLarge
            ), contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = color ?: MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(size * 0.45f)
        )
    }
}