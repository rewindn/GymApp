package com.example.gymapp.core.model

import android.media.metrics.RecordingSession
import java.time.Instant


data class TrackRecord(
    val startSession: Instant = Instant.now(),
    val nameSet : String = "Chess Press",
    val endSession: Instant?
)

data class TrackRecordWithSet(
    val nameSet: TrackRecord?,
    val sets : List<TrackOnSetRecord> = emptyList<TrackOnSetRecord>()
)

data class TrackOnSetRecord(
    val startSession: Instant = Instant.now(),
    var weight : Int? = null,
    var reps : Int? = null,
    val setNumber : Int? = null,
    val endSession: Instant?
)