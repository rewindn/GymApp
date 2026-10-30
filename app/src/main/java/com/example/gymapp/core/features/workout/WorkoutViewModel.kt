package com.example.gymapp.core.features.workout

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.core.model.TrackOnSetRecord
import com.example.gymapp.core.model.TrackPlan
import com.example.gymapp.core.model.TrackRecordWithSet
import com.example.gymapp.core.model.WorkoutPlan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant


// Pakai fungsi biar selalu dapet objek baru & jam sekarang pas dipanggil
fun createInitialPlan(): WorkoutPlan {
    return WorkoutPlan(
        nameWorkout = "Upper Body",
        startSession = Instant.now(),
        endSession = null,
        trackList = listOf(
            TrackRecordWithSet(
                session = TrackPlan(
                    nameSet = "Chest Press",
                    totalSet = 3,
                    startSession = Instant.now(),
                    endSession = null
                ),
                sets = emptyList()
            ),
            TrackRecordWithSet(
                session = TrackPlan(
                    nameSet = "Lat Pulldown",
                    totalSet = 3,
                    startSession = Instant.now(),
                    endSession = null
                ),
                sets = emptyList()
            )
        )
    )
}

class WorkoutViewModel : ViewModel(){
    val workoutPlan = createInitialPlan()
    private val _uistate = MutableStateFlow(WorkoutUiState(workoutPlan = workoutPlan))
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    val uiState : StateFlow<WorkoutUiState> = _uistate.asStateFlow()
    val totalTimer = _uistate.value.timer.toInt() / 1000

    var time = 0L

    var stopwatch : Job? = null


    override fun onCleared() {
        coroutineScope.cancel()
    }
    fun setStart() {
      _uistate.update { it.copy(workoutPlan = workoutPlan ,titleWorkout = workoutPlan.trackList[_uistate.value.indexWorkout].session.nameSet ,sessionStart = Instant.now(), weightInput = "", repsInput = "", progress = 1f) }
       stopwatch =  viewModelScope.launch {
           time = 0
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
            _uistate.update { it.copy(titleWorkout = "Chess Press", isResting = false) }

           if (!_uistate.value.isDone) (restButton())
        }
    }

    fun resetWorkout() {
        _uistate.value  = WorkoutUiState(workoutPlan = workoutPlan)
        timer.cancel()

    }
    fun setRepsInput(reps : String) {
        Log.d("Check Input", reps)

        _uistate.update { it.copy(repsInput = reps) }
    }


    fun setWeightInput(weight : String) {

        _uistate.update { it.copy(weightInput = weight) }


    }
    fun setButton() {
        val weight = _uistate.value.weightInput.toDoubleOrNull() ?: 0.0
        val reps = _uistate.value.repsInput.toIntOrNull() ?: 0
        val trackOnSetRecord : TrackOnSetRecord = TrackOnSetRecord(startedAt = _uistate.value.sessionStart, weight = weight, reps = reps, endedAt = Instant.now(), setNumber =  _uistate.value.labelSet.toInt() )



        stopwatch?.cancel()
        _uistate.update { it.copy( isResting = true, labelSet = it.labelSet + 1, lastReps = trackOnSetRecord.reps, lastWeight = trackOnSetRecord.weight?.toInt() , lastTotalTime = formatInMinute( (trackOnSetRecord.endedAt?.toEpochMilli() ?: 0) - trackOnSetRecord.startedAt.toEpochMilli()), completeSets = it.completeSets +  trackOnSetRecord ) }
        Log.d("Test On Set",trackOnSetRecord.toString())

        if (workoutPlan.trackList[_uistate.value.indexWorkout].session.totalSet < _uistate.value.labelSet) {
            val trackPlan : TrackPlan = TrackPlan(
                startSession = _uistate.value.trackStartedAt ,
                totalSet = _uistate.value.workoutPlan.trackList[_uistate.value.indexWorkout].session.totalSet,
                nameSet = _uistate.value.workoutPlan.trackList[_uistate.value.indexWorkout].session.nameSet ,
                endSession = Instant.now())
            val trackRecordWithSet : TrackRecordWithSet = TrackRecordWithSet(
                session = trackPlan,
                sets = _uistate.value.completeSets,

            )


            val indexWorkout = _uistate.value.indexWorkout + 1
            _uistate.update {it.copy(completeSets = emptyList() ,trackStartedAt = Instant.now() ,completeTrackList = it.completeTrackList + trackRecordWithSet, indexWorkout = indexWorkout, labelSet = 1)}



            Log.d("hasil akhir per track",trackRecordWithSet.toString())
        }

        if (_uistate.value.workoutPlan.trackList.size <= _uistate.value.indexWorkout) {
            val completeWorkout = WorkoutPlan(nameWorkout = workoutPlan.nameWorkout,startSession = _uistate.value.sessionStartedAt,  endSession = Instant.now(),trackList = _uistate.value.completeTrackList)
            _uistate.update { it.copy(workoutPlan = completeWorkout, isDone = true) }
            Log.d("hasil akhir per track",completeWorkout.toString())

            this.timer.cancel()
        }

            restStart()

    }

    fun restButton(){
        timer.cancel()
        _uistate.update { it.copy(titleWorkout = it.workoutPlan.trackList[it.indexWorkout].session.nameSet, isResting = false) }
        setStart()
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


}