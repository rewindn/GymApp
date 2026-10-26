package com.example.gymapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gymapp.core.features.workout.WorkoutScreen
import com.example.gymapp.ui.theme.GymAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Preview
@Composable
fun GymApp(){
    val scope : CoroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    GymAppTheme(darkTheme = true){
        Scaffold(
//         bottomBar = { NavigationButton() }
            snackbarHost = { SnackbarHost(
                snackbarHostState,
            ) }
        ) { padding ->

            WorkoutScreen(
                snackbarTrigger = {

                scope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(it)
                }
            },
                modifier = Modifier.padding(padding)
                )
//            StartScreen(modifier = Modifier.padding(padding))
        }
    }
}