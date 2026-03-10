package com.joseleandro.flowtask.ui.screen.task.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme

private fun buildMonthlySummary(days: Set<Int>): String {
    val sorted = days.sorted()

    return when (sorted.size) {
        1 -> "Repete todo dia ${sorted.first()}"
        else -> "Repete nos dias ${sorted.joinToString(", ")}"
    }
}


@Composable
fun MonthlyDaySelector(
    selectedDays: Set<Int>,
    onChange: (Set<Int>) -> Unit
) {

    val days = (1..31).toList()

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Selecionar dias do mês",
            style = MaterialTheme.typography.titleMedium
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(days) { day ->

                val isSelected = selectedDays.contains(day)

                val backgroundColor by animateColorAsState(
                    if (isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surfaceVariant,
                    label = ""
                )

                val textColor by animateColorAsState(
                    if (isSelected)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    label = ""
                )

                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .background(backgroundColor)
                        .clickable {

                            val updated =
                                if (isSelected)
                                    selectedDays - day
                                else
                                    selectedDays + day

                            onChange(updated)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day.toString(),
                        color = textColor,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

    }
}

@Preview
@Composable
private fun MonthlyDaySelectorDarkPreview() {

    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        MonthlyDaySelector(
            selectedDays = setOf(),
            onChange = {}
        )
    }

}