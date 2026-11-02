package com.example.gymapp.core.features.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.gymapp.core.components.WorkoutQueueItem
import com.example.gymapp.ui.theme.GymAppTheme

data class QueueExerciseItem(
    val id: String,
    val title: String,
    val desc: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutSheet(
    modifier: Modifier = Modifier,
    initialExercises: List<QueueExerciseItem> = listOf(
        QueueExerciseItem("1", "Butter Fly", "Upper Body"),
        QueueExerciseItem("2", "Back Fly", "Upper Body"),
        QueueExerciseItem("3", "Lat Pulldown", "Upper Body"),
        QueueExerciseItem("4", "Rowing Cable", "Upper Body"),
        QueueExerciseItem("5", "Side Raises Cable", "Upper Body"),
        QueueExerciseItem("6", "Overhead Extension", "Upper Body"),
        QueueExerciseItem("7", "Bicep Curl", "Upper Body")
    ),
    onQueueChanged: (List<QueueExerciseItem>) -> Unit = {},
    onClick: () -> Unit = {}
) {
    var exerciseList by remember(initialExercises) { mutableStateOf(initialExercises) }
    val currentList by rememberUpdatedState(exerciseList)

    // Lacak berdasarkan ID stabil, bukan index angka
    var draggedItemId by remember { mutableStateOf<String?>(null) }
    var dragOffset by remember { mutableFloatStateOf(0f) }
    var itemHeightPx by remember { mutableIntStateOf(0) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh)
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
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    tint = Color.White,
                    contentDescription = "Exit queue list"
                )
            }

            BottomSheetDefaults.DragHandle(
                color = Color.Gray,
            )

            Spacer(modifier = Modifier.size(24.dp))
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(
                items = exerciseList,
                key = { _, item -> item.id }
            ) { _, item ->
                val isDragging = draggedItemId == item.id

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onSizeChanged { size ->
                            if (size.height > 0) itemHeightPx = size.height
                        }
                        .zIndex(if (isDragging) 2f else 0f)
                        .graphicsLayer {
                            translationY = if (isDragging) dragOffset else 0f
                        }
                        // Animate hanya untuk item diam yang bergeser menyingkir
                        .then(if (!isDragging) Modifier.animateItem() else Modifier)
                        // KUNCI: Pakai item.id agar gesture TIDAK terbunuh saat list bertukar
                        .pointerInput(item.id) {
                            detectDragGesturesAfterLongPress(
                                onDragStart = {
                                    draggedItemId = item.id
                                    dragOffset = 0f
                                },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    dragOffset += dragAmount.y

                                    val currentId = draggedItemId ?: return@detectDragGesturesAfterLongPress
                                    val currentIndex = currentList.indexOfFirst { it.id == currentId }
                                    if (currentIndex == -1) return@detectDragGesturesAfterLongPress

                                    val height = itemHeightPx.toFloat().coerceAtLeast(1f)

                                    // Geser ke Bawah
                                    if (dragOffset > height / 2 && currentIndex < currentList.lastIndex) {
                                        val targetIndex = currentIndex + 1
                                        val updated = currentList.toMutableList().apply {
                                            add(targetIndex, removeAt(currentIndex))
                                        }
                                        exerciseList = updated
                                        dragOffset -= height
                                        onQueueChanged(updated)
                                    }
                                    // Geser ke Atas
                                    else if (dragOffset < -height / 2 && currentIndex > 0) {
                                        val targetIndex = currentIndex - 1
                                        val updated = currentList.toMutableList().apply {
                                            add(targetIndex, removeAt(currentIndex))
                                        }
                                        exerciseList = updated
                                        dragOffset += height
                                        onQueueChanged(updated)
                                    }
                                },
                                onDragEnd = {
                                    draggedItemId = null
                                    dragOffset = 0f
                                },
                                onDragCancel = {
                                    draggedItemId = null
                                    dragOffset = 0f
                                }
                            )
                        }
                ) {
                    WorkoutQueueItem(
                        title = item.title,
                        desc = item.desc
                    )
                }
            }
        }

        IconButton(
            modifier = Modifier.size(30.dp),
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Tutup",
                tint = Color.White,
            )
        }

        Spacer(modifier = Modifier.height(79.dp))
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