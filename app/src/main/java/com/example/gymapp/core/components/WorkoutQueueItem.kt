package com.example.gymapp.core.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.R

@Composable
fun WorkoutQueueItem(
    title: String,
    desc: String,
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int = R.drawable.wo_img_upper,
    isLocked: Boolean = false,
    dragModifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 23.dp, vertical = 12.dp)
    ) {

        Image(
            painter = painterResource(imageRes),
            contentDescription = title,
            modifier = Modifier.size(54.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))


        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = desc,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )
        }


        Icon(
            imageVector = Icons.Filled.Menu,
            tint = if (isLocked) Color.DarkGray else Color.White,
            contentDescription = "toggle queue",
            modifier = Modifier
                .size(24.dp)
                .then(dragModifier)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun WorkoutQueueItemPreviews() {
    WorkoutQueueItem(
        title = "Chest Press",
        desc = "Upper Body",
        isLocked = false
    )
}