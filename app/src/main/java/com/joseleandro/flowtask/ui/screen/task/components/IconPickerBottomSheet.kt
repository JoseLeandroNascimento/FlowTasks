package com.joseleandro.flowtask.ui.screen.task.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.domain.model.IconCategory
import com.joseleandro.flowtask.domain.model.TaskIcon
import com.joseleandro.flowtask.ui.screen.task.components.emoji.EmojiPickerSelect
import com.joseleandro.flowtask.ui.screen.task.components.solid.SolidIconsPicker
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconPickerBottomSheet(
    onDismiss: () -> Unit,
    onIconSelected: (TaskIcon) -> Unit
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var selectedTab by remember { mutableStateOf(IconCategory.ICONS) }


    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {

        Column(
            modifier = Modifier.fillMaxHeight(0.7f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            IconCategoryTabs(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            when (selectedTab) {

                IconCategory.ICONS -> {
                    SolidIconsPicker {
                        onIconSelected(TaskIcon.Drawable(it))
                        onDismiss()
                    }
                }

                IconCategory.ANIMATED -> {
//                    AnimatedIconsPicker(
//                        onSelect = {
//                            onIconSelected(TaskIcon.Animated(it))
//                            onDismiss()
//                        }
//                    )
                }

                IconCategory.EMOJI -> {
                    EmojiPickerSelect(
                        onSelect = { emoji ->
                            onIconSelected(TaskIcon.Emoji(emoji))
                            onDismiss()
                        }
                    )
                }


            }
        }
    }
}

@Composable
fun IconCategoryTabs(
    selectedTab: IconCategory,
    onTabSelected: (IconCategory) -> Unit
) {

    PrimaryTabRow(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(shape = CircleShape),
        selectedTabIndex = selectedTab.ordinal,
        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = .1f),
        divider = {},
    ) {

        IconCategory.entries.forEach { category ->

            Tab(
                selected = selectedTab == category,
                onClick = { onTabSelected(category) },
                text = {
                    Text(
                        stringResource(id = category.title),
                        modifier = Modifier.padding(vertical = 12.dp),
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun IconPickerBottomSheetDarkPreview() {

    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        IconPickerBottomSheet(
            onDismiss = {},
            onIconSelected = {}
        )
    }
}
