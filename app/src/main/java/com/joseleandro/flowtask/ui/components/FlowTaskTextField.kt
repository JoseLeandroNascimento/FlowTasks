package com.joseleandro.flowtask.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme

@Composable
fun FlowTaskTextField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    state: TextFieldState
) {

    OutlinedTextField(

        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFF151F2C),
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = .1f)
        ),
        state = state,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 22.dp
        )
    )
}

@Preview
@Composable
private fun FlowTaskTextFieldDarkPreview() {

    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        FlowTaskTextField(
            label = "Nome da Tarefa",
            placeholder = "Digite aqui o nome da tarefa",
            state = rememberTextFieldState()
        )
    }
}