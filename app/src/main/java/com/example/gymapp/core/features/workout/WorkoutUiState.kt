package com.example.gymapp.core.features.workout

import com.example.gymapp.core.model.TrackOnSetRecord
import com.example.gymapp.core.model.TrackRecordWithSet
import com.example.gymapp.core.model.WorkoutPlan
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


data class WorkoutUiState (
    val masterPlan: WorkoutPlan = WorkoutPlan(
        nameWorkout = "Upper Body",
        startSession = Instant.now(),
        restTime = "90s",
        endSession = null,
        trackList = emptyList()
    ),
    val activeTrackList: List<TrackRecordWithSet> = emptyList(),
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
    val workoutPlan : WorkoutPlan = WorkoutPlan(
        nameWorkout = "Upper Body",
        startSession = Instant.now(),
        restTime = "90s",
        endSession = null,
        trackList = emptyList()
    ), )
{

    val formattedDate: String
        get() {
            val start = workoutPlan.startSession ?: sessionStartedAt
            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
            return start.atZone(ZoneId.systemDefault()).format(formatter)
        }


    val formattedTimeRange: String
        get() {
            val start = workoutPlan.startSession ?: sessionStartedAt
            val end = workoutPlan.endSession ?: Instant.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
            val startStr = start.atZone(ZoneId.systemDefault()).format(formatter)
            val endStr = end.atZone(ZoneId.systemDefault()).format(formatter)
            return "$startStr - $endStr"
        }


    val formattedTotalDuration: String
        get() {
            val start = workoutPlan.startSession ?: sessionStartedAt
            val end = workoutPlan.endSession ?: Instant.now()
            val seconds = Duration.between(start, end).seconds.coerceAtLeast(0)
            val hours = seconds / 3600
            val minutes = (seconds % 3600) / 60
            val remainingSecs = seconds % 60

            return if (hours > 0) {
                "%02dh %02dm".format(hours, minutes)
            } else {
                "%02dm %02dsec".format(minutes, remainingSecs)
            }
        }
}

