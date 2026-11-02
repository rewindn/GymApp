package com.example.gymapp.features.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.core.database.AppDatabase
import com.example.gymapp.core.database.WorkoutRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StartViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = WorkoutRepository(database.workoutDao(), application)

    val uiState: StateFlow<StartUiState> = repository.allWorkouts
        .map { allPlans ->
            val distinctTemplates = allPlans
                .groupBy { it.nameWorkout }
                .values
                .mapNotNull { plans -> plans.maxByOrNull { it.startSession } }

            StartUiState(
                isLoading = false,
                plans = distinctTemplates
            )
        }
        .catch { error ->
            emit(StartUiState(isLoading = false, errorMessage = error.message))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = StartUiState(isLoading = true)
        )

    init {
        viewModelScope.launch {
            repository.seedDatabaseIfEmpty()
        }
    }
}