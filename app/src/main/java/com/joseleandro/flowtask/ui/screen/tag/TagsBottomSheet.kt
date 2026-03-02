package com.joseleandro.flowtask.ui.screen.tag

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.ui.components.ColorPickerBottomSheet
import com.joseleandro.flowtask.ui.components.FlowTaskTextField
import com.joseleandro.flowtask.ui.components.hideKeyboard
import com.joseleandro.flowtask.ui.event.CreateTagEvent
import com.joseleandro.flowtask.ui.state.CreateTagUiState
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme
import com.joseleandro.flowtask.ui.viewmodel.CreateTagViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun TagsBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
) {

    val viewModel = koinViewModel<CreateTagViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isDismissRequest) {
        if (uiState.isDismissRequest) {
            onDismissRequest()
            viewModel.onEvent(CreateTagEvent.OnReset)
        }
    }


    TagsBottomSheet(
        modifier = modifier,
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onDismissRequest = {
            viewModel.onEvent(CreateTagEvent.OnDismiss)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsBottomSheet(
    modifier: Modifier = Modifier,
    uiState: CreateTagUiState,
    onEvent: (CreateTagEvent) -> Unit,
    onDismissRequest: () -> Unit,
) {


    if (uiState.showColorPickerBottomSheet) {
        ColorPickerBottomSheet(
            onDismissRequest = {
                onEvent(CreateTagEvent.ChangeVisibilityColorPickerBottomSheet(false))
            },
            preview = {
                TagColorPreview(
                    iconRes = R.drawable.park_solid_tag,
                    color = selectedColor
                )
            },
            selectedColor = uiState.form.color.value,
            onColorSelected = { color ->
                onEvent(CreateTagEvent.OnChangeColor(color))
            }
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .hideKeyboard()
                .padding(
                    horizontal = 16.dp,
                )
                .padding(top = 8.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = stringResource(R.string.nova_tag),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )


            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                FlowTaskTextField(
                    label = stringResource(R.string.nome_da_tag),
                    placeholder = stringResource(R.string.ex_trabalho_escola),
                    value = uiState.form.name.value,
                    onValueChange = {
                        onEvent(CreateTagEvent.OnChangeName(it))
                    },
                    isError = uiState.form.name.error != null,
                    errorMessage = uiState.form.name.error?.let { error -> stringResource(id = error) },
                    leadingIcon = {
                        Image(
                            painter = painterResource(
                                id = R.drawable.park_solid_tag
                            ),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(
                                color = uiState.form.color.value
                            )
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                onEvent(CreateTagEvent.ChangeVisibilityColorPickerBottomSheet(true))
                            }
                        ) {
                            Image(
                                painter = painterResource(
                                    id = R.drawable.color_lens
                                ),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .hideKeyboard()
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = .2f),
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = MaterialTheme.shapes.extraLarge,
                        onClick = {
                            onEvent(CreateTagEvent.OnDismiss)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancelar)
                        )
                    }
                    Button(
                        modifier = Modifier
                            .hideKeyboard()
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        shape = MaterialTheme.shapes.extraLarge,
                        onClick = {
                            onEvent(CreateTagEvent.OnSave)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.salvar_tag)
                        )
                    }
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
private fun TagsBottomSheetContentDarkPreview() {
    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        TagsBottomSheet(
            uiState = CreateTagUiState(),
            onEvent = {},
            onDismissRequest = {},
        )
    }
}