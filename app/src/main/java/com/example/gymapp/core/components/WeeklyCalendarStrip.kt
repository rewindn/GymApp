package com.example.gymapp.core.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.ui.theme.GymAppTheme
import java.time.LocalDate

@Composable
fun WeeklyCalendarStrip(
    weekDates: List<LocalDate>,
    selectedDate: LocalDate,
    workoutDates: Set<LocalDate>,
    modifier: Modifier = Modifier,
    onDateClick: (LocalDate) -> Unit = {}
) {
    val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            daysOfWeek.forEach { dayName ->
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text(
                        text = dayName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(11.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF2C2D31))
                .padding(horizontal = 6.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            weekDates.forEach { date ->
                val hasWorkout = workoutDates.contains(date)
                val isSelected = date == selectedDate

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (hasWorkout) NeonLime else Color.Transparent)

                            .then(
                                if (isSelected) Modifier.border(2.dp, Color.White, RoundedCornerShape(16.dp))
                                else Modifier
                            )
                            .clickable { onDateClick(date) }
                    ) {
                        Text(
                            text = date.dayOfMonth.toString(),
                            fontSize = 14.sp,
                            fontWeight = if (hasWorkout) FontWeight.Bold else FontWeight.Medium,
                            color = if (hasWorkout) Color.Black else Color(0xFF8E8E93)
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Weekly Strip Responsive", showBackground = true, backgroundColor = 0xFF1E1F22)
@Composable
fun WeeklyCalendarStripPreview() {
    GymAppTheme(darkTheme = true) {
        Surface(color = Color(0xFF1E1F22), modifier = Modifier.padding(23.dp)) {

        }
    }
}