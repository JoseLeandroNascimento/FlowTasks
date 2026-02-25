package com.joseleandro.flowtask.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme

@Composable
fun TagsBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
            )
            .padding(top = 8.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = stringResource(R.string.nova_tag),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )

        FlowTaskTextField(
            label = stringResource(R.string.nome_da_tag),
            placeholder = stringResource(R.string.ex_trabalho_escola),
            state = rememberTextFieldState(),
            leadingIcon = {
                Image(
                    painter = painterResource(
                        id = R.drawable.park_solid_tag
                    ),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {}
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
                onClick = {}
            ) {
                Text(
                    text = stringResource(id = R.string.salvar_tag)
                )
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
            onDismissRequest = {}
        )
    }
}