package com.joseleandro.flowtask.ui.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.ui.screen.TagColorPreview
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsBottomSheet(
    modifier: Modifier = Modifier,
    colorTagSelected: Color,
    textValue: String,
    onDismissRequest: () -> Unit,
    onTextValueChange: (String) -> Unit,
    onSave: () -> Unit,
    onColorSelected: (Color) -> Unit
) {

    var showColorPickerBottomSheet by remember { mutableStateOf(false) }

    if (showColorPickerBottomSheet) {
        ColorPickerBottomSheet(
            onDismissRequest = {
                showColorPickerBottomSheet = false
            },
            preview = {
                TagColorPreview(
                    iconRes = R.drawable.park_solid_tag,
                    color = selectedColor
                )
            },
            selectedColor = colorTagSelected,
            onColorSelected = onColorSelected
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
                    value = textValue,
                    onValueChange = onTextValueChange,
                    leadingIcon = {
                        Image(
                            painter = painterResource(
                                id = R.drawable.park_solid_tag
                            ),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(
                                color = colorTagSelected
                            )
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                showColorPickerBottomSheet = true
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
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = .2f),
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = MaterialTheme.shapes.extraLarge,
                        onClick = onDismissRequest
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
                        onClick = onSave
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
            onDismissRequest = {},
            colorTagSelected = MaterialTheme.colorScheme.primary,
            onColorSelected = {},
            textValue = "",
            onTextValueChange = {},
            onSave = {}
        )
    }
}