package com.joseleandro.flowtask.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme
import kotlinx.coroutines.launch

@Composable
fun CircularTimePicker(
    range: IntRange,
    value: Int,
    onValueChange: (Int) -> Unit
) {

    val itemCount = 1000
    val middle = itemCount / 2

    val startIndex = remember(value) {
        middle - (middle % range.count()) + (value - range.first)
    }

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = startIndex
    )

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(listState.firstVisibleItemIndex) {
        val index = listState.firstVisibleItemIndex
        val newValue = range.first + (index % range.count())
        onValueChange(newValue)
    }

    LaunchedEffect(value) {
        val newIndex = middle - (middle % range.count()) + (value - range.first)
        listState.scrollToItem(newIndex)
    }

    Box(
        modifier = Modifier
            .height(150.dp)
            .width(70.dp),
        contentAlignment = Alignment.Center
    ) {

        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
            contentPadding = PaddingValues(vertical = 50.dp)
        ) {

            items(itemCount) { index ->

                val valueItem = range.first + (index % range.count())
                val isSelected = index == listState.firstVisibleItemIndex

                Text(
                    text = valueItem.toString().padStart(2, '0'),
                    style = if (isSelected)
                        MaterialTheme.typography.headlineMedium
                    else
                        MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            coroutineScope.launch {
                                listState.animateScrollToItem(index)
                            }
                        },
                    color = if (isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .height(40.dp)
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.dp)
                )
        )
    }
}

@Preview
@Composable
private fun CircularTimePickerDarkPreview() {
    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        CircularTimePicker(
            range = 1..24,
            value = 10,
            onValueChange = {}
        )
    }
}