package com.example.gymapp.core.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.ui.theme.GymAppTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val PAGER_MIDDLE_INDEX = 1200
private const val TOTAL_PAGES = 2400
private const val MAX_CALENDAR_SLOTS = 42

@Composable
fun MonthlyCalendarCard(
    modifier: Modifier = Modifier,
    startMonth: YearMonth = YearMonth.of(2026, 2),
    selectedDate: LocalDate? = null,
    daysOfWeek: List<String> = listOf("S", "M", "T", "W", "T", "F", "S"),
    getWorkoutDaysForMonth: (YearMonth) -> Set<Int> = { emptySet() },
    onDateClick: (LocalDate) -> Unit = {}
) {
    val baseMonth = remember { startMonth }

    val pagerState = rememberPagerState(
        initialPage = PAGER_MIDDLE_INDEX,
        pageCount = { TOTAL_PAGES }
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF2C2D31))
            .padding(vertical = 16.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val monthOffset = (page - PAGER_MIDDLE_INDEX).toLong()
            val currentYearMonth = baseMonth.plusMonths(monthOffset)

            val startDayOffset = currentYearMonth.atDay(1).dayOfWeek.value % 7
            val totalDaysInMonth = currentYearMonth.lengthOfMonth()
            val workoutDays = getWorkoutDaysForMonth(currentYearMonth)


            val gridItems = buildList {
                repeat(startDayOffset) { add(null) }
                for (day in 1..totalDaysInMonth) {
                    add(day)
                }
                while (size < MAX_CALENDAR_SLOTS) {
                    add(null)
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {

                Text(
                    text = currentYearMonth.format(
                        DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)
                    ),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    daysOfWeek.forEach { dayName ->
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dayName,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))


                gridItems.chunked(7).forEach { weekRow ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (i in 0 until 7) {
                            val dateNumber = weekRow.getOrNull(i)
                            val thisDate = dateNumber?.let { currentYearMonth.atDay(it) }

                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                CalendarDayCell(
                                    dayText = dateNumber?.toString() ?: "",
                                    hasWorkout = dateNumber != null && workoutDays.contains(dateNumber),
                                    onClick = {
                                        if (thisDate != null) {
                                            onDateClick(thisDate)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Monthly Card Fixed 6 Rows", showBackground = true, backgroundColor = 0xFF1E1F22)
@Composable
fun MonthlyCalendarCardPreview() {
    GymAppTheme(darkTheme = true) {
        Surface(color = Color(0xFF1E1F22), modifier = Modifier.padding(23.dp)) {
            MonthlyCalendarCard(
                startMonth = YearMonth.of(2026, 2),
                getWorkoutDaysForMonth = { setOf(2, 3, 4, 5, 23, 24, 26) }
            )
        }
    }
}