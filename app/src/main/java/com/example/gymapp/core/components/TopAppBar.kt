package com.example.gymapp.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.ui.theme.GymAppTheme


@Composable
fun TopAppBar(
    icon : ImageVector? = null,
    text : String = "",
    label : String? = null,
    onExitClick : () -> Unit,
    modifier: Modifier
){
    var expanded by remember { mutableStateOf(false) }

    Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = modifier

            ) {

            if (icon != null){
            Icon(
                imageVector = icon,
                contentDescription = "setting",
                modifier = Modifier
                    .size(24.dp)
            )
        }


                Column {
                    if (label != null) {
                        Text(
                            label,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                    Text(
                        text,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,

                    )

            }


            IconButton(
                onClick = { expanded = !expanded },

            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "setting",
                    modifier = Modifier.size(24.dp)

                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Exit") },
                        onClick = onExitClick
                    )
                }
            }

            }


        }





@Preview
@Composable
fun TopBarPreview(){
    GymAppTheme(darkTheme = true) {
        Surface(){
            Column() {
                TopAppBar(icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft, onExitClick = {},modifier = Modifier.fillMaxWidth().padding(horizontal = 23.dp).padding(bottom = 108.dp))
                TopAppBar(text = "Start",onExitClick = {} ,modifier = Modifier.padding(top = 45.dp, bottom = 96.dp).padding(horizontal = 23.dp) .fillMaxWidth())
                TopAppBar(label = "Playing Now", text = "ChestPress",onExitClick = {} ,modifier =  Modifier.padding( bottom = 51.dp).padding(horizontal = 23.dp) .fillMaxWidth())

            }

        }
    }

}


