package com.example.gymapp.core.components


import android.R
import androidx.compose.animation.core.RepeatableSpec
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.ui.theme.GymAppTheme


@Composable
fun WorkoutSetInput(valueReps : String ,valueWeight : String,modifier: Modifier = Modifier, onRepsChange :(String) -> Unit = {}, onWeightChange :(String) -> Unit = {}){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ){
        TrackInput(imeAction = ImeAction.Next, value = valueReps,text = "Reps", modifier = Modifier.width(96.dp), setinput = { reps -> onRepsChange(reps)})
        Text("x",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(42.dp)
        )
        TrackInput(imeAction = ImeAction.Done, value = valueWeight, text = "Weight", modifier = Modifier.width( 96.dp), setinput = { weight -> onWeightChange(weight)})
        Text("Kg",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(42.dp)
        )

    }
}

@Preview
@Composable
fun WorkoutSetInputPreview(){
    GymAppTheme(darkTheme = false) {

        Surface {
            WorkoutSetInput("","")
        }

    }
}