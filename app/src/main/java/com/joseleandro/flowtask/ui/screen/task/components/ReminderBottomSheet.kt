package com.joseleandro.flowtask.ui.screen.task.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.ui.components.CircularTimePicker
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderBottomSheet(
    isEnabled: Boolean,
    initialHour: Int,
    initialMinute: Int,
    onDismiss: () -> Unit,
    onConfirm: (enabled: Boolean, hour: Int?, minute: Int?) -> Unit
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var enabled by remember { mutableStateOf(isEnabled) }
    var selectedHour by remember { mutableIntStateOf(initialHour) }
    var selectedMinute by remember { mutableIntStateOf(initialMinute) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight(.9f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Text(
                    text = "Definir lembrete",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Receba uma notificação no horário escolhido para não esquecer desta tarefa.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column {
                        Text(
                            text = "Lembrete",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = "Ativar lembrete para esta tarefa",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(.6f)
                        )
                    }

                    Switch(
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            uncheckedTrackColor = Color.White,
                        ),
                        checked = enabled,
                        onCheckedChange = { enabled = it }
                    )
                }

                AnimatedVisibility(enabled) {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "Horário do lembrete",
                            style = MaterialTheme.typography.labelLarge
                        )

                        Text(
                            text = "Você será avisado às %02d:%02d"
                                .format(selectedHour, selectedMinute),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Row(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            CircularTimePicker(
                                range = 0..23,
                                value = selectedHour,
                                onValueChange = { selectedHour = it }
                            )

                            Text(
                                text = ":",
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            CircularTimePicker(
                                range = 0..59,
                                value = selectedMinute,
                                onValueChange = { selectedMinute = it }
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = .2f),
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = MaterialTheme.shapes.extraLarge,
                    onClick = onDismiss
                ) {
                    Text(
                        text = stringResource(id = R.string.cancelar)
                    )
                }
                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.extraLarge,
                    onClick = {
                        if (enabled) {
                            onConfirm(true, selectedHour, selectedMinute)
                        } else {
                            onConfirm(false, null, null)
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.action_confirmar)
                    )
                }
            }

        }
    }
}


@Preview(
    showBackground = true,
)
@Composable
fun ReminderBottomSheetPreview() {

    FlowTaskTheme(
        darkTheme = true,
        dynamicColor = false
    ) {

        ReminderBottomSheet(
            isEnabled = true,
            initialHour = 14,
            initialMinute = 30,
            onDismiss = {},
            onConfirm = { _, _, _ -> }
        )
    }
}