package com.joseleandro.flowtask.ui.screen.task.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.domain.model.SubtaskItem
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme


@Composable
fun Subtasks(
    subtasks: List<SubtaskItem>,
    onAdd: (String) -> Unit,
    onToggle: (String) -> Unit,
    onRemove: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val total = subtasks.size
    val done = subtasks.count { it.done }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Subtarefas",
                style = MaterialTheme.typography.titleMedium
            )

            if (total > 0) {
                Text(
                    text = "$done/$total",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 220.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            items(subtasks, key = { it.code }) { subtask ->

                SubtaskRow(
                    subtask = subtask,
                    onToggle = onToggle,
                    onRemove = onRemove
                )
            }
        }

        AddSubtaskField(
            onAdd = onAdd
        )
    }
}

@Composable
private fun SubtaskRow(
    subtask: SubtaskItem,
    onToggle: (String) -> Unit,
    onRemove: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            modifier = Modifier.size(40.dp),
            checked = subtask.done,
            onCheckedChange = { onToggle(subtask.code) }
        )

        Text(
            text = subtask.title,
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp),
            style = MaterialTheme.typography.bodyMedium,
            textDecoration =
                if (subtask.done) TextDecoration.LineThrough else null,
            color =
                if (subtask.done)
                    MaterialTheme.colorScheme.onSurface.copy(.5f)
                else
                    MaterialTheme.colorScheme.onSurface
        )

        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = { onRemove(subtask.code) }
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Remover"
            )
        }
    }
}

@Composable
private fun AddSubtaskField(
    onAdd: (String) -> Unit
) {

    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            modifier = Modifier.size(40.dp),
            checked = false,
            onCheckedChange = null
        )

        BasicTextField(
            value = text,
            onValueChange = { text = it },
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp),
            decorationBox = { inner ->

                Box(
                    contentAlignment = Alignment.CenterStart
                ) {

                    if (text.isEmpty()) {
                        Text(
                            "Adicionar subtarefa",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(.5f)
                        )
                    }

                    inner()
                }
            }
        )

        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = {

                val value = text.trim()

                if (value.isNotEmpty()) {
                    onAdd(value)
                    text = ""
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Adicionar"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSubtasks() {

    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {

        Subtasks(
            subtasks = listOf(
                SubtaskItem(code = "1", title = "Comprar leite"),
                SubtaskItem(code = "2", title = "Enviar relatório"),
                SubtaskItem(code = "3", title = "Estudar Kotlin", done = true)
            ),
            onAdd = {},
            onToggle = {},
            onRemove = {}
        )
    }
}