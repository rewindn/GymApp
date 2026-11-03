package com.example.gymapp.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.ui.theme.GymAppTheme

data class TodayWorkoutUiModel(
    val id: String,
    val title: String,
    val category: String,
    val setCount: String,
    val sets: List<WorkoutSetDetail> = emptyList()
)

@Composable
fun TodayWorkoutSection(
    title: String,
    items: List<TodayWorkoutUiModel>,
    modifier: Modifier = Modifier,
    totalDuration: String = "1h 20m"
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = totalDuration,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }


        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items.filter { item ->
                    item.setCount != "0 Sets" &&
                    !item.setCount.startsWith("0") &&
                            item.sets.isNotEmpty()
        }
            .forEach { item ->
                TodayWorkoutItem(
                    title = item.title,
                    category = item.category,
                    weight = item.setCount,
                    sets = item.sets
                )
            }
        }
    }
}

@Preview(name = "Today Workout List Section", showBackground = true, backgroundColor = 0xFF1E1F22)
@Composable
fun TodayWorkoutSectionPreview() {
    val sampleList = listOf(
        TodayWorkoutUiModel("1", "Chest Press", "Upper Body", "20kg", ),
        TodayWorkoutUiModel("2", "Chest Press", "Upper Body", "20kg", ),
        TodayWorkoutUiModel("3", "Chest Press", "Upper Body", "20kg", ),
        TodayWorkoutUiModel("4", "Chest Press", "Upper Body", "20kg", )
    )

    GymAppTheme(darkTheme = true) {
        Surface(color = Color(0xFF1E1F22), modifier = Modifier.padding(24.dp)) {
            TodayWorkoutSection(
                items = sampleList,
                title = "Today Workout",
                totalDuration = "1h 20m"
            )
        }
    }
}