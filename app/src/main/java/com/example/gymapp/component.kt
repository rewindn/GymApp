package com.example.gymapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.ui.theme.GymAppTheme


@Composable
fun DoneText( modifier: Modifier = Modifier) {
    Text(
        text = "Done",
        fontSize = 40.sp,
        modifier = modifier
    )
}


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
        Spacer(Modifier.size(53.dp))
        Icon(
            imageVector = Icons.Outlined.Edit,
            tint = Color.White,
            contentDescription = "edit",
            modifier = Modifier.size(24.dp)
        )
    }
}
@Composable
fun ProgressBar(){
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = Color(60,60,60)),
    ) {
        Image(
            painter = painterResource(R.drawable.wo_img_upper),
            contentDescription = "Workout Image",
            modifier = Modifier
                .size(54.dp)
                .padding(end = 16.dp)
        )
        Column {
            Text("Playing Now",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
            Text(
                "Upper Body",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .width(300.dp)
            )
        }
    }
}
@Composable
fun LongPressQueueItem(){
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = Color(95,95,95)),
        shape = RoundedCornerShape(14.dp),


        ) {
        Icon(
            painter = painterResource(R.drawable.baseline_queue_24),
            tint = Color.White,
            contentDescription = "add to queue",
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(
            "Add To Queue",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .width(300.dp)
                .padding(vertical = 38.dp)

        )
    }
}

@Composable
fun LongPressEditItem(){
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = Color(95,95,95)),
        shape = RoundedCornerShape(14.dp),


        ) {
        Icon(
            imageVector = Icons.Filled.Edit,
            tint = Color.White,
            contentDescription = "edit",
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(
            "Edit",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .width(300.dp)
                .padding(vertical = 16.dp)

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
fun PlayButton(){
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .background(Color.Black)
            .padding(10.dp)
    ){
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            tint = Color.Black,
            contentDescription = "play",
            modifier = Modifier.size(46.dp)
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

@Composable
fun BackHeader(){
    Row(
        modifier = Modifier
            .background(Color.Black)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,


            ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                tint = Color.White,
                contentDescription = "setting",
                modifier = Modifier
                    .padding(end = 300.dp)
                    .size(24.dp)




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
@Composable
fun PlayingNowHeader(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(Color.Black)

    ) {
        Column {
            Text("Playing Now",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )
            Text(
                "Upper Body",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 200.dp)
            )
        }



        Icon(
            imageVector = Icons.Filled.Settings,
            tint = Color.White,
            contentDescription = "setting",
            modifier = Modifier.size(24.dp)

        )
    }

}

@Composable
fun WorkoutQueueItem(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(Color.Black)

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
                "Chest Press",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 150.dp)
            )
            Text(
                "Upper Body",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )


        }

        Icon(
            imageVector = Icons.Filled.Menu,
            tint = Color.White,
            contentDescription = "toggle queue",
            modifier = Modifier.size(24.dp)

        )
    }

}
@Composable
fun WorkoutItem(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(Color.Black)

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
                "Chest Press",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 150.dp)
            )
            Text(
                "Upper Body",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )


        }

        Text(
            "8set",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
        )
    }

}

@Composable
fun OnPlayInput(){
    MaterialTheme(darkColorScheme()) {
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Input Your Reps") },
            shape = RoundedCornerShape(100),
        )
    }
}

@Composable
fun UpperBigCard(){
    OutlinedCard(
        colors = CardDefaults.cardColors(Color(30,30,30)),
        border = BorderStroke(1.dp,Color.White)
    ) {
        Box(
            modifier = Modifier.size(width = 248.dp, height = 337.dp),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource( R.drawable.upper_image),
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 367.dp, height = 348.dp)

            )
            Text(
                "UPPER BODY",
                fontSize = 31.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

        }
    }
}


@Composable
fun UpperCard(){
    OutlinedCard(
        colors = CardDefaults.cardColors(Color(30,30,30)),
        border = BorderStroke(1.dp,Color.White)
    ) {
        Box(
            modifier = Modifier.size(width = 248.dp, height = 248.dp),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource( R.drawable.upper_image),
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 367.dp, height = 348.dp)

            )
            Text(
                "UPPER BODY",
                fontSize = 31.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

        }
    }
}

@Composable
fun DateSquare(date: String){
    Text(
        date,
        color = Color.White,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        modifier = Modifier.background(color = Color.Green, shape = RoundedCornerShape(16))
            .size(36.dp)
            .wrapContentHeight(align = Alignment.CenterVertically)

    )
}

@Preview(showBackground = true)
@Composable
fun AfterSessionCardPreview() {
    GymAppTheme {
        DateSquare("1")
    }
}