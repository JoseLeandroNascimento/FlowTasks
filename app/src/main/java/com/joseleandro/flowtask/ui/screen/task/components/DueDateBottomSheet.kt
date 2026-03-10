package com.joseleandro.flowtask.ui.screen.task.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.domain.model.RepeatConfig
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

fun RepeatConfig.isDateAllowed(date: LocalDate): Boolean {
    return when (this) {

        RepeatConfig.None -> true

        is RepeatConfig.Daily -> {
            true
        }

        is RepeatConfig.Weekly -> {

            date.dayOfWeek in daysOfWeek
        }

        is RepeatConfig.Monthly -> {

            date.dayOfMonth in daysOfMonth
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DueDateBottomSheet(
    currentDate: LocalDate?,
    repeatConfig: RepeatConfig,
    onDismiss: () -> Unit,
    onConfirm: (LocalDate?) -> Unit
) {

    val currentMonth = remember { YearMonth.now() }
    val startMonth = currentMonth.minusMonths(24)
    val endMonth = currentMonth.plusMonths(24)

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var selectedDate by remember { mutableStateOf(currentDate) }

    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = DayOfWeek.MONDAY
    )

    val coroutineScope = rememberCoroutineScope()

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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "Data de vencimento",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Escolha a data em que a tarefa deve ser concluída.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f)
                )

                selectedDate?.let {
                    Text(
                        text = "Selecionado: ${
                            it.dayOfMonth.toString().padStart(2, '0')
                        }/${it.monthValue.toString().padStart(2, '0')}/${it.year}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                CalendarHeader(
                    calendarState = calendarState,
                    onPrevious = {
                        coroutineScope.launch {
                            calendarState.animateScrollToMonth(
                                calendarState.firstVisibleMonth.yearMonth.minusMonths(1)
                            )
                        }
                    },
                    onNext = {
                        coroutineScope.launch {
                            calendarState.animateScrollToMonth(
                                calendarState.firstVisibleMonth.yearMonth.plusMonths(1)
                            )
                        }
                    }
                )

                WeekDaysRow()

                HorizontalCalendar(
                    modifier = Modifier.padding(top = 16.dp),
                    state = calendarState,
                    dayContent = { day ->

                        val date = day.date
                        val isSelected = selectedDate == date
                        val isToday = date == LocalDate.now()
                        val isAllowed = repeatConfig.isDateAllowed(date)

                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        Color.Transparent
                                )
                                .clickable(
                                    enabled = isAllowed
                                ) {
                                    selectedDate = date
                                },
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = date.dayOfMonth.toString(),
                                color = when {
                                    day.position != DayPosition.MonthDate ->
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)

                                    !isAllowed ->
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)

                                    isSelected ->
                                        Color.White

                                    else ->
                                        MaterialTheme.colorScheme.onSurface
                                }
                            )
                        }
                    }
                )
            }

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
                        onConfirm(selectedDate)
                    }
                ) {
                    Text("Salvar data")
                }
            }
        }
    }
}

@Composable
private fun CalendarHeader(
    calendarState: CalendarState,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {

    val month = calendarState.firstVisibleMonth.yearMonth

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = onPrevious) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
        }

        Text(
            text = month.month.name.lowercase()
                .replaceFirstChar { it.uppercase() } +
                    " ${month.year}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        IconButton(onClick = onNext) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, null)
        }
    }
}

@Composable
private fun WeekDaysRow() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        DayOfWeek.entries.forEach { day ->

            Text(
                text = day.name.take(3),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(
    showBackground = true,
    locale = "pt"
)
@Composable
private fun DueDateBottomSheetDarkPreview() {
    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        DueDateBottomSheet(
            currentDate = LocalDate.now(),
            repeatConfig = RepeatConfig.Monthly(
                daysOfMonth = setOf(1, 2, 3, 4)
            ),
            onDismiss = {},
            onConfirm = {}
        )
    }
}