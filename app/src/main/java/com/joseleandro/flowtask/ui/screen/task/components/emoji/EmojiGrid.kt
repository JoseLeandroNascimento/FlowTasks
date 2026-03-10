package com.joseleandro.flowtask.ui.screen.task.components.emoji

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmojiGrid(
    emojis: List<String>,
    onSelect: (String) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(8),
        contentPadding = PaddingValues(16.dp)
    ) {

        items(emojis, key = {it}) { emoji ->

            Text(
                text = emoji,
                fontSize = 26.sp,
                modifier = Modifier
                    .clickable { onSelect(emoji) }
                    .padding(6.dp)
            )
        }
    }
}