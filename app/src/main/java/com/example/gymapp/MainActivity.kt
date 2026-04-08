package com.example.gymapp


import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.ui.theme.GymAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GymAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {

                }
            }
        }
    }
}

@Composable
fun DoneText( modifier: Modifier = Modifier) {
    Text(
        text = "Done",
        fontSize = 40.sp,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationButton(){

    OutlinedButton(
        onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
        border = BorderStroke(6.dp, Color.Gray)



    ) {
        Icon(
            imageVector = Icons.Outlined.Info,
            tint = Color.White,
            contentDescription = "Info",
            modifier = Modifier.size(24.dp)
        )
            Spacer(Modifier.size(53.dp))
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            tint = Color.White,
            contentDescription = "play",
            modifier = Modifier.size(46.dp)

        )
        Spacer(Modifier.size(53.dp,))
        Icon(
            imageVector = Icons.Outlined.Edit,
            tint = Color.White,
            contentDescription = "edit",
            modifier = Modifier.size(24.dp)
        )
}
}

@Composable
fun ImGoodNowButton(){
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .background(Color.Black)
            .padding(10.dp)


    ){
    Text("Im Good Now",
        color = Color.Black
        )
    }
}



@Composable
fun StartHeader(){
    Row(
        modifier = Modifier
            .background(Color.Black)
         
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,


        ) {
            Text(
                "Start",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 300.dp)


            )
            Icon(
                imageVector = Icons.Filled.Settings,
                tint = Color.White,
                contentDescription = "setting",
                modifier = Modifier.size(24.dp)

        )
        }

    }

}


@Preview(showBackground = true)
@Composable
fun AfterSessionCardPreview() {
    GymAppTheme {
        StartHeader()
    }
}