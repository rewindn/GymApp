package com.example.gymapp.core.features.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gymapp.DoneText

import com.example.gymapp.PlayButton
import com.example.gymapp.WorkoutBigCard
import com.example.gymapp.core.components.NavigationButton
import com.example.gymapp.core.components.TopAppBar
import com.example.gymapp.ui.theme.GymAppTheme


@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onClick : () -> Unit

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
                PlayButton(onClick = onClick)

        }
    }




@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BigCardCarosel(){

    var pagerState = rememberPagerState(pageCount = {10})

    Box(
        contentAlignment = Alignment.Center,
        ){

        HorizontalPager(
            state = pagerState,
            pageSpacing = 43.dp,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 86.dp)

            )  {page ->
            WorkoutBigCard(
                modifier = Modifier
                    .fillMaxWidth()
            )
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
            StartScreen(Modifier.padding(paddingValues), onClick = {})
        }


    }
}
