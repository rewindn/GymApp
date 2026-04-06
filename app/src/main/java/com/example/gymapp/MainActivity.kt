package com.example.gymapp


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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


@Preview(showBackground = true)
@Composable
fun AfterSessionCardPreview() {
    GymAppTheme {
        ImGoodNowButton()
    }
}