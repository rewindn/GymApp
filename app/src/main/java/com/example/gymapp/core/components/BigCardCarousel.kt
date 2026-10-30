package com.example.gymapp.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.gymapp.WorkoutBigCard
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BigCardCarosel(){

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
                    .height(337.dp)
                    .graphicsLayer {
                        val pageOffset =
                            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                        val transformation =
                            lerp(
                                start = 0.9f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        alpha = transformation
                        scaleY = transformation
                    }
            )
        }
    }
}
