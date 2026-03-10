package com.joseleandro.flowtask.ui.screen.tag.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.ui.event.TagsEvent
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme

@Composable
fun TagCard(
    modifier: Modifier = Modifier,
    tag: Tag,
    onEvent: (TagsEvent) -> Unit
) {

    var showMenuOptions by remember { mutableStateOf(false) }

    ListItem(modifier = modifier, headlineContent = {
        Text(
            text = tag.name, style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }, supportingContent = {
        Text(
            text = "12 tarefas", style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
            )
        )
    }, leadingContent = {
        Image(
            modifier = Modifier.size(20.dp), painter = painterResource(
                id = R.drawable.park_solid_tag
            ), contentDescription = null, colorFilter = ColorFilter.tint(tag.color)
        )
    }, trailingContent = {

        Box() {
            IconButton(
                onClick = {
                    showMenuOptions = true
                }) {
                Icon(
                    imageVector = Icons.Default.MoreVert, contentDescription = null
                )
            }

            DropdownMenu(
                expanded = showMenuOptions,
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp,
                onDismissRequest = {
                    showMenuOptions = false
                }) {
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.editar),
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    onClick = {
                        onEvent(
                            TagsEvent.OnEditTag(
                                tag = tag
                            )
                        )
                        showMenuOptions = false
                    }
                )

                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.excluir),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.error
                            )
                        )
                    },
                    onClick = {

                        onEvent(
                            TagsEvent.OnConfirmDeleteTag(
                                tag = tag
                            )
                        )
                        showMenuOptions = false
                    }
                )
            }
        }
    }
    )
}

@Preview
@Composable
private fun TagCardDarkPreview() {
    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        TagCard(
            tag = Tag(
                id = 1,
                name = "Trabalho",
                color = MaterialTheme.colorScheme.primary
            ),
            onEvent = {},
        )
    }
}