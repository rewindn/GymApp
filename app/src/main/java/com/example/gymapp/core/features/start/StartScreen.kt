package com.example.gymapp.core.features.start

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymapp.PlayButton
import com.example.gymapp.ProgressBar

import com.example.gymapp.core.components.BigCardCarosel
import com.example.gymapp.core.components.NavigationButton
import com.example.gymapp.core.components.TopAppBar
import com.example.gymapp.core.model.WorkoutPlan
import com.example.gymapp.features.start.StartViewModel

import com.example.gymapp.ui.theme.GymAppTheme


@Composable
fun StartScreen(
    changeButtonSheet: Boolean,
    titleBar: String,
    expandSheet: () -> Unit,
    onClick: (WorkoutPlan) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StartViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { state.plans.size })
    if (state.plans.isNotEmpty()){
        val selectedPlan = state.plans[pagerState.currentPage]

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                text = "Start",
                onExitClick = {},
                modifier = Modifier
                    .padding(top = 45.dp, bottom = 96.dp)
                    .padding(horizontal = 23.dp)
                    .fillMaxWidth()
            )

            BigCardCarosel(
                workouts = state.plans,
                pagerState = pagerState
            )

            AnimatedContent(
                targetState = changeButtonSheet,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "PlayButtonProgressBarTransition"
            ) { isProgress ->
                if (isProgress) {
                    ProgressBar(
                        title = titleBar,
                        onClick = expandSheet,
                        modifier = Modifier
                            .padding(top = 80.dp)
                            .padding(horizontal = 53.dp)
                    )
                } else {
                    PlayButton(
                        onClick = {
                            if (changeButtonSheet) {
                                expandSheet()
                            } else {
                                onClick(selectedPlan)
                            }

                        },
                        modifier = Modifier
                            .padding(top = 80.dp)
                            .padding(horizontal = 53.dp)
                    )
                }
            }
        }
    }else{
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }


    }

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    GymAppTheme(darkTheme = true) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { NavigationButton() }
        ) { paddingValues ->
            StartScreen(
                changeButtonSheet = false,
                modifier = Modifier.padding(paddingValues),
                titleBar = "Chess Press",
                expandSheet = {},
                onClick = {}
            )
        }
    }
}