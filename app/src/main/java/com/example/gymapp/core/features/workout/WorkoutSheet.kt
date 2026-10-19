package com.example.gymapp.core.features.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gymapp.core.components.WorkoutQueueItem
import com.example.gymapp.core.features.done.DoneScreen
import com.example.gymapp.ui.GymApp
import com.example.gymapp.ui.theme.GymAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutSheet(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 23.dp).padding(top = 28.dp)
        ){

            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                tint = Color.White,
                contentDescription = "bottom of button for exit the queue list",
                modifier = Modifier.size(24.dp)
            )

            BottomSheetDefaults.DragHandle(
                color = Color.Gray,
            )
            Spacer(modifier = Modifier.size(24.dp))
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                WorkoutQueueItem(title = "Butter Fly", desc = "Upper Body") //[cite: 13]
                WorkoutQueueItem(title = "Back Fly", desc = "Upper Body") //[cite: 13]
                WorkoutQueueItem(title = "Lat Pulldown", desc = "Upper Body") //[cite: 13]
                WorkoutQueueItem(title = "Rowing Cable", desc = "Upper Body") //[cite: 13]
                WorkoutQueueItem(title = "Side Raises Cable", desc = "Upper Body") //[cite: 13]
                WorkoutQueueItem(title = "Overhead Extension", desc = "Upper Body") //[cite: 13]
                WorkoutQueueItem(title = "Bicep Curl", desc = "Upper Body") //[cite: 13]
            }


        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowDown, //[cite: 14]
            contentDescription = "Tutup",
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.height(79.dp))
    }
}

@Preview
@Composable
fun WorkoutSheetPreview(){
    GymAppTheme(darkTheme = true) {
        Surface {
            DoneScreen()
        }
    }
}

@Preview
@Composable
fun done(){
    GymAppTheme(darkTheme = true) {
        Surface {
            WorkoutSheet()
        }
    }
}

@Preview
@Composable
fun workout(){
    GymAppTheme(darkTheme = false  ) {
        Surface {
            WorkoutScreen()
        }
    }
}

@Preview
@Composable
fun gym(){
    GymAppTheme(darkTheme = false) {
        Surface {
            GymApp()
        }
    }
}