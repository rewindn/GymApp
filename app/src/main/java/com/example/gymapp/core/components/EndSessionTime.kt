package com.example.gymapp.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.ui.theme.GymAppTheme

@Composable
fun EndSessionTime(title: String,date: String,time: String){
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 23.dp).padding(bottom = 40.dp)
    ) {
        Text(
            title,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            "$date $time",
            fontSize = 24.sp,
            fontWeight = FontWeight.Light
        )
    }

}


@Preview
@Composable
fun EndSessionTitlePreview(){
    GymAppTheme(darkTheme = true) {
        Surface() {
            EndSessionTime(title = "Upper Body", date = "February", time = "16:20 - 18:20")
        }
    }
}

