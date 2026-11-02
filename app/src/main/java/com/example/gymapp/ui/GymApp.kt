package com.example.gymapp.ui

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymapp.core.components.NavigationButton
import com.example.gymapp.core.features.start.StartScreen

import com.example.gymapp.core.features.workout.WorkoutScreen
import com.example.gymapp.core.features.workout.WorkoutViewModel

import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun GymApp() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val workoutViewModel: WorkoutViewModel = viewModel()
    val workoutUiState by workoutViewModel.uiState.collectAsStateWithLifecycle()

    var showWorkoutSheet by remember { mutableStateOf(false) }
    var changeButtonSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        bottomBar = { NavigationButton() },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp)
            )
        }
    ) { padding ->
        StartScreen(
            changeButtonSheet = changeButtonSheet,
            modifier = Modifier.padding(padding),
            titleBar = workoutUiState.titleWorkout,
            expandSheet = {
               showWorkoutSheet = true
            },
            onClick = { selectedPlan ->
               workoutViewModel.setStart(selectedPlan)
                showWorkoutSheet = true
            }
        )

        if (showWorkoutSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showWorkoutSheet = false
                    changeButtonSheet = true // Mengubah tombol jadi "Resume" saat di-minimize
                },
                sheetState = sheetState,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                dragHandle = null
            ) {
                WorkoutScreen(
                    workoutViewModel = workoutViewModel,
                    snackbarTrigger = { pesan ->
                        scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(pesan)
                        }
                    },
                    doneClick = {
                        showWorkoutSheet = false
                        changeButtonSheet = false
                        workoutViewModel.resetWorkout()
                    }
                )
            }
        }
    }
}