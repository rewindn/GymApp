package com.example.gymapp.features.start

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.core.database.AppDatabase
import com.example.gymapp.core.database.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StartViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = WorkoutRepository(database.workoutDao(),application,database)
    private val _uiState = MutableStateFlow(StartUiState())

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
    loadData()
    }

        private fun loadData() {
            viewModelScope.launch {
                Log.d("GYM_DEBUG", "1. StartViewModel: Mulai loadData")
                _uiState.update { it.copy(isLoading = true) }

                try {
                    Log.d("GYM_DEBUG", "2. StartViewModel: Menjalankan seedDatabaseIfEmpty()")
                    repository.seedDatabaseIfEmpty()
                    Log.d("GYM_DEBUG", "3. StartViewModel: Selesai seedDatabaseIfEmpty()")
                } catch (e: Exception) {
                    Log.e("GYM_DEBUG", "ERROR saat seed: ${e.message}", e)
                }

                Log.d("GYM_DEBUG", "4. StartViewModel: Mulai mendengarkan allWorkouts.collect")
                repository.allWorkouts.collect { plansList ->
                    Log.d(
                        "GYM_DEBUG",
                        "5. StartViewModel: Emisi diterima! Jumlah plan: ${plansList.size}"
                    )
                    _uiState.update {
                        it.copy(
                            plans = plansList,
                            isLoading = false
                        )
                    }
                }
            }
        }
}