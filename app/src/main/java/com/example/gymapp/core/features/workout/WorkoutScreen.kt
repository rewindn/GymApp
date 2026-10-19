package com.example.gymapp.core.features.workout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymapp.core.components.CircularTimer
import com.example.gymapp.core.components.TopAppBar
import com.example.gymapp.core.components.TrackButton
import com.example.gymapp.core.components.WorkoutSetInput
import com.example.gymapp.ui.theme.GymAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen (
    modifier: Modifier = Modifier,
    workoutViewModel: WorkoutViewModel = viewModel()
){
    val workoutUiState by workoutViewModel.uiState.collectAsState()

    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight()
    ){
        BottomSheetDefaults.DragHandle(
            color = Color.Gray,
            modifier = Modifier.padding(top = 28.dp)
        )
        TopAppBar(label = "Playing Now", text = workoutUiState.titleWorkout, modifier =  Modifier.padding( bottom = 51.dp).padding(horizontal = 23.dp) .fillMaxWidth())

        CircularTimer(workoutUiState.timer,workoutUiState.progress)

        Text("SET ${workoutUiState.labelSet}",
            textAlign = TextAlign.Center,
            fontSize = 20.sp
            )
        Text("Last Achivement\n" +
                "${workoutUiState.lastWeight}Kg : ${workoutUiState.lastReps}Reps \n" +
                "in ${workoutUiState.lastTotalTime}",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 26.dp)
            )
        WorkoutSetInput(valueReps = workoutUiState.repsInput, valueWeight = workoutUiState.weightInput ,modifier = Modifier.padding(bottom = 24.dp).padding(horizontal = 42.dp).fillMaxWidth(), onWeightChange = { weight -> workoutViewModel.setWeightInput(weight) },onRepsChange = { reps -> workoutViewModel.setRepsInput(reps) })
        TrackButton("Go To Rest", onclick = { workoutViewModel.setButton() }, modifier = Modifier
            .padding(horizontal = 42.dp)
            .height( 66.dp)
            .fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))

                Icon(
            imageVector = Icons.Filled.Menu,
            tint = Color.White,
            contentDescription = "setting",
            modifier = Modifier.size(24.dp))

        Spacer(modifier = Modifier.height( 28.dp))


    }
}

@Preview
@Composable
fun WorkoutScreenPreview(){
    GymAppTheme(
        darkTheme = true
    ) {
        Surface() {
            WorkoutScreen()
        }
    }
}
