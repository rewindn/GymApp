package com.example.gymapp.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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

@Composable
fun CircularTimer(color: Color = Color.Cyan, timer : String, progress : Float, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(bottom = 37.dp)
    ) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = modifier,
            strokeWidth = 18.5.dp,
            color = color

        )
        Text(
            timer,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp
        )
    }
}

@Preview
@Composable
fun CircularTimerPreview(){
    GymAppTheme(darkTheme = true) {
        Surface {
            CircularTimer(timer ="00:00", progress =  0.5f, modifier =  Modifier.size(269.dp))
        }
    }
}