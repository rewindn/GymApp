package com.example.gymapp.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun TotalActivityTime(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 23.dp).padding(bottom = 129.dp)
    ){
        Text("Activity Time",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
            )
        Text("1h 20m",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun TotalActivityTimePreview(){
    GymAppTheme(darkTheme = true) {
        Surface {
            TotalActivityTime()
        }
    }
}