package com.example.gymapp.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.gymapp.R
import com.example.gymapp.WorkoutBigCard
import com.example.gymapp.core.model.WorkoutPlan
import kotlin.math.absoluteValue

val WorkoutPlan.bannerImageRes: Int
    get() = when {
        // Cek nama workout pada masing-masing item, bukan list workouts
        nameWorkout.contains("lower", ignoreCase = true) ||
                nameWorkout.contains("leg", ignoreCase = true) -> {
            R.drawable.lower_image
        }
        nameWorkout.contains("upper", ignoreCase = true) ||
                nameWorkout.contains("pull", ignoreCase = true) ||
                nameWorkout.contains("back", ignoreCase = true) -> {
            R.drawable.upper_image
        }
        else -> R.drawable.upper_image
    }
@Composable
fun BigCardCarosel(
    workouts: List<WorkoutPlan>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {



    if (workouts.isEmpty()) return


    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 43.dp,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 86.dp),
        ) { page ->
            val workoutItem = workouts[page]
            WorkoutBigCard(
                workout = workoutItem,
                colors = CardDefaults.cardColors(
                    containerColor = if (pagerState.currentPage == page)
                        MaterialTheme.colorScheme.secondaryContainer
                    else
                        MaterialTheme.colorScheme.surfaceContainer
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                image = painterResource(workoutItem.bannerImageRes),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(337.dp)
                    .graphicsLayer {
                     val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                      val transformation = lerp(
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