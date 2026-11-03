package com.example.gymapp.core.features.workout

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.core.database.AppDatabase
import com.example.gymapp.core.database.WorkoutRepository
import com.example.gymapp.core.model.TrackOnSetRecord
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
    private val repository = WorkoutRepository(database.workoutDao(), application, database)

    private val _uistate = MutableStateFlow(WorkoutUiState())
    val uiState: StateFlow<WorkoutUiState> = _uistate.asStateFlow()

    private var stopwatchJob: Job? = null
    private var restTimerJob: Job? = null
    private var elapsedSeconds = 0L

    fun removeTrackItem(index: Int) {
        _uistate.update { state ->
            if (index !in state.activeTrackList.indices) return@update state

            val updatedQueue = state.activeTrackList.filterIndexed { i, _ -> i != index }
            val newIndex = when {
                index < state.indexWorkout -> (state.indexWorkout - 1).coerceAtLeast(0)
                else -> state.indexWorkout
            }
            val currentExerciseName = updatedQueue.getOrNull(newIndex)?.session?.nameSet ?: state.titleWorkout

            state.copy(
                activeTrackList = updatedQueue,
                workoutPlan = state.workoutPlan.copy(trackList = updatedQueue), // Sinkronkan ke UI lama
                indexWorkout = newIndex,
                titleWorkout = if (state.isResting) state.titleWorkout else currentExerciseName
            )
        }
    }

    fun zeroTrackSets(index: Int) {
        removeTrackItem(index)
    }

    fun setStart(selectedPlan: WorkoutPlan) {
        val firstExerciseName = selectedPlan.trackList.firstOrNull()?.session?.nameSet ?: selectedPlan.nameWorkout

        _uistate.update {
            it.copy(
                masterPlan = selectedPlan,
                workoutPlan = selectedPlan,
                activeTrackList = selectedPlan.trackList,
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

    fun reorderTrackList(fromIndex: Int, toIndex: Int) {
        _uistate.update { state ->
            val list = state.activeTrackList
            if (fromIndex !in list.indices || toIndex !in list.indices) return@update state

            val updatedList = list.toMutableList().apply {
                add(toIndex, removeAt(fromIndex))
            }

            val newIndex = when {
                state.indexWorkout == fromIndex -> toIndex
                fromIndex < state.indexWorkout && toIndex >= state.indexWorkout -> state.indexWorkout - 1
                fromIndex > state.indexWorkout && toIndex <= state.indexWorkout -> state.indexWorkout + 1
                else -> state.indexWorkout
            }

            val activeExerciseName = updatedList.getOrNull(newIndex)?.session?.nameSet ?: state.titleWorkout

            state.copy(
                activeTrackList = updatedList,
                workoutPlan = state.workoutPlan.copy(trackList = updatedList),
                indexWorkout = newIndex,
                titleWorkout = if (state.isResting) state.titleWorkout else activeExerciseName
            )
        }
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
        val currentTrack = currentState.activeTrackList.getOrNull(currentState.indexWorkout)
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

      if (updatedSets.size >= targetTotalSets) {
            val isLastExercise = currentState.indexWorkout + 1 >= currentState.activeTrackList.size
            if (isLastExercise) {
                setData(isDone = true)
                return
            } else {
                setData(isDone = false)
                startRestTimer()
                return
            }
        }

        startRestTimer()
    }

    fun setData(isDone: Boolean = false) {
        val currentState = _uistate.value
        val currentTrack = currentState.activeTrackList.getOrNull(currentState.indexWorkout)

        if (isDone) {
            stopwatchJob?.cancel()
            restTimerJob?.cancel()

            val completedTracks = currentState.completeTrackList.toMutableList()

            if (currentTrack != null && currentState.completeSets.isNotEmpty()) {
                completedTracks.add(
                    TrackRecordWithSet(
                        session = currentTrack.session.copy(
                            startSession = currentState.trackStartedAt,
                            endSession = Instant.now()
                        ),
                        sets = currentState.completeSets
                    )
                )
            }

            val completedMap = completedTracks.associateBy { it.session.nameSet }
            val persistentTrackList = currentState.masterPlan.trackList.map { templateItem ->
                completedMap[templateItem.session.nameSet] ?: templateItem.copy(sets = emptyList())
            }

            val finalWorkoutPlan = currentState.masterPlan.copy(
                startSession = currentState.sessionStartedAt,
                endSession = Instant.now(),
                trackList = persistentTrackList
            )

           _uistate.update {
                it.copy(
                    masterPlan = finalWorkoutPlan,
                    workoutPlan = finalWorkoutPlan,
                    completeTrackList = persistentTrackList,
                    isResting = false,
                    isDone = true
                )
            }

            Log.d("WorkoutViewModel", "Workout selesai disimpan: ${persistentTrackList.size} gerakan master aman.")
            viewModelScope.launch(Dispatchers.IO) {
                repository.saveWorkoutPlan(finalWorkoutPlan)
            }
        } else {
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

            val nextIndex = currentState.indexWorkout + 1
            val nextExerciseName = currentState.activeTrackList.getOrNull(nextIndex)?.session?.nameSet ?: "Workout"

            _uistate.update {
                it.copy(
                    completeSets = emptyList(),
                    completeTrackList = updatedTrackList,
                    workoutPlan = it.workoutPlan.copy(trackList = it.activeTrackList),
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
        val restTimeSeconds = parseRestTimeToSeconds(_uistate.value.masterPlan.restTime)

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
        _uistate.update { it.copy(weightInput = "", repsInput = "") }
        restTimerJob?.cancel()
        onFinishRest()
    }

    private fun onFinishRest() {
        val currentState = _uistate.value
        if (currentState.isDone) return

        val currentExerciseName = currentState.activeTrackList
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