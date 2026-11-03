package com.example.gymapp.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymapp.PlayButton
import com.example.gymapp.ProgressBar
import com.example.gymapp.core.components.NavigationButton
import com.example.gymapp.core.features.start.StartScreen
import com.example.gymapp.core.features.statistic.StatisticScreen
import com.example.gymapp.core.features.workout.WorkoutScreen
import com.example.gymapp.core.features.workout.WorkoutViewModel
import kotlinx.coroutines.launch


enum class AppScreen {
    STATISTIC,
    START,
    EDIT
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun GymApp() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val workoutViewModel: WorkoutViewModel = viewModel()
    val workoutUiState by workoutViewModel.uiState.collectAsStateWithLifecycle()


    var currentScreen by remember { mutableStateOf(AppScreen.START) }
    val screens = AppScreen.entries
    val mainPagerState = rememberPagerState(
        initialPage = AppScreen.START.ordinal,
        pageCount = {
            screens.size
        }
    )

    var showWorkoutSheet by remember { mutableStateOf(false) }
    var changeButtonSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(

        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())   )
        {
            HorizontalPager(
                state = mainPagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (screens[page]) {
                    AppScreen.START -> {
                        StartScreen(
                            changeButtonSheet = changeButtonSheet,
                            titleBar = workoutUiState.titleWorkout,
                            expandSheet = { showWorkoutSheet = true },
                            onClick = { selectedPlan ->
                                workoutViewModel.setStart(selectedPlan)
                                showWorkoutSheet = true
                            }
                        )
                    }

                    AppScreen.STATISTIC -> {
                        StatisticScreen(
                            onSettingsClick = { /* Settings */ }
                        )
                    }

                    AppScreen.EDIT -> { /* Edit */ }
                }
            }






            NavigationButton(
            currentScreen = screens[mainPagerState.currentPage],
                onNavigate = { targetScreen ->
                    scope.launch {
                   mainPagerState.animateScrollToPage(targetScreen.ordinal)
                    }
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }


        if (showWorkoutSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showWorkoutSheet = false
                if (workoutUiState.workoutPlan.trackList.isNotEmpty()) {
                        changeButtonSheet = true
                    }
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