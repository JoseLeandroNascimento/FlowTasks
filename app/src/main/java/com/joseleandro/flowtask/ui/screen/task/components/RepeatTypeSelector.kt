package com.joseleandro.flowtask.ui.screen.task.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.domain.model.RepeatType
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme

@Composable
fun RepeatTypeSelector(
    selected: RepeatType,
    onSelect: (RepeatType) -> Unit
) {

    val tabs = RepeatType.entries.filter { it != RepeatType.NONE }
    val selectedIndex = tabs.indexOf(selected)

    PrimaryTabRow(
        modifier = Modifier.clip(shape = CircleShape),
        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        selectedTabIndex = selectedIndex,
        divider = {}
    ) {

        tabs.forEachIndexed { index, type ->

            val isSelected = selectedIndex == index

            Tab(
                selected = isSelected,
                onClick = { onSelect(type) },
                selectedContentColor = Color.White,
                unselectedContentColor = MaterialTheme.colorScheme.primary,
                text = {
                    Text(
                        text = type.label,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun RepeatTypeSelectorDarkPreview() {

    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        RepeatTypeSelector(
            selected = RepeatType.DAILY,
            onSelect = {}
        )
    }
}