package com.example.gymapp.core.model

import java.time.Instant


data class TrackRecord(
    val startSession: Instant = Instant.now(),
    val nameSet : String = "Chess Press",
    val endSession: Instant?
)

data class TrackRecordWithSet(
    val session: TrackRecord?,
    val sets : List<TrackOnSetRecord> = emptyList<TrackOnSetRecord>()
)

data class TrackOnSetRecord(
    val startedAt: Instant = Instant.now(),
    var weight : Double? = null,
    var reps : Int? = null,
    var setNumber : Int = 1,
    val endedAt: Instant?
)