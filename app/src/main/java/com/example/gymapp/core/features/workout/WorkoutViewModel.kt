package com.example.gymapp.core.features.workout

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.core.database.AppDatabase
import com.example.gymapp.core.database.WorkoutRepository
import com.example.gymapp.core.model.TrackOnSetRecord
import com.example.gymapp.core.model.TrackPlan
import com.example.gymapp.core.model.TrackRecordWithSet
import com.example.gymapp.core.model.WorkoutPlan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = WorkoutRepository(database.workoutDao(), application)

    private val _uistate = MutableStateFlow(WorkoutUiState())
    val uiState: StateFlow<WorkoutUiState> = _uistate.asStateFlow()

    private var stopwatchJob: Job? = null
    private var restTimerJob: Job? = null
    private var elapsedSeconds = 0L

    fun setStart(selectedPlan: WorkoutPlan) {
        val firstExerciseName = selectedPlan.trackList.firstOrNull()?.session?.nameSet ?: selectedPlan.nameWorkout

        _uistate.update {
            it.copy(
                workoutPlan = selectedPlan,
                titleWorkout = firstExerciseName,
                sessionStart = Instant.now(),
                sessionStartedAt = Instant.now(),
                trackStartedAt = Instant.now(),
                indexWorkout = 0,
                labelSet = 1,
                weightInput = "",
                repsInput = "",
                isResting = false,
                isDone = false,
                completeTrackList = emptyList(),
                completeSets = emptyList()
            )
        }
        startExerciseStopwatch()
    }

    private fun startExerciseStopwatch() {
        stopwatchJob?.cancel()
        restTimerJob?.cancel()
        elapsedSeconds = 0L

        stopwatchJob = viewModelScope.launch {
            while (!_uistate.value.isResting && !_uistate.value.isDone) {
                _uistate.update { it.copy(timer = formatTime(elapsedSeconds)) }
                delay(1000L)
                elapsedSeconds++
            }
        }
    }

    fun setButton() {
        val currentState = _uistate.value
        val weight = currentState.weightInput.toDoubleOrNull() ?: 0.0
        val reps = currentState.repsInput.toIntOrNull() ?: 0

        val newSet = TrackOnSetRecord(
            startedAt = currentState.sessionStart,
            weight = weight,
            reps = reps,
            endedAt = Instant.now(),
            setNumber = currentState.labelSet
        )

        stopwatchJob?.cancel()

        val updatedSets = currentState.completeSets + newSet
        val currentTrack = currentState.workoutPlan.trackList.getOrNull(currentState.indexWorkout)
        val targetTotalSets = currentTrack?.session?.totalSet ?: 3

        _uistate.update {
            it.copy(
                isResting = true,
                labelSet = it.labelSet + 1,
                lastReps = newSet.reps,
                lastWeight = newSet.weight?.toInt(),
                lastTotalTime = formatInMinute(
                    (newSet.endedAt?.toEpochMilli() ?: 0) - newSet.startedAt.toEpochMilli()
                ),
                completeSets = updatedSets
            )
        }

        // Cek transisi pergerakan track atau penyelesaian sesi
        if (updatedSets.size >= targetTotalSets) {
            val isLastExercise = currentState.indexWorkout + 1 >= currentState.workoutPlan.trackList.size
            if (isLastExercise) {
                setData(isDone = true)
                return
            } else {
                setData(isDone = false)
            }
        }

        startRestTimer()
    }

    // FUNGSI INI YANG DIPANGGIL OLEH WorkoutScreen (onExitTopBar & setButton)
    fun setData(isDone: Boolean = false) {
        val currentState = _uistate.value
        val currentTrack = currentState.workoutPlan.trackList.getOrNull(currentState.indexWorkout)

        // Bungkus track yang baru saja tuntas (jika ada set di dalamnya)
        val finishedTrackRecord = if (currentTrack != null && currentState.completeSets.isNotEmpty()) {
            TrackRecordWithSet(
                session = currentTrack.session.copy(
                    startSession = currentState.trackStartedAt,
                    endSession = Instant.now()
                ),
                sets = currentState.completeSets
            )
        } else null

        val updatedTrackList = if (finishedTrackRecord != null) {
            currentState.completeTrackList + finishedTrackRecord
        } else {
            currentState.completeTrackList
        }

        if (isDone) {
            stopwatchJob?.cancel()
            restTimerJob?.cancel()

            val completeWorkout = currentState.workoutPlan.copy(
                startSession = currentState.sessionStartedAt,
                endSession = Instant.now(),
                trackList = updatedTrackList
            )

            _uistate.update {
                it.copy(
                    workoutPlan = completeWorkout,
                    completeTrackList = updatedTrackList,
                    isDone = true
                )
            }

            Log.d("WorkoutViewModel", "Hasil akhir tersimpan: $completeWorkout")
            viewModelScope.launch(Dispatchers.IO) {
                repository.saveWorkoutPlan(completeWorkout)
            }
        } else {
            val nextIndex = currentState.indexWorkout + 1
            val nextExerciseName = currentState.workoutPlan.trackList.getOrNull(nextIndex)?.session?.nameSet ?: "Workout"

            _uistate.update {
                it.copy(
                    completeSets = emptyList(),
                    completeTrackList = updatedTrackList,
                    indexWorkout = nextIndex,
                    labelSet = 1,
                    trackStartedAt = Instant.now(),
                    titleWorkout = nextExerciseName
                )
            }
        }
    }

    private fun startRestTimer() {
        restTimerJob?.cancel()
        val restTimeSeconds = parseRestTimeToSeconds(_uistate.value.workoutPlan.restTime)

        _uistate.update { it.copy(titleWorkout = "Rest", progress = 1f) }

        restTimerJob = viewModelScope.launch {
            for (sec in restTimeSeconds downTo 0L) {
                val progressFraction = sec.toFloat() / restTimeSeconds.toFloat()
                _uistate.update {
                    it.copy(
                        timer = formatTime(sec),
                        progress = progressFraction
                    )
                }
                if (sec > 0L) delay(1000L)
            }
            onFinishRest()
        }
    }

    fun restButton() {
        restTimerJob?.cancel()
        onFinishRest()
    }

    private fun onFinishRest() {
        val currentState = _uistate.value
        if (currentState.isDone) return

        val currentExerciseName = currentState.workoutPlan.trackList
            .getOrNull(currentState.indexWorkout)?.session?.nameSet ?: "Workout"

        _uistate.update {
            it.copy(
                titleWorkout = currentExerciseName,
                isResting = false,
                sessionStart = Instant.now()
            )
        }
        startExerciseStopwatch()
    }

    fun setRepsInput(reps: String) {
        _uistate.update { it.copy(repsInput = reps) }
    }

    fun setWeightInput(weight: String) {
        _uistate.update { it.copy(weightInput = weight) }
    }

    fun resetWorkout() {
        stopwatchJob?.cancel()
        restTimerJob?.cancel()
        _uistate.value = WorkoutUiState()
    }

    private fun parseRestTimeToSeconds(restTimeStr: String): Long {
        return when {
            restTimeStr.endsWith("s", ignoreCase = true) -> {
                restTimeStr.dropLast(1).toLongOrNull() ?: 90L
            }
            restTimeStr.toLongOrNull() != null -> {
                val value = restTimeStr.toLong()
                if (value > 1000L) value / 1000L else value
            }
            else -> 90L
        }
    }

    fun formatTime(totalSeconds: Long): String {
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }

    fun formatInMinute(totalMillis: Long): String {
        val totalSeconds = totalMillis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%02dm %02dsec".format(minutes, seconds)
    }
}