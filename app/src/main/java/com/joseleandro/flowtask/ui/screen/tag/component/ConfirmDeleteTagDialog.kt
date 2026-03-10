package com.joseleandro.flowtask.ui.screen.tag.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.domain.model.Tag
import com.joseleandro.flowtask.ui.components.DialogDefault
import com.joseleandro.flowtask.ui.components.hideKeyboard
import com.joseleandro.flowtask.ui.event.TagsEvent
import com.joseleandro.flowtask.ui.state.TagsUiState
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme

@Composable
fun ConfirmDeleteTagDialog(
    onEvent: (TagsEvent) -> Unit,
    uiState: TagsUiState
) {
    DialogDefault(
        onDismissRequest = {
            onEvent(TagsEvent.OnConfirmDeleteTag(null))
        },
        icon = Icons.Default.QuestionMark,
        title = "Confirme",
        actions = {
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
                    onEvent(TagsEvent.OnConfirmDeleteTag(null))
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
                    uiState.tagDelete?.let { tagDelete ->
                        onEvent(TagsEvent.OnDeleteTag(tagDelete.id))
                    }
                }
            ) {
                Text(
                    text = stringResource(id = R.string.confirme_dialog)
                )
            }
        }
    ) {
        uiState.tagDelete?.let { tagDelete ->
            Text(
                text = "Deseja excluir a tag ${tagDelete.name}?",
                style = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Preview
@Composable
private fun ConfirmDeleteTagDialogDarkPreview() {
    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        ConfirmDeleteTagDialog(
            onEvent = {},
            uiState = TagsUiState(
                tagDelete = Tag(
                    id = 1,
                    name = "Tag teste",
                    color = MaterialTheme.colorScheme.primary
                )
            )
        )
    }
}