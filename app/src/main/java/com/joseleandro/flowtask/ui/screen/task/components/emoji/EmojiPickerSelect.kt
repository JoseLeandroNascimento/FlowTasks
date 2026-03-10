package com.joseleandro.flowtask.ui.screen.task.components.emoji

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmojiPickerSelect(
    onSelect: (String) -> Unit
) {

    var category by remember {
        mutableStateOf(EmojiCategory.SMILEYS)
    }


    Column(
        modifier = Modifier
            .fillMaxHeight(0.85f)
    ) {

        Text(
            text = "Selecionar emoji",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        EmojiTabs(
            selected = category,
            onSelected = { category = it }
        )

        Spacer(Modifier.height(12.dp))

        EmojiGrid(
            emojis = EmojiData.emojis[category] ?: emptyList(),
            onSelect = {
                onSelect(it)
            }
        )
    }

}