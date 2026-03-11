package com.joseleandro.flowtask.ui.screen.task.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme

@Composable
fun Check(
    modifier: Modifier = Modifier,
    select: Boolean = false,
    onSelected: () -> Unit
) {

    val colorBorderAnim by animateColorAsState(
        targetValue = if (select) Color(0xFF06AC2A) else Color(0xFFB8B7B9),
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = modifier
            .size(30.dp)
            .border(
                width = 2.dp,
                color = colorBorderAnim,
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

        AnimatedVisibility(
            visible = select,
            enter = scaleIn(
                animationSpec = tween(durationMillis = 300)
            ),
            exit = scaleOut(
                animationSpec = tween(durationMillis = 300)
            ),
            label = "ImageCheckVisibility"
        ) {
            Image(
                modifier = Modifier.fillMaxSize(fraction = .9f),
                painter = painterResource(R.drawable.check_fill),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color(0xFF06AC2A))
            )
        }
    }

}

@Preview
@Composable
private fun CheckDarkPreview() {

    var select by remember { mutableStateOf(false) }

    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        Check(
            select = select,
            onSelected = {
                select = !select
            }
        )
    }

}

