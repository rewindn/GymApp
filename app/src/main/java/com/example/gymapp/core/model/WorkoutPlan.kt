package com.example.gymapp.core.model

import java.time.Instant

data class WorkoutSession(
    val workoutList : List<WorkoutPlan>,
    )
data class WorkoutPlan(
    val startSession: Instant = Instant.now(),
    val nameWorkout : String = "Upper Body",
    val trackList : List<TrackRecordWithSet> = emptyList(),
    val endSession: Instant?
)


data class TrackRecordWithSet(
    val session: TrackPlan,
    val sets : List<TrackOnSetRecord>? = emptyList<TrackOnSetRecord>()
)
data class TrackPlan(
    val startSession: Instant = Instant.now(),
    val nameSet : String = "Chest Press",
    val totalSet : Int = 3,
    val endSession: Instant?
)
data class TrackOnSetRecord(
    val startedAt: Instant = Instant.now(),
    val weight : Double? = null,
    val reps : Int? = null,
    val setNumber : Int = 1,
    val endedAt: Instant?
)