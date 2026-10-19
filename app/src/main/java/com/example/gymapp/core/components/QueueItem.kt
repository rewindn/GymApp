package com.example.gymapp.core.components

import android.media.session.MediaSession
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.R
import com.example.gymapp.ui.theme.GymAppTheme
import java.util.Queue

@Composable
fun WorkoutQueueItem(title : String, desc : String){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 23.dp, vertical = 8.dp)

    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.wo_img_upper),
                contentDescription = "Workout Image",
                modifier = Modifier
                    .size(54.dp)
                    .padding(end = 16.dp)
            )
            Column {
                Text(
                    title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    desc,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
        }



        }

        Icon(
            imageVector = Icons.Filled.Menu,
            tint = Color.White,
            contentDescription = "toggle queue",
            modifier = Modifier.size(24.dp)

        )
    }

}

@Preview
@Composable
fun WorkoutQueueItemPreview(){
    GymAppTheme(darkTheme = true) {
        Surface {
            WorkoutQueueItem(title = "Chest Press", desc = "Upper Body")
        }
    }
}
