package com.example.gymapp.core.features.workout

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.gymapp.core.model.TrackOnSetRecord
import com.example.gymapp.core.model.TrackRecord
import com.example.gymapp.core.model.TrackRecordWithSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant


class WorkoutViewModel : ViewModel(){
    private val _uistate = MutableStateFlow(WorkoutUiState())
    val uiState : StateFlow<WorkoutUiState> = _uistate.asStateFlow()
    val trackRecord : TrackRecord = TrackRecord(endSession = null)
    val trackOnSetRecord : TrackOnSetRecord = TrackOnSetRecord(endSession = null)


    val totalTimer = _uistate.value.timer.toInt() / 1000
    val timer  = object : CountDownTimer (_uistate.value.timer.toLong(),1000){
        override fun onTick(p0: Long) {
            val totalSeconds = p0 /1000
            val progressPresentage : Double =  totalSeconds.toDouble() / totalTimer
            _uistate.update {current -> current.copy(timer = formatTime(totalSeconds), progress = progressPresentage.toFloat())}


        }

        override fun onFinish() {
            _uistate.value = WorkoutUiState("00:00")
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
        val set : TrackOnSetRecord = TrackOnSetRecord(weight = _uistate.value.weightInput.toInt(), reps = _uistate.value.repsInput.toInt(), endSession = Instant.now(), setNumber =  _uistate.value.labelSet)
        val trackRecordWithSet : TrackRecordWithSet = TrackRecordWithSet(
            nameSet = trackRecord,
            sets = listOf(set)
            )

        Log.d("Test On Set",trackRecordWithSet.toString())
    }

    fun formatTime(totalSeconds: Long): String {
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }


    init {
    timer.start()
    }
}