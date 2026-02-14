package com.joseleandro.flowtask.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    state: CalendarState,
    firstDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY
) {

    val daysOfWeek = daysOfWeek(firstDayOfWeek = firstDayOfWeek)
    val currentYearMonth = state.firstVisibleMonth.yearMonth

    val monthLabelHeader = currentYearMonth.month.getDisplayName(
        TextStyle.FULL, Locale.getDefault()
    ).replaceFirstChar { char ->
        if (char.isLowerCase()) char.titlecase(Locale.getDefault())
        else char.toString()
    }
    val scope = rememberCoroutineScope()
    val yearLabelHeader = currentYearMonth.year.toString()


    Surface() {
        BoxWithConstraints(
            modifier.fillMaxWidth()
        ) {
            val width = maxWidth

            val sizeFieldDay = width / 7

            HorizontalCalendar(
                modifier = modifier,
                state = state,
                monthHeader = {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(28.dp)
                    ) {

                        HeaderCalendar(
                            month = monthLabelHeader,
                            year = yearLabelHeader,
                            onNext = {
                                scope.launch {
                                    state.animateScrollToMonth(currentYearMonth.nextMonth)
                                }
                            },
                            onPrevious = {
                                scope.launch {
                                    state.animateScrollToMonth(currentYearMonth.previousMonth)
                                }
                            }
                        )
                        DaysOfWeekTitle(
                            daysOfWeek = daysOfWeek
                        )
                    }

                },
                dayContent = { calendarDay ->
                    DayMonth(
                        calendarDay = calendarDay,
                        size = sizeFieldDay
                    )
                }
            )
        }

    }
}

@Composable
fun HeaderCalendar(
    modifier: Modifier = Modifier,
    month: String,
    year: String,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
) {
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = Color(0xFF9CA3AF)
            ),
            onClick = onPrevious
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = null,
            )

        }

        Text(
            text = "$month $year",
            style = MaterialTheme.typography.titleMedium,
        )

        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = Color(0xFF9CA3AF)
            ),
            onClick = onNext
        ) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null
            )
        }
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5D6D83)
                ),
                text = dayOfWeek.getDisplayName(
                    TextStyle.SHORT_STANDALONE,
                    Locale.getDefault()
                )
                    .uppercase()
                    .removeSuffix("."),
            )
        }
    }
}

@Composable
fun DayMonth(
    modifier: Modifier = Modifier,
    calendarDay: CalendarDay,
    size: Dp
) {

    val today = LocalDate.now()

    val isToday = calendarDay.date == today
    val isOtherMonth = calendarDay.position != DayPosition.MonthDate

    Box(
        modifier = modifier
            .width(size)
            .heightIn(min = size)
            .padding(4.dp)
            .background(
                color = when {
                    isToday -> MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
                    else -> Color.Transparent
                },
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = calendarDay.date.dayOfMonth.toString(),
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 12.sp,
                color = when {
                    isOtherMonth -> Color(0xFFCCD6E2).copy(alpha = 0.35f)
                    else -> Color(0xFFCCD6E2)
                }
            )
        )
    }
}

@Preview
@Composable
private fun CalendarDarkPreview() {

    val calendarState = rememberCalendarState()

    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        Calendar(
            state = calendarState
        )
    }
}