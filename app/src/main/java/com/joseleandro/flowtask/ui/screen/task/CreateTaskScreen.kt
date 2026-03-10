package com.joseleandro.flowtask.ui.screen.task

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.ui.components.ColorPickerBottomSheet
import com.joseleandro.flowtask.ui.components.ColorSelect
import com.joseleandro.flowtask.ui.components.FlowTaskTextField
import com.joseleandro.flowtask.ui.components.hideKeyboard
import com.joseleandro.flowtask.ui.event.CreateTaskEvent
import com.joseleandro.flowtask.ui.extention.toDueDateText
import com.joseleandro.flowtask.ui.extention.toReadableText
import com.joseleandro.flowtask.ui.screen.task.components.DueDateBottomSheet
import com.joseleandro.flowtask.ui.screen.task.components.IconPickerBottomSheet
import com.joseleandro.flowtask.ui.screen.task.components.PlanningBottomSheet
import com.joseleandro.flowtask.ui.screen.task.components.ReminderBottomSheet
import com.joseleandro.flowtask.ui.screen.task.components.Subtasks
import com.joseleandro.flowtask.ui.screen.task.components.TaskIconSelect
import com.joseleandro.flowtask.ui.state.CreateTaskUiState
import com.joseleandro.flowtask.ui.theme.ColorPickerPallet
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme
import com.joseleandro.flowtask.ui.viewmodel.CreateTaskViewModel
import com.joseleandro.flowtask.ui.viewmodel.NavigationViewModel
import org.koin.compose.viewmodel.koinViewModel


val COLORS = ColorPickerPallet.colorCategories[0].colors.subList(0, 5)


@Composable
fun CreateTaskScreen() {

    val navigationViewModel: NavigationViewModel = koinViewModel()
    val createTaskViewModel: CreateTaskViewModel = koinViewModel()

    val uiState by createTaskViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = uiState.isBack) {

        if (uiState.isBack) {

            navigationViewModel.pop()
            createTaskViewModel.onEvent(CreateTaskEvent.OnResetUiState)
        }

    }

    CreateTaskScreen(
        onBack = navigationViewModel::pop,
        uiState = uiState,
        onEvent = createTaskViewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(
    onBack: () -> Unit,
    uiState: CreateTaskUiState,
    onEvent: (CreateTaskEvent) -> Unit
) {


    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.nova_tarefa),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        CreateTaskContent(
            modifier = Modifier.padding(innerPadding),
            onBack = onBack,
            uiState = uiState,
            onEvent = onEvent
        )
    }

}

@Composable
fun CreateTaskContent(
    modifier: Modifier = Modifier,
    uiState: CreateTaskUiState,
    onEvent: (CreateTaskEvent) -> Unit,
    onBack: () -> Unit
) {

    val context = LocalContext.current

    if (uiState.showDueDateSheet) {
        DueDateBottomSheet(
            currentDate = uiState.form.dueDate,
            repeatConfig = uiState.form.repeatConfig,
            onDismiss = {
                onEvent(CreateTaskEvent.ChangeVisibilityDueDateSheet(false))
            },
            onConfirm = { date ->
                onEvent(CreateTaskEvent.ChangeDueDate(date))
            }
        )
    }

    if (uiState.showColorPickerBottomSheet) {
        ColorPickerBottomSheet(
            selectedColor = uiState.form.colorSelected.value,
            onColorSelected = { color ->
                onEvent(CreateTaskEvent.OnChangeColor(color))
            },
            onDismissRequest = {
                onEvent(CreateTaskEvent.ChangeVisibilityColorPickerBottomSheet(false))
            },
            preview = {
                selectedColor?.let { selectedColor ->

                    uiState.form.selectIcon?.let {

                        TaskIconSelect(
                            color = selectedColor,
                            onClick = {
                                onEvent(CreateTaskEvent.ChangeVisibilityEmojiPicker(true))
                            },
                            icon = uiState.form.selectIcon
                        )
                    }
                }
            }
        )
    }

    if (uiState.showRepeatSheet) {
        PlanningBottomSheet(
            currentConfig = uiState.form.repeatConfig,
            onDismiss = {
                onEvent(CreateTaskEvent.ChangeVisibilityRepeatSheet(false))
            },
            onConfirm = { newConfig ->
                onEvent(
                    CreateTaskEvent.ChangeRepeat(newConfig)
                )
            }
        )
    }

    if (uiState.showEmojiPicker) {
        IconPickerBottomSheet(
            onDismiss = {
                onEvent(CreateTaskEvent.ChangeVisibilityEmojiPicker(false))
            },
            onIconSelected = { emoji ->
                onEvent(CreateTaskEvent.SelectIcon(emoji))
            }
        )
    }

    if (uiState.showReminderSheetVisible) {
        ReminderBottomSheet(
            isEnabled = uiState.form.isReminderEnabled,
            initialHour = uiState.form.reminderHour,
            initialMinute = uiState.form.reminderMinute,
            onDismiss = {
                onEvent(CreateTaskEvent.ChangeVisibilityReminderSheet(false))
            },
            onConfirm = { enabled, hour, minute ->

                hour?.let {
                    onEvent(
                        CreateTaskEvent.ChangeReminderHour(hour)
                    )
                }

                if (uiState.form.isReminderEnabled != enabled) {
                    onEvent(
                        CreateTaskEvent.ToggleReminder(enabled)
                    )
                }

                minute?.let {
                    onEvent(
                        CreateTaskEvent.ChangeReminderMinute(minute)
                    )
                }

                onEvent(CreateTaskEvent.ChangeVisibilityReminderSheet(false))
            }
        )
    }

    Box(
        modifier = modifier
            .hideKeyboard()
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState()
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(
                    horizontal = 16.dp
                )
            ) {

                items(items = uiState.tags) { tag ->
                    FilterChip(
                        onClick = {
                            onEvent(CreateTaskEvent.OnChangeTag(tag))
                        },
                        label = {
                            Text(
                                text = tag.name,
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = MaterialTheme.shapes.large,
                        selected = uiState.form.tagSelected.value?.let { tagSelected ->
                            tag.id == tagSelected.id
                        } ?: false
                    )
                }
            }

            Column(
                modifier = Modifier.padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                TaskIconSelect(
                    color = uiState.form.colorSelected.value,
                    onClick = {
                        onEvent(CreateTaskEvent.ChangeVisibilityEmojiPicker(true))
                    },
                    icon = uiState.form.selectIcon
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.escolher_um_cone),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = stringResource(R.string.toque_para_alterar),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
                        )
                    )
                }
            }

            FlowTaskTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = uiState.form.title.value,
                isError = uiState.form.title.error != null,
                errorMessage = uiState.form.title.error?.let { stringResource(uiState.form.title.error) },
                onValueChange = {
                    onEvent(CreateTaskEvent.OnChangeTitle(it))
                },
                label = stringResource(R.string.nome_da_tarefa),
                placeholder = stringResource(R.string.digite_aqui_o_nome_da_tarefa)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                LabelSection(
                    label = stringResource(R.string.cor_de_fundo)
                )

                BoxWithConstraints(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val itemCount = COLORS.size + 1
                    val horizontalPadding = 16.dp * 2
                    val spacing = 8.dp

                    val availableWidth = maxWidth - horizontalPadding - (spacing * (itemCount - 1))
                    val itemSize = availableWidth / itemCount

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(spacing),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(COLORS) { color ->
                            ColorSelect(
                                size = itemSize.coerceAtMost(56.dp),
                                color = color,
                                selected = color == uiState.form.colorSelected.value,
                                onSelected = {
                                    onEvent(CreateTaskEvent.OnChangeColor(color))
                                }
                            )
                        }

                        item {

                            FilledIconButton(
                                modifier = Modifier.size(itemSize.coerceAtMost(56.dp)),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color(0xFFF2F2F2)
                                ),
                                onClick = {
                                    onEvent(
                                        CreateTaskEvent.ChangeVisibilityColorPickerBottomSheet(
                                            true
                                        )
                                    )
                                }
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = R.drawable.color_lens
                                    ),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }

            }

            Subtasks(
                subtasks = uiState.form.subtasks,
                onAdd = {
                    onEvent(CreateTaskEvent.AddSubtask(it))
                },
                onToggle = {
                    onEvent(CreateTaskEvent.ToggleSubtask(it))
                },
                onRemove = {
                    onEvent(CreateTaskEvent.RemoveSubtask(it))
                }
            )

            LabelSection(
                label = stringResource(R.string.detalhes)
            )

            Surface(
                modifier = Modifier.padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium,
                color = Color(0xFF151F2C)
            ) {

                Column(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                ) {

                    ListItem(
                        modifier = Modifier.clickable {
                            onEvent(CreateTaskEvent.ChangeVisibilityRepeatSheet(true))
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = Color(0xFF151F2C)
                        ),
                        leadingContent = {
                            Image(
                                painter = painterResource(
                                    id = R.drawable.calendar
                                ),
                                contentDescription = stringResource(R.string.icon_calendario),
                                colorFilter = ColorFilter.tint(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        },
                        headlineContent = {
                            Text(
                                text = stringResource(R.string.planejamento),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            )
                        },
                        trailingContent = {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = uiState.form.repeatConfig.toReadableText(),
                                    style = MaterialTheme.typography.labelMedium
                                )
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                    contentDescription = null
                                )
                            }
                        }
                    )


                    ListItem(
                        modifier = Modifier.clickable {
                            onEvent(CreateTaskEvent.ChangeVisibilityDueDateSheet(true))
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = Color(0xFF151F2C)
                        ),
                        leadingContent = {
                            Image(
                                painter = painterResource(
                                    id = R.drawable.target_fill
                                ),
                                contentDescription = stringResource(R.string.icon_calendario),
                                colorFilter = ColorFilter.tint(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        },
                        headlineContent = {
                            Text(
                                text = stringResource(R.string.data_de_vencimento),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            )
                        },
                        trailingContent = {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = uiState.form.dueDate.toDueDateText(context),
                                    style = MaterialTheme.typography.labelMedium
                                )
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                    contentDescription = null
                                )
                            }
                        }
                    )


                    ListItem(
                        modifier = Modifier.clickable {
                            onEvent(CreateTaskEvent.ChangeVisibilityReminderSheet(true))
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = Color(0xFF151F2C)
                        ),
                        leadingContent = {
                            Image(
                                painter = painterResource(
                                    id = R.drawable.alarm_sharp
                                ),
                                contentDescription = stringResource(R.string.icon_calendario),
                                colorFilter = ColorFilter.tint(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        },
                        headlineContent = {
                            Text(
                                text = stringResource(R.string.alerta),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            )
                        },
                        trailingContent = {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = if (uiState.form.isReminderEnabled) formatReminderTime(
                                        hour = uiState.form.reminderHour,
                                        minute = uiState.form.reminderMinute
                                    )
                                    else stringResource(R.string.sem_alarme),

                                    style = MaterialTheme.typography.labelMedium
                                )
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(
                    150.dp
                )
            )
        }

        Surface(
            modifier = Modifier.align(Alignment.BottomCenter),
            color = Color(0xFF151F2C)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = .1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                ) {

                    Button(
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = .2f),
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = onBack,
                        contentPadding = PaddingValues(
                            16.dp
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.cancelar),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        onClick = {
                            onEvent(CreateTaskEvent.OnSave)
                        },
                        contentPadding = PaddingValues(
                            16.dp
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.criar_tarefa),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }

}

fun formatReminderTime(
    hour: Int,
    minute: Int,
): String {

    return String.format("%02d:%02d", hour, minute)
}

@Composable
fun LabelSection(
    modifier: Modifier = Modifier,
    label: String
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = label,
        style = MaterialTheme.typography.titleSmall.copy(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
        )
    )
}


@Preview
@Composable
private fun CreateTaskScreenDarkPreview() {

    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        CreateTaskScreen(
            onBack = {},
            uiState = CreateTaskUiState(),
            onEvent = {}
        )
    }
}