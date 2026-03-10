package com.joseleandro.flowtask.ui.screen.task.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.domain.model.RepeatConfig
import com.joseleandro.flowtask.domain.model.RepeatType
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme
import java.time.DayOfWeek


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanningBottomSheet(
    currentConfig: RepeatConfig,
    onConfirm: (RepeatConfig) -> Unit,
    onDismiss: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var isEnabled by remember {
        mutableStateOf(currentConfig !is RepeatConfig.None)
    }

    var selectedType by remember {
        mutableStateOf(
            when (currentConfig) {
                is RepeatConfig.Daily -> RepeatType.DAILY
                is RepeatConfig.Weekly -> RepeatType.WEEKLY
                is RepeatConfig.Monthly -> RepeatType.MONTHLY
                else -> RepeatType.DAILY
            }
        )
    }

    var dailyInterval by remember {
        mutableStateOf(
            (currentConfig as? RepeatConfig.Daily)?.interval ?: 1
        )
    }

    var weeklyDays by remember {
        mutableStateOf(
            (currentConfig as? RepeatConfig.Weekly)?.daysOfWeek ?: emptySet()
        )
    }

    var monthlyDays by rememberSaveable {
        mutableStateOf(
            (currentConfig as? RepeatConfig.Monthly)?.daysOfMonth ?: emptySet()
        )
    }

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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {


                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    Text(
                        text = "Repetição",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "Configure quando a tarefa deve se repetir",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column {

                            Text("Repetir tarefa")

                            Text(
                                text = "A tarefa será criada novamente",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Switch(
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                uncheckedTrackColor = Color.White,
                            ),
                            checked = isEnabled,
                            onCheckedChange = { isEnabled = it }
                        )
                    }
                }

                if (isEnabled) {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        RepeatTypeSelector(
                            selected = selectedType,
                            onSelect = { selectedType = it }
                        )

                        AnimatedContent(
                            targetState = selectedType,
                            transitionSpec = {
                                fadeIn(animationSpec = tween(300)) + slideInVertically() togetherWith
                                        slideOutVertically() + fadeOut(animationSpec = tween(300))
                            },
                            label = "RepeatTypeAnimation"
                        ) { selectedType ->

                            when (selectedType) {

                                RepeatType.DAILY -> {
                                    DailyIntervalSelector(
                                        interval = dailyInterval,
                                        onChange = { dailyInterval = it }
                                    )
                                }

                                RepeatType.WEEKLY -> {
                                    WeeklyDaySelector(
                                        selectedDays = weeklyDays,
                                        onChange = { weeklyDays = it }
                                    )
                                }

                                RepeatType.MONTHLY -> {
                                    MonthlyDaySelector(
                                        selectedDays = monthlyDays,
                                        onChange = { monthlyDays = it }
                                    )
                                }

                                else -> {}
                            }
                        }

                        Text(
                            text = "Repetição: ${
                                buildRepeatPreview(
                                    selectedType,
                                    dailyInterval,
                                    weeklyDays,
                                    monthlyDays
                                )
                            }",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
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
                        val result =
                            if (!isEnabled) {
                                RepeatConfig.None
                            } else {
                                when (selectedType) {
                                    RepeatType.DAILY ->
                                        RepeatConfig.Daily(dailyInterval)

                                    RepeatType.WEEKLY ->
                                        RepeatConfig.Weekly(weeklyDays)

                                    RepeatType.MONTHLY ->
                                        RepeatConfig.Monthly(monthlyDays)

                                    RepeatType.NONE -> RepeatConfig.None
                                }
                            }

                        onConfirm(result)
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

fun buildRepeatPreview(
    type: RepeatType,
    interval: Int,
    weekly: Set<DayOfWeek>,
    monthly: Set<Int>
): String {

    return when (type) {

        RepeatType.DAILY ->
            if (interval == 1)
                "Todos os dias"
            else
                "A cada $interval dias"

        RepeatType.WEEKLY ->
            if (weekly.isEmpty())
                "Selecione os dias"
            else
                weekly.joinToString {
                    it.name.take(3).lowercase()
                        .replaceFirstChar { c -> c.uppercase() }
                }

        RepeatType.MONTHLY ->
            if (monthly.isEmpty())
                "Selecione os dias"
            else
                "Dias ${monthly.joinToString()}"

        RepeatType.NONE -> "Não repete"
    }
}


@Preview(
    showBackground = true,
    backgroundColor = 0xFF121212
)
@Composable
private fun PlanningBottomSheetNonePreview() {

    FlowTaskTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        PlanningBottomSheet(
            currentConfig = RepeatConfig.None,
            onConfirm = {},
            onDismiss = {}
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF121212
)
@Composable
private fun PlanningBottomSheetDailyPreview() {

    FlowTaskTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        PlanningBottomSheet(
            currentConfig = RepeatConfig.Daily(interval = 2),
            onConfirm = {},
            onDismiss = {}
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF121212
)
@Composable
private fun PlanningBottomSheetWeeklyPreview() {

    FlowTaskTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        PlanningBottomSheet(
            currentConfig = RepeatConfig.Weekly(
                daysOfWeek = setOf(
                    DayOfWeek.MONDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.FRIDAY
                )
            ),
            onConfirm = {},
            onDismiss = {}
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF121212
)
@Composable
private fun PlanningBottomSheetMonthlyPreview() {

    FlowTaskTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        PlanningBottomSheet(
            currentConfig = RepeatConfig.Monthly(daysOfMonth = setOf(1, 15)),
            onConfirm = {},
            onDismiss = {}
        )
    }
}