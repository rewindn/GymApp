package com.example.gymapp.core.features.done

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.gymapp.core.components.EndSessionTime

import com.example.gymapp.core.components.TopAppBar
import com.example.gymapp.core.components.TotalActivityTime
import com.example.gymapp.core.components.TrackButton
import com.example.gymapp.ui.theme.GymAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoneScreen(modifier: Modifier = Modifier){
    Column(
    horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight()
    ) {
        BottomSheetDefaults.DragHandle(
            color = Color.Gray,
            modifier = Modifier.padding(top = 28.dp)
        )
        TopAppBar(icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft, modifier = Modifier.fillMaxWidth().padding(horizontal = 23.dp).padding(bottom = 108.dp))
        Text("Done",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(bottom = 108.dp)
            )
        EndSessionTime(title = "Upper Body", date = "February", time = "16:20 - 18:20")
        TotalActivityTime()
        TrackButton(color = ButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondary,
            disabledContentColor = MaterialTheme.colorScheme.secondaryContainer,
        ),"Finish The Session", modifier = Modifier
            .padding(horizontal = 42.dp)
            .height( 66.dp)
            .fillMaxWidth())
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Filled.Menu,
            tint = Color.White,
            contentDescription = "setting",
            modifier = Modifier.size(24.dp))

        Spacer(modifier = Modifier.height( 28.dp))

    }
}


@Preview
@Composable
fun DoneScreenPreview(){
    GymAppTheme(darkTheme = true) {
        Surface{
            DoneScreen()
        }
    }
}
