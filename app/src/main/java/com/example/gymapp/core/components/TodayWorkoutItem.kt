package com.example.gymapp.core.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.R
import com.example.gymapp.core.components.calendar.NeonLime
import com.example.gymapp.ui.theme.GymAppTheme


data class WorkoutSetDetail(
    val setNumber: Int,
    val reps: Int,
    val weight: String,
    val gain: String? = null
)

@Composable
fun TodayWorkoutItem(
    title: String,
    category: String,
    weight: String,
    modifier: Modifier = Modifier,
    sets: List<WorkoutSetDetail> = emptyList(),
    @DrawableRes imageRes: Int = R.drawable.wo_img_upper
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable(enabled = sets.isNotEmpty()) { isExpanded = !isExpanded }
            .padding(vertical = 14.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = title,
                modifier = Modifier.size(54.dp)
            )

            Spacer(modifier = Modifier.width(17.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
              Text(
                    text = if (sets.isNotEmpty()) "$category • ${sets.size} Sets" else category,
                    color = Color(0xFF8E8E93),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = weight,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )


            }
        }


        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 71.dp, top = 10.dp, end = 4.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                HorizontalDivider(
                    color = Color(0xFF2C2D30),
                    thickness = 1.dp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                sets.forEach { setItem ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Set ${setItem.setNumber}",
                            color = Color(0xFF8E8E93),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = "${setItem.reps} Reps  ×  ${setItem.weight}",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = setItem.gain ?: "—",
                            color = if (setItem.gain?.startsWith("+") == true) NeonLime else Color(0xFF8E8E93),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Single Workout Item Expanded", showBackground = true, backgroundColor = 0xFF1E1F22)
@Composable
fun TodayWorkoutItemPreview() {
    GymAppTheme(darkTheme = true) {
        Surface(color = Color(0xFF1E1F22), modifier = Modifier.padding(horizontal = 24.dp)) {
            TodayWorkoutItem(
                title = "Chest Press",
                category = "Upper Body",
                weight = "20kg",
                sets = listOf(
                    WorkoutSetDetail(setNumber = 1, reps = 12, weight = "15kg", gain = "+ 2kg"),
                    WorkoutSetDetail(setNumber = 2, reps = 10, weight = "18kg", gain = "+ 3kg"),
                    WorkoutSetDetail(setNumber = 3, reps = 8, weight = "20kg", gain = "+ 5kg")
                )
            )
        }
    }
}