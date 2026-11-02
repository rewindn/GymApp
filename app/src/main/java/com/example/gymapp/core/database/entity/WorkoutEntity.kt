package com.example.gymapp.core.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.Instant

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nameWorkout: String = "Upper Body",
    val restTime: String,
    val startSession: Instant,
    val endSession: Instant? = null
)

data class ExerciseWithSets(
    @Embedded val exercise: ExerciseEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "exerciseId"
    )
    val sets: List<SetRecordEntity>
)

data class WorkoutWithExercises(
    @Embedded val workout: WorkoutEntity,
    @Relation(
        entity = ExerciseEntity::class,
        parentColumn = "id",        // Primary Key di WorkoutEntity
        entityColumn = "workoutId"  // Foreign Key di ExerciseEntity
    )
    val exercises: List<ExerciseWithSets>
)