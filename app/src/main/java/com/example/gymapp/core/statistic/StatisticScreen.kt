package com.example.gymapp.core.features.statistic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymapp.core.components.TodayWorkoutSection
import com.example.gymapp.core.components.TodayWorkoutUiModel
import com.example.gymapp.core.components.TopAppBar
import com.example.gymapp.core.components.calendar.MonthlyCalendarCard
import com.example.gymapp.core.components.calendar.WeeklyCalendarStrip
import com.example.gymapp.ui.theme.GymAppTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun StatisticScreen(
    modifier: Modifier = Modifier,
    viewModel: StatisticViewModel = viewModel(),
    onSettingsClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StatisticContent(
        uiState = uiState,
        modifier = modifier,
        onDateSelected = { clickedDate ->
            viewModel.selectDate(clickedDate)
        },
        onSettingsClick = onSettingsClick
    )
}


@Composable
fun StatisticContent(
    uiState: StatisticUiState,
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit,
    onSettingsClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    val sectionTitle = if (uiState.selectedDate == LocalDate.now()) {
        "Today Workout"
    } else {
        uiState.selectedDate.format(DateTimeFormatter.ofPattern("dd MMM 'Workout'", Locale.ENGLISH))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
         .verticalScroll(scrollState)
            .padding(horizontal = 23.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        TopAppBar(
            text = "Statistic",
            onExitClick = {},
            modifier = Modifier
                .padding(top = 45.dp)
                .fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(28.dp))


        WeeklyCalendarStrip(
            weekDates = uiState.currentWeekDays,
            selectedDate = uiState.selectedDate,
            workoutDates = uiState.allWorkoutDates,
            onDateClick = onDateSelected
        )

        Spacer(modifier = Modifier.height(61.dp))


        MonthlyCalendarCard(
            startMonth = remember { YearMonth.now() },
            getWorkoutDaysForMonth = { yearMonth ->
                uiState.getWorkoutDaysInMonth(yearMonth)
            },
            onDateClick = onDateSelected
        )

        Spacer(modifier = Modifier.height(41.dp))


        if (uiState.selectedDateWorkouts.isNotEmpty()) {
            TodayWorkoutSection(
                title = sectionTitle,
                items = uiState.selectedDateWorkouts,
                totalDuration = uiState.selectedDateTotalDuration,

            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No workout recorded for this date",
                    color = Color.Gray,
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(120.dp))
    }
}


@Preview(name = "Statistic Screen Preview", showBackground = true, backgroundColor = 0xFF1E1F22)
@Composable
fun StatisticContentPreview() {
    GymAppTheme(darkTheme = true) {
        Surface(color = Color(0xFF1E1F22)) {
            StatisticContent(
                uiState = StatisticUiState(
                    selectedDate = LocalDate.now(),
                    selectedDateTotalDuration = "1h 20m",
                    selectedDateWorkouts = listOf(
                        TodayWorkoutUiModel("1", "Chest Press", "Upper Body", "3 Sets",)
                    )
                ),
                onDateSelected = {},
                onSettingsClick = {}
            )
        }
    }
}