package com.example.gymapp.core.features.workout

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.core.model.TrackOnSetRecord
import com.example.gymapp.core.model.TrackRecord
import com.example.gymapp.core.model.TrackRecordWithSet
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant


class WorkoutViewModel : ViewModel(){
    private val _uistate = MutableStateFlow(WorkoutUiState())
    val uiState : StateFlow<WorkoutUiState> = _uistate.asStateFlow()
    val trackRecord : TrackRecord = TrackRecord(endSession = null)
    val trackOnSetRecord : TrackOnSetRecord = TrackOnSetRecord(endedAt = null)


    val totalTimer = _uistate.value.timer.toInt() / 1000

    var time = 0L

    var stopwatch : Job? = null
    fun setStart() {
      _uistate.update { it.copy(sessionStart = Instant.now()) }
       stopwatch =  viewModelScope.launch {
            while (!_uistate.value.isResting){
                Log.d("StopWatch ", "$time")
                _uistate.update {it.copy(timer = formatTime(time))}
                delay(1000L)
                time++
            }
        }

    }

    fun restStart(){
        timer.start()
        _uistate.update { it.copy(titleWorkout = "Rest",) }
    }

    val timer  = object : CountDownTimer (_uistate.value.timer.toLong(),1000){
        override fun onTick(p0: Long) {
            val totalSeconds = p0 /1000
            val progressPresentage : Double =  totalSeconds.toDouble() / totalTimer
            _uistate.update {current -> current.copy(timer = formatTime(totalSeconds), progress = progressPresentage.toFloat())}


        }


        override fun onFinish() {
            _uistate.update { it.copy(isResting = false) }
        }
    }


    fun setRepsInput(reps : String) {
        Log.d("Check Input", reps)
        _uistate.update { it.copy(repsInput = reps) }
    }


    fun setWeightInput(weight : String) {

        _uistate.update { it.copy(weightInput = weight) }


    }
    fun setButton(){
        val set : TrackOnSetRecord = TrackOnSetRecord(startedAt = _uistate.value.sessionStart, weight = _uistate.value.weightInput.toDouble(), reps = _uistate.value.repsInput.toInt(), endedAt = Instant.now(), setNumber =  _uistate.value.labelSet.toIntOrNull())

        val trackRecordWithSet : TrackRecordWithSet = TrackRecordWithSet(
            session = trackRecord,
            sets = listOf(set)
            )

        stopwatch?.cancel()
        _uistate.update { it.copy(isResting = true, labelSet = "Last", lastReps = set.reps?.toInt() , lastWeight = set.weight?.toInt() , lastTotalTime = formatInMinute( (set.endedAt?.toEpochMilli() ?: 0) - set.startedAt.toEpochMilli())) }
        Log.d("Test On Set",trackRecordWithSet.toString())

        restStart()
    }

    fun formatTime(totalSeconds: Long): String {
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }

    fun formatInMinute(totalSeconds: Long): String {
        val minutes = totalSeconds /1000 / 60
        val seconds = totalSeconds /1000 % 60
        return "%02dm %02dsec".format(minutes, seconds)
    }

    init {
    setStart()
    }
}