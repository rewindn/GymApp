package com.example.gymapp.core.features.statistic

import com.example.gymapp.core.components.TodayWorkoutUiModel
import com.example.gymapp.core.components.WorkoutSetDetail
import java.time.LocalDate
import java.time.YearMonth

data class StatisticUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val currentWeekDays: List<LocalDate> = emptyList(),
    val allWorkoutDates: Set<LocalDate> = emptySet(),
    val selectedDateWorkouts: List<TodayWorkoutUiModel> = emptyList(),
    val selectedDateTotalDuration: String = "0m",
    val isLoading: Boolean = false,

) {
    fun getWorkoutDaysInMonth(yearMonth: YearMonth): Set<Int> {
        return allWorkoutDates
            .filter { YearMonth.from(it) == yearMonth }
            .map { it.dayOfMonth }
            .toSet()
    }
}