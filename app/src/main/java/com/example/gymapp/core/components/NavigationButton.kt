package com.example.gymapp.core.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gymapp.ui.AppScreen
import com.example.gymapp.ui.theme.GymAppTheme
@Composable
fun NavigationButton(

    currentScreen: AppScreen = AppScreen.START,
    onNavigate: (AppScreen) -> Unit = {},
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = {},
        border = BorderStroke(6.dp, MaterialTheme.colorScheme.secondaryContainer),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer),
            modifier = modifier
            .padding(horizontal = 53.dp)
            .padding(bottom = 50.dp)
            .height(66.dp)
    ) {

        NavigationBarItem(
            selected = currentScreen == AppScreen.STATISTIC,
            onClick = { onNavigate(AppScreen.STATISTIC) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color.White,
                unselectedIconColor = Color(0xFF8E8E93)
            ),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "statistic",
                    modifier = Modifier.size(if (currentScreen == AppScreen.STATISTIC) 36.dp else 24.dp)
                )
            }
        )


        NavigationBarItem(
            selected = currentScreen == AppScreen.START,
            onClick = { onNavigate(AppScreen.START) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color.White,
                unselectedIconColor = Color(0xFF8E8E93)
            ),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "start",
                    modifier = Modifier.size(if (currentScreen == AppScreen.START) 36.dp else 24.dp)
                )
            }
        )


        NavigationBarItem(
            selected = currentScreen == AppScreen.EDIT,
            onClick = { onNavigate(AppScreen.EDIT) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color.White,
                unselectedIconColor = Color(0xFF8E8E93)
            ),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "edit",
                    modifier = Modifier.size(if (currentScreen == AppScreen.EDIT) 36.dp else 24.dp)
                )
            }
        )
    }
}

@Preview(name = "Navigation Bar - Start Selected", showBackground = true, backgroundColor = 0xFF1E1F22)
@Composable
fun NavigationButtonPreview() {
    GymAppTheme(darkTheme = true) {
        Surface(color = Color(0xFF1E1F22)) {
            NavigationButton(
                currentScreen = AppScreen.START,
                onNavigate = {}
            )
        }
    }
}