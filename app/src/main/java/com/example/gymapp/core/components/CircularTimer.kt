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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.ui.theme.GymAppTheme

@Composable
fun CircularTimer(timer : String, progress : Float) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(bottom = 37.dp)
    ) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.size(269.dp),
            strokeWidth = 18.5.dp

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
            CircularTimer("00:00",0.5f)
        }
    }
}