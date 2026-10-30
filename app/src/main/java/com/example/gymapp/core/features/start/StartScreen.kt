package com.example.gymapp.core.features.start

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.gymapp.DoneText

import com.example.gymapp.PlayButton
import com.example.gymapp.ProgressBar
import com.example.gymapp.WorkoutBigCard
import com.example.gymapp.core.components.NavigationButton
import com.example.gymapp.core.components.BigCardCarosel

import com.example.gymapp.core.components.TopAppBar
import com.example.gymapp.ui.theme.GymAppTheme
import kotlin.math.absoluteValue
import kotlin.math.max


@Composable
fun StartScreen(
    changeButtonSheet : Boolean,
    modifier: Modifier = Modifier,
    titleBar: String,
    onClick : () -> Unit,
    expandSheet : () -> Unit = {},


    ){

            Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally



                ){
            TopAppBar(text = "Start", modifier = Modifier
                .padding(top = 45.dp, bottom = 96.dp)
                .padding(horizontal = 23.dp)
                .fillMaxWidth())
            BigCardCarosel()



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
                                        .padding(top = 96.dp, bottom = 50.dp)
                                        .padding(horizontal = 53.dp)
                                )
                            } else {
                                PlayButton(
                                    onClick = onClick,
                                    modifier = Modifier
                                        .padding(top = 96.dp, bottom = 50.dp)
                                )
                            }
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
            ) {paddingValues ->
            StartScreen(changeButtonSheet = false, Modifier.padding(paddingValues), titleBar = "Chess Press" ,onClick = {})
        }


    }
}
