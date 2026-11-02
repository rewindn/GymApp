package com.example.gymapp.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.gymapp.core.database.entity.ExerciseEntity
import com.example.gymapp.core.database.entity.SetRecordEntity

data class ExerciseWithSets(
    @Embedded val exercise: ExerciseEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "exerciseId"
    )
    val sets: List<SetRecordEntity>
)