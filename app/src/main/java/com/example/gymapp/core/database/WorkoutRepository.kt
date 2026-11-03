package com.example.gymapp.core.database

import android.content.Context
import androidx.room.withTransaction
import com.example.gymapp.core.database.entity.ExerciseEntity
import com.example.gymapp.core.database.entity.SetRecordEntity
import com.example.gymapp.core.database.entity.WorkoutEntity
import com.example.gymapp.core.database.model.WorkoutWithExercises
import com.example.gymapp.core.model.TrackOnSetRecord
import com.example.gymapp.core.model.TrackPlan
import com.example.gymapp.core.model.TrackRecordWithSet
import com.example.gymapp.core.model.WorkoutPlan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class WorkoutRepository(private val workoutDao: WorkoutDao,context: Context,private val database: AppDatabase,) {


    val allWorkouts: Flow<List<WorkoutPlan>> = workoutDao.getAllWorkoutsWithExercises()
        .map { list ->
            list.map { item ->
                WorkoutPlan(
                    nameWorkout = item.workout.nameWorkout,
                    restTime = item.workout.restTime,
                    startSession = item.workout.startSession,
                    endSession = item.workout.endSession,
                    trackList = item.exercises.map { exerciseWithSets ->
                        TrackRecordWithSet(
                            session = TrackPlan(
                                nameSet = exerciseWithSets.exercise.nameSet,
                                totalSet = exerciseWithSets.exercise.totalSet,
                                startSession = exerciseWithSets.exercise.startSession,
                                endSession = exerciseWithSets.exercise.endSession
                            ),
                            sets = exerciseWithSets.sets.map { set ->
                                TrackOnSetRecord(
                                    setNumber = set.setNumber,
                                    weight = set.weight,
                                    reps = set.reps,
                                    startedAt = set.startedAt,
                                    endedAt = set.endedAt
                                )
                            }
                        )
                    }
                )
            }
        }
    private val seeder = GymDataSeeder(context)



    suspend fun seedDatabaseIfEmpty() = withContext(Dispatchers.IO) {
        if (workoutDao.getWorkoutCount() == 0) {
            val seedList = seeder.loadSeedWorkouts()

           database.withTransaction {
                seedList.forEach { workoutJson ->
                    val workoutId = workoutDao.insertWorkout(
                        WorkoutEntity(
                            nameWorkout = workoutJson.nameWorkout,
                            startSession = workoutJson.startSession,
                            endSession = workoutJson.endSession,
                            restTime = workoutJson.restTime
                        )
                    )

                    workoutJson.trackList.forEach { track ->
                        val exerciseId = workoutDao.insertExercise(
                            ExerciseEntity(
                                workoutId = workoutId,
                                nameSet = track.session.nameSet,
                                totalSet = track.session.totalSet,
                                startSession = track.session.startSession,
                                endSession = track.session.endSession
                            )
                        )

                        val setEntities = track.sets.map { setJson ->
                            SetRecordEntity(
                                exerciseId = exerciseId,
                                setNumber = setJson.setNumber,
                                weight = setJson.weight,
                                reps = setJson.reps,
                                startedAt = setJson.startedAt,
                                endedAt = setJson.endedAt
                            )
                        }
                        workoutDao.insertSets(setEntities)
                    }
                }
            }
        }
    }

    suspend fun saveWorkoutPlan(plan: WorkoutPlan) {
       val workoutId = workoutDao.insertWorkout(
            WorkoutEntity(
                nameWorkout = plan.nameWorkout,
                restTime = plan.restTime,
                startSession = plan.startSession,
                endSession = plan.endSession
            )
        )

      plan.trackList.forEach { trackRecord ->
            val exerciseId = workoutDao.insertExercise(
                ExerciseEntity(
                    workoutId = workoutId,
                    nameSet = trackRecord.session.nameSet,
                    totalSet = trackRecord.session.totalSet,
                    startSession = trackRecord.session.startSession,
                    endSession = trackRecord.session.endSession
                )
            )

          val setEntities = trackRecord.sets?.map { setRecord ->
                SetRecordEntity(
                    exerciseId = exerciseId,
                    setNumber = setRecord.setNumber,
                    weight = setRecord.weight,
                    reps = setRecord.reps,
                    startedAt = setRecord.startedAt,
                    endedAt = setRecord.endedAt
                )
            } ?: emptyList()

            if (setEntities.isNotEmpty()) {
                workoutDao.insertSets(setEntities)
            }
        }
    }
}