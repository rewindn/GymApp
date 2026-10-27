package com.example.gymapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gymapp.core.components.NavigationButton
import com.example.gymapp.core.features.start.StartScreen
import com.example.gymapp.core.features.workout.WorkoutScreen
import com.example.gymapp.ui.theme.GymAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview
@Composable
fun GymApp(){
    val scope : CoroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    GymAppTheme(darkTheme = true){


            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetShape = RoundedCornerShape(20.dp),//Rounded corners
                modifier = Modifier.fillMaxHeight(),
                sheetContent = {
                    Scaffold() { paddingValues ->
                        WorkoutScreen(
                            snackbarTrigger = {

                                scope.launch {
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                    snackbarHostState.showSnackbar(it)
                                }
                            },
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                    }


//

            ){innerpadding ->
                Scaffold(
                    bottomBar = { NavigationButton() },
                    snackbarHost = { SnackbarHost(
                        snackbarHostState,
                    ) }
                ) { padding ->
                    StartScreen(modifier = Modifier.padding(padding), onClick = {
                        scope.launch { sheetState.expand() }
                    })
                }
            }
        }
    }
