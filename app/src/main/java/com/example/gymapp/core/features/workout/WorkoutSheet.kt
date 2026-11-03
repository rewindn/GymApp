package com.example.gymapp.core.features.workout

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymapp.core.components.WorkoutQueueItem
import com.example.gymapp.ui.theme.GymAppTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutSheet(
    modifier: Modifier = Modifier,
    workoutViewModel: WorkoutViewModel = viewModel(),
    onClick: () -> Unit = {}
) {
    val workoutUiState by workoutViewModel.uiState.collectAsStateWithLifecycle()
    val exerciseList = workoutUiState.activeTrackList
    val workoutCategory = workoutUiState.masterPlan.nameWorkout

   var draggedIndex by remember { mutableStateOf<Int?>(null) }
    var dragOffset by remember { mutableFloatStateOf(0f) }
    var itemHeightPx by remember { mutableIntStateOf(0) }
    var sheetDragOffsetY by remember { mutableFloatStateOf(0f) }
    val activeIndex = workoutUiState.indexWorkout
    val currentDraggedIndex = draggedIndex
    val height = itemHeightPx.toFloat().coerceAtLeast(1f)

    val targetIndex = if (currentDraggedIndex != null && exerciseList.isNotEmpty()) {
        val displacement = (dragOffset / height).roundToInt()
        val minAllowedIndex = (activeIndex + 1).coerceAtMost(exerciseList.lastIndex)
        (currentDraggedIndex + displacement).coerceIn(minAllowedIndex, exerciseList.lastIndex)
    } else null

  val commitReorder by rememberUpdatedState {
        val from = draggedIndex
        val to = targetIndex
        if (from != null && to != null && from != to) {
            workoutViewModel.reorderTrackList(from, to)
        }
        draggedIndex = null
        dragOffset = 0f
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .graphicsLayer {
                translationY = sheetDragOffsetY
            }
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 23.dp)
                .padding(top = 28.dp)
        ) {
            IconButton(
                onClick = onClick,
                modifier = Modifier.size(40.dp) // Area sentuh aman
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = "Exit queue list",
                    modifier = Modifier.size(28.dp)
                )
            }

            BottomSheetDefaults.DragHandle(
                color = Color.Gray
            )

            Spacer(modifier = Modifier.size(40.dp))
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(
                items = exerciseList,
                key = { _, item -> item.session.nameSet }
            ) { index, item ->
                val isLocked = index <= activeIndex
                val isDragging = currentDraggedIndex == index

                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { dismissValue ->
                        if (!isLocked && dismissValue != SwipeToDismissBoxValue.Settled) {
                            workoutViewModel.zeroTrackSets(index)
                            true
                        } else {
                            false
                        }
                    }
                )
                val otherItemOffset by animateFloatAsState(
                    targetValue = when {
                        currentDraggedIndex != null && targetIndex != null && !isLocked -> {
                            when {
                                currentDraggedIndex < targetIndex && index in (currentDraggedIndex + 1)..targetIndex -> -height
                                currentDraggedIndex > targetIndex && index in targetIndex until currentDraggedIndex -> height
                                else -> 0f
                            }
                        }
                        else -> 0f
                    },
                    label = "OtherItemOffsetAnimation"
                )

                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = !isLocked,
                    enableDismissFromEndToStart = !isLocked,
                    backgroundContent = {
                        val color = when (dismissState.targetValue) {
                            SwipeToDismissBoxValue.Settled -> Color.Transparent
                            else -> Color(0xFFD32F2F)
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 24.dp),
                            contentAlignment = if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
                                Alignment.CenterStart
                            } else {
                                Alignment.CenterEnd
                            }
                        ) {

                        }
                    }
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onSizeChanged { size ->
                                if (size.height > 0) itemHeightPx = size.height
                            }
                            .zIndex(if (isDragging) 2f else 0f)
                            .graphicsLayer {
                                translationY = if (isDragging) dragOffset else otherItemOffset
                            }
                          .alpha(if (isLocked) 0.35f else 1f)
                    )
                    {
                        WorkoutQueueItem(
                            title = item.session.nameSet,
                            desc = if (isLocked && index == activeIndex) "Sedang Berjalan" else workoutCategory,
                            isLocked = isLocked,
                            dragModifier = if (!isLocked) {
                                Modifier.pointerInput(index) {
                                    detectDragGesturesAfterLongPress(
                                        onDragStart = {
                                            draggedIndex = index
                                            dragOffset = 0f
                                        },
                                        onDrag = { change, dragAmount ->
                                            change.consume()
                                            dragOffset += dragAmount.y
                                        },
                                        onDragEnd = { commitReorder() },
                                        onDragCancel = {
                                            draggedIndex = null
                                            dragOffset = 0f
                                        }
                                    )
                                }
                            } else {
                                Modifier
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun WorkoutSheetPreview() {
    GymAppTheme(darkTheme = true) {
        Surface {
            WorkoutSheet()
        }
    }
}