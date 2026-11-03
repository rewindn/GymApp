package com.example.gymapp.core.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.ui.theme.GymAppTheme


val NeonLime = Color(0xFF6EEB10)

@Composable
fun CalendarDayCell(
    dayText: String,
    hasWorkout: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(36.dp) // Sesuai spek Figma: W 36 x H 36
            .clip(RoundedCornerShape(16.dp)) // Border radius 16.dp
            .background(if (hasWorkout) NeonLime else Color.Transparent)
            .clickable(enabled = dayText.isNotBlank(), onClick = onClick)
    ) {
        if (dayText.isNotBlank()) {
            Text(
                text = dayText,
                fontSize = 14.sp,
                fontWeight = if (hasWorkout) FontWeight.Bold else FontWeight.Medium,
                color = if (hasWorkout) Color.Black else Color(0xFF8E8E93)
            )
        }
    }
}

@Preview(name = "Calendar Day Cell", showBackground = true, backgroundColor = 0xFF1E1F22)
@Composable
fun CalendarDayCellPreview() {
    GymAppTheme(darkTheme = true) {
        Surface(color = Color(0xFF1E1F22)) {
            CalendarDayCell(
                dayText = "23",
                hasWorkout = true
            )
        }
    }
}