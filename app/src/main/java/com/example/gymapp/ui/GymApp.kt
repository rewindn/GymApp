package com.example.gymapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gymapp.NavigationButton
import com.example.gymapp.core.features.start.StartScreen
import com.example.gymapp.core.features.workout.WorkoutScreen
import com.example.gymapp.ui.theme.GymAppTheme

@Preview
@Composable
fun GymApp(){
    GymAppTheme(darkTheme = true){
        Scaffold(
//         bottomBar = { NavigationButton() }
        ) { padding ->

            WorkoutScreen()
//            StartScreen(modifier = Modifier.padding(padding))
        }
    }
}