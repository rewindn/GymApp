package com.example.gymapp.features.start

import com.example.gymapp.core.model.WorkoutPlan

data class StartUiState(
    val isLoading: Boolean = true,
    val plans: List<WorkoutPlan> = emptyList(),
    val errorMessage: String? = null
)