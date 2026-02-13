package com.joseleandro.flowtask.ui.components

import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme

@Composable
fun LabelFilter(
    label: String,
    onClick: () -> Unit,
    isSelected: Boolean
) {

    FilterChip(
        selected = isSelected,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Color(0xFF122334),
            selectedLabelColor = Color.White,
            labelColor = Color(0xFF9CA3AF),
        ),
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
            )
        },
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = true
        ),
        shape = MaterialTheme.shapes.large,
        onClick = onClick
    )
}

@Preview
@Composable
private fun LabelFilterSelectedPreview() {
    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        LabelFilter(label = "Todos", isSelected = true, onClick = {})
    }
}

@Preview
@Composable
private fun LabelFilterUnselectPreview() {
    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        LabelFilter(label = "Todos", isSelected = false, onClick = {})
    }
}