package com.example.gymapp.core.features.statistic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.core.components.TodayWorkoutUiModel
import com.example.gymapp.core.components.WorkoutSetDetail
import com.example.gymapp.core.database.AppDatabase
import com.example.gymapp.core.database.WorkoutRepository
import com.example.gymapp.core.model.WorkoutPlan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

class StatisticViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = WorkoutRepository(database.workoutDao(), application, database)


    private val _uiState = MutableStateFlow(StatisticUiState())
    val uiState: StateFlow<StatisticUiState> = _uiState.asStateFlow()

    private var rawWorkouts: List<WorkoutPlan> = emptyList()



    init {
        updateWeekDays(LocalDate.now())
        observeWorkouts()
    }

    private fun observeWorkouts() {
        viewModelScope.launch {
            repository.allWorkouts.collect { workoutList ->
                rawWorkouts = workoutList

                val workoutDates = workoutList.mapNotNull { plan ->
                    plan.startSession?.toLocalDate()
                }.toSet()

                _uiState.update { it.copy(allWorkoutDates = workoutDates) }

                filterWorkoutsByDate(_uiState.value.selectedDate)
            }
        }
    }


    fun selectDate(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
        updateWeekDays(date)
        filterWorkoutsByDate(date)
    }

    private fun filterWorkoutsByDate(date: LocalDate) {
        val workoutsOnDate = rawWorkouts.filter { plan ->
            plan.startSession?.toLocalDate() == date
        }

        val uiItems = mutableListOf<TodayWorkoutUiModel>()
        var totalSeconds = 0L

        workoutsOnDate.forEach { plan ->
            if (plan.startSession != null && plan.endSession != null) {
                totalSeconds += Duration.between(plan.startSession, plan.endSession).seconds
            }

            plan.trackList.forEachIndexed { index, track ->
                val maxWeight = track.sets.maxOfOrNull { it.weight ?: 0.0 } ?: 0.0

                val setDetails = track.sets.mapIndexed { setIndex, setItem ->
                    WorkoutSetDetail(
                        setNumber = setIndex + 1,
                        reps = setItem.reps ?: 0,
                        weight = "${(setItem.weight ?: 0.0).toInt()}kg",
                        gain = "+ 2kg" // Nanti bisa dihubungkan ke kalkulasi gain jika data sesi lalu sudah ada
                    )
                }

                uiItems.add(
                    TodayWorkoutUiModel(
                        id = "${plan.nameWorkout}_${track.session.nameSet}_$index",
                        title = track.session.nameSet,
                        category = plan.nameWorkout,
                        setCount = "${setDetails.size} Sets",
                        sets = setDetails

                    )
                )
            }
        }

        val durationFormatted = formatDuration(totalSeconds)

        _uiState.update {
            it.copy(
                selectedDateWorkouts = uiItems,
                selectedDateTotalDuration = durationFormatted
            )
        }
    }

    private fun updateWeekDays(date: LocalDate) {
        val sunday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
        val days = (0..6).map { sunday.plusDays(it.toLong()) }
        _uiState.update { it.copy(currentWeekDays = days) }
    }

    private fun Instant.toLocalDate(): LocalDate {
        return this.atZone(ZoneId.systemDefault()).toLocalDate()
    }

    private fun formatDuration(seconds: Long): String {
        val h = seconds / 3600
        val m = (seconds % 3600) / 60
        return if (h > 0) "${h}h ${m}m" else "${m}m"
    }
}