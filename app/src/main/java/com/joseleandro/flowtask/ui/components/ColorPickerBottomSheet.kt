package com.joseleandro.flowtask.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.ui.theme.ColorPickerPallet
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme
import kotlinx.coroutines.launch

class ColorPickerBottomSheetScope(
    val selectedColor: Color?
)

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerBottomSheet(
    selectedColor: Color?,
    preview: (@Composable ColorPickerBottomSheetScope.() -> Unit)? = null,
    onColorSelected: (Color) -> Unit,
    onDismissRequest: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true }
    )
    var colorSelect by remember { mutableStateOf<Color?>(selectedColor) }
    val scope = rememberCoroutineScope()
    val bottomSheetScope by remember(selectedColor) {
        derivedStateOf {
            ColorPickerBottomSheetScope(
                selectedColor = colorSelect
            )
        }
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.7f),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.escolha_a_cor_de_fundo),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )

            preview?.let { previewContent ->
                bottomSheetScope.previewContent()
            }

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                val spacing = 8.dp
                val minItemSize = 48.dp
                val horizontalPadding = 16.dp * 2
                val availableWidth = maxWidth - horizontalPadding

                val columnsCount =
                    ((availableWidth + spacing) / (minItemSize + spacing)).toInt().coerceAtLeast(1)
                val itemSize = (availableWidth - (spacing * (columnsCount - 1))) / columnsCount

                LazyVerticalGrid(
                    columns = GridCells.Fixed(columnsCount),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(spacing),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    ColorPickerPallet.colorCategories.forEach { category ->
                        item(span = { GridItemSpan(maxLineSpan) }, key = category.category) {
                            Text(
                                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                                text = stringResource(id = category.category.title),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
                                )
                            )
                        }

                        items(category.colors, key = { it.toArgb() }) { color ->
                            ColorSelect(
                                size = itemSize,
                                color = color,
                                selected = colorSelect == color,
                                onSelected = { colorSelect = color }
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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
                    onClick = {
                        colorSelect?.let { colorSelect ->
                            onColorSelected(colorSelect)
                            scope.launch {
                                sheetState.hide()
                            }
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.aplicar)
                    )
                }
            }
        }
    }

}


@Preview
@Composable
private fun ColorPickerBottomSheetDarkPreview() {
    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        ColorPickerBottomSheet(
            selectedColor = null,
            onColorSelected = {},
            onDismissRequest = {}
        )
    }
}