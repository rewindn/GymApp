package com.example.gymapp.core.features.workout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymapp.core.components.CircularTimer
import com.example.gymapp.core.components.TopAppBar
import com.example.gymapp.core.components.TrackButton
import com.example.gymapp.core.components.WorkoutSetInput
import com.example.gymapp.ui.theme.GymAppTheme



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WorkoutScreen(
    modifier: Modifier = Modifier,
    workoutViewModel: WorkoutViewModel = viewModel(),
    snackbarTrigger: (String) -> Unit = {}
){
    val workoutUiState by workoutViewModel.uiState.collectAsStateWithLifecycle()
    val iskeyboardOpen = WindowInsets.isImeVisible
    val colorChangeCircle : Color by animateColorAsState(
        if (workoutUiState.isResting){
            Color.Green
        }else{
            MaterialTheme.colorScheme.primary}
    )
    val colorChangeButton : Color by animateColorAsState(
        if (workoutUiState.isResting){
            Color.White
        }else{
            MaterialTheme.colorScheme.primary}
    )


    WorkoutScreen(
        iskeyboardOpen = iskeyboardOpen,
        workoutUiState = workoutUiState,
        verticalArrangement = Arrangement.spacedBy(if (!iskeyboardOpen) 0.dp else 12.dp),
        colorChangeCircle = colorChangeCircle,
        colorChangeButton = colorChangeButton,
        circleSize = if (!iskeyboardOpen) Modifier.size(269.dp) else Modifier.size(223.dp),
        labelSet = if (workoutUiState.isResting) "LAST SET" else "SET ${workoutUiState.labelSet}",
        labelAchivement = if (workoutUiState.isResting) { "Previous Achivement\n" } else { "Last Achivement\n" } +
                "${workoutUiState.lastWeight}Kg : ${workoutUiState.lastReps}Reps \n" +
                "in ${workoutUiState.lastTotalTime}",
        labelButton =  if (workoutUiState.isResting) "Im Good Now" else "Go To Rest",
        buttonOnClick = { if (workoutUiState.repsInput.isEmpty() || workoutUiState.repsInput.toIntOrNull() == null|| workoutUiState.weightInput.toIntOrNull() == null || workoutUiState.weightInput.isEmpty()){snackbarTrigger("Reps Dan Weight Kosong Atau Salah Format")}else{ if (workoutUiState.isResting) workoutViewModel.restButton()else workoutViewModel.setButton() } },
        onWeightChange = workoutViewModel::setWeightInput,
        onRepsChange =  workoutViewModel::setRepsInput,
        modifier = modifier,

    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun WorkoutScreen (

    modifier: Modifier = Modifier,
    iskeyboardOpen: Boolean,
    verticalArrangement: Arrangement.Vertical,
    colorChangeButton : Color,
    colorChangeCircle : Color,
    circleSize : Modifier = Modifier.size(269.dp),
    labelSet: String,
    labelAchivement: String,
    labelButton: String,
    workoutUiState: WorkoutUiState,
    buttonOnClick : () -> Unit,
    onWeightChange: (String) -> Unit,
    onRepsChange: (String) -> Unit,

    ){

    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = verticalArrangement,
        modifier = modifier
    ){
        BottomSheetDefaults.DragHandle(
            color = Color.Gray,

        )


        AnimatedVisibility(visible = !iskeyboardOpen) {
            TopAppBar(label = "Playing Now", text = workoutUiState.titleWorkout, modifier =  Modifier
                .padding(bottom = 48.dp)
                .padding(horizontal = 23.dp)
                .fillMaxWidth())
        }
        AnimatedVisibility(visible = iskeyboardOpen) {
            Box(Modifier.size(71.dp))
        }


        CircularTimer(
            modifier = circleSize,
            color = colorChangeCircle,
            timer = workoutUiState.timer, progress = workoutUiState.progress,
        )
        Text(labelSet,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 26.dp)

        )
        AnimatedVisibility(visible = !iskeyboardOpen) {

            Text(labelAchivement,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 26.dp),
                )
        }

        AnimatedVisibility(visible = !workoutUiState.isResting) {
            WorkoutSetInput(valueReps = workoutUiState.repsInput, valueWeight = workoutUiState.weightInput ,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .padding(horizontal = 42.dp)
                    .fillMaxWidth(),
                onWeightChange = onWeightChange,
                onRepsChange = onRepsChange
            )
        }
        TrackButton(title = labelButton, onclick = buttonOnClick, modifier = Modifier
            .padding(horizontal = 42.dp)
            .height(66.dp)
            .fillMaxWidth(),
            color = ButtonColors(
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = colorChangeButton,
                disabledContainerColor = MaterialTheme.colorScheme.secondary,
                disabledContentColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        )
        Spacer(modifier = Modifier.weight(1f))
         Icon(
            imageVector = Icons.Filled.Menu,
            tint = Color.White,
            contentDescription = "setting",
            modifier = Modifier.size(24.dp))

        Spacer(modifier = Modifier.size(12.dp))

    }
}

@Preview
@Composable
fun WorkoutScreenPreview(){
    GymAppTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        Surface {
            WorkoutScreen()
        }
    }
}


