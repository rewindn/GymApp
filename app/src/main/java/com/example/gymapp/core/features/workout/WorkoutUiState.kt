package com.example.gymapp.core.features.workout

import androidx.lifecycle.ViewModel

data class WorkoutUiState (
    val timer : String = "120000",
    val titleWorkout : String = "Chess Press",
    val status : String = "OnSet",
    val labelSet : Int = 1,
    val lastReps : Int = 12,
    val lastWeight : Int = 50,
    val progress : Float = 1f,
    val lastTotalTime : String = "1m 2sec",
    val repsInput: String = "",
    val weightInput: String = ""
)

