package com.example.gymapp.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun NavigationButton() {

    OutlinedButton(
        onClick = {},
        border = BorderStroke(6.dp, MaterialTheme.colorScheme.secondary),
        modifier = Modifier.padding(horizontal = 53.dp).padding(bottom = 50.dp).height(66.dp)





    ) {
        NavigationBarItem(
            icon = {Icon(
                imageVector = Icons.Outlined.Info,

                contentDescription = "play",
                modifier = Modifier.size(24.dp)

            )}, selected = false, onClick = {}
        )

        NavigationBarItem(
            icon = {Icon(
                imageVector = Icons.Filled.PlayArrow,

                contentDescription = "play",
                modifier = Modifier.size(46.dp)

            )}, selected = false, onClick = {}
        )

        NavigationBarItem(
            icon = { Icon(
                imageVector = Icons.Outlined.Edit,

                contentDescription = "edit",
                modifier = Modifier.size(24.dp)
            )}, selected = false, onClick = {}
        )

    }
}