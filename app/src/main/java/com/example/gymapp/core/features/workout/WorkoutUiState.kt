package com.example.gymapp.core.features.workout

import com.example.gymapp.core.model.TrackOnSetRecord
import com.example.gymapp.core.model.TrackRecordWithSet
import com.example.gymapp.core.model.WorkoutPlan
import java.time.Instant


data class WorkoutUiState (
    val timer : String = "120000",
    val titleWorkout : String = "Chess Press",
    val status : String = "OnSet",
    val sessionStart: Instant = Instant.now(),
    val labelSet : Int = 1,
    val indexWorkout : Int = 0,
    val lastReps : Int? = 12,
    val isResting  : Boolean = false,
    val isDone  : Boolean = false,
    val lastWeight : Int? = 50,
    val progress : Float = 1f,
    val lastTotalTime : String = "1m 2sec",
    val repsInput: String = "",
    val weightInput: String = "",
    val sessionStartedAt : Instant = Instant.now(),
    val trackStartedAt : Instant = Instant.now(),
    val completeSets : List<TrackOnSetRecord> = emptyList(),
    val completeTrackList: List<TrackRecordWithSet> = emptyList<TrackRecordWithSet>(),
    val workoutPlan : WorkoutPlan,

    )

