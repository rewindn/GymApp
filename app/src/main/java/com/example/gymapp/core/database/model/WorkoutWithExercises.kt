package com.example.gymapp.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.gymapp.core.database.entity.ExerciseEntity
import com.example.gymapp.core.database.entity.WorkoutEntity

data class WorkoutWithExercises(
    @Embedded val workout: WorkoutEntity,
    @Relation(
        entity = ExerciseEntity::class,
        parentColumn = "id",
        entityColumn = "workoutId"
    )
    val exercises: List<ExerciseWithSets>
)