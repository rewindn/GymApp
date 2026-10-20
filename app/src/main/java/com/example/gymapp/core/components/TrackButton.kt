package com.example.gymapp.core.components

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.ui.theme.GymAppTheme


@Composable
fun TrackButton(color: ButtonColors, title : String, modifier: Modifier = Modifier, onclick : () -> Unit = {}){
    Button(
        onClick = onclick,
        modifier = modifier,
        colors = color

    ){
        Text(title,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,

            color = MaterialTheme.colorScheme.primaryContainer
        )
    }
}

@Preview
@Composable
fun TrackButtonPreview(){
    GymAppTheme(darkTheme = true) {
        Surface {
//            TrackButton(title = "Finish The Session")
        }
    }
}