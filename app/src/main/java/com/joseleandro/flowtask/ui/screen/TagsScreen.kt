package com.joseleandro.flowtask.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.ui.components.GlowingFab
import com.joseleandro.flowtask.ui.components.TagsBottomSheet
import com.joseleandro.flowtask.ui.event.TagsEvent
import com.joseleandro.flowtask.ui.state.TagsUiState
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme
import com.joseleandro.flowtask.ui.viewmodel.NavigationViewModel
import com.joseleandro.flowtask.ui.viewmodel.TagsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TagsScreen() {

    val navigationViewModel: NavigationViewModel = koinViewModel()
    val tagsViewModel: TagsViewModel = koinViewModel()
    val uiState by tagsViewModel.uiState.collectAsStateWithLifecycle()

    TagsScreen(
        onBack = {
            navigationViewModel.pop()
        }, onEvent = tagsViewModel::onEvent, uiState = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsScreen(
    onBack: () -> Unit, uiState: TagsUiState, onEvent: (TagsEvent) -> Unit
) {


    if (uiState.showTagCreateBottomSheet) {
        TagsBottomSheet(
            onDismissRequest = {
                onEvent(TagsEvent.ChangeVisibilityTagsBottomSheet(false))
            },
            colorTagSelected = uiState.form.color.value ?: MaterialTheme.colorScheme.primary,
            onColorSelected = { color ->
                onEvent(TagsEvent.OnChangeColor(color))
            },
            textValue = uiState.form.name.value ?: "",
            onTextValueChange = { valueText ->
                onEvent(TagsEvent.OnChangeName(valueText))
            },
            onSave = {
                onEvent(TagsEvent.OnSave)
            })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.gerenciar_tags),
                        style = MaterialTheme.typography.titleMedium
                    )
                }, navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }, floatingActionButton = {
            GlowingFab {
                onEvent(TagsEvent.ChangeVisibilityTagsBottomSheet())
            }
        }, containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->

        Content(
            modifier = Modifier.padding(innerPadding),
            tags = uiState.tags
        )

    }
}

@Composable
fun TagColorPreview(
    modifier: Modifier = Modifier, color: Color?, iconRes: Int, size: Dp = 96.dp
) {
    val backgroundColor by animateColorAsState(
        targetValue = color?.copy(alpha = 0.15f) ?: MaterialTheme.colorScheme.surfaceVariant,
        label = "preview-bg"
    )

    val borderColor by animateColorAsState(
        targetValue = color ?: MaterialTheme.colorScheme.outlineVariant, label = "preview-border"
    )

    Box(
        modifier = modifier
            .size(size)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(backgroundColor)
            .border(
                width = 1.5.dp, color = borderColor, shape = MaterialTheme.shapes.extraLarge
            ), contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = color ?: MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(size * 0.45f)
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    tags: List<Tag> = emptyList()
) {

    LazyColumn(
        modifier = modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)
    ) {

        items(items = tags, key = {it.id}){tag->
            TagCard(
                title = tag.name, color = tag.color
            )
        }
    }
}

@Composable
fun TagCard(
    modifier: Modifier = Modifier, title: String, color: Color
) {
    ListItem(modifier = modifier, headlineContent = {
        Text(
            text = title, style = MaterialTheme.typography.titleSmall.copy(
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
            ), contentDescription = null, colorFilter = ColorFilter.tint(color)
        )
    }, trailingContent = {
        IconButton(
            onClick = {}) {
            Icon(
                imageVector = Icons.Default.MoreVert, contentDescription = null
            )
        }
    })
}


@Preview
@Composable
private fun TagsScreenDarkPreview() {

    FlowTaskTheme(
        dynamicColor = false, darkTheme = true
    ) {
        TagsScreen(
            onBack = {}, onEvent = {}, uiState = TagsUiState()
        )
    }
}