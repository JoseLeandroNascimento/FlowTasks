package com.joseleandro.flowtask.ui.screen.task.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme
import java.time.DayOfWeek

private fun DayOfWeek.shortLabel(): String {
    return when (this) {
        DayOfWeek.MONDAY -> "S"
        DayOfWeek.TUESDAY -> "T"
        DayOfWeek.WEDNESDAY -> "Q"
        DayOfWeek.THURSDAY -> "Q"
        DayOfWeek.FRIDAY -> "S"
        DayOfWeek.SATURDAY -> "S"
        DayOfWeek.SUNDAY -> "D"
    }
}

@Composable
fun WeeklyDaySelector(
    selectedDays: Set<DayOfWeek>,
    onChange: (Set<DayOfWeek>) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Repetir nos dias",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            DayOfWeek.entries.forEach { day ->

                val isSelected = selectedDays.contains(day)

                val backgroundColor by animateColorAsState(
                    targetValue =
                        if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceVariant,
                    label = ""
                )

                val contentColor by animateColorAsState(
                    targetValue =
                        if (isSelected)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                    label = ""
                )

                Surface(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(44.dp)
                        .clickable {
                            val updated =
                                if (isSelected)
                                    selectedDays - day
                                else
                                    selectedDays + day

                            onChange(updated)
                        },
                    shape = CircleShape,
                    color = backgroundColor
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day.shortLabel(),
                            color = contentColor,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun WeeklyDaySelectorDarkPreview() {
    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        WeeklyDaySelector(
            selectedDays = setOf(),
            onChange = {}
        )
    }
}