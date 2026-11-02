package com.example.gymapp.core.database

import android.content.Context
import android.util.Log
import com.example.gymapp.core.model.WorkoutPlan
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.reflect.TypeToken
import java.time.Instant

class GymDataSeeder(private val context: Context) {

   private val gson = GsonBuilder()
        .registerTypeAdapter(
            Instant::class.java,
            JsonDeserializer { json, _, _ ->
                if (json.isJsonNull || json.asString.isEmpty()) null else Instant.parse(json.asString)
            }
        )
        .create()

    fun loadSeedWorkouts(): List<WorkoutPlan> {
        return try {
            val listFiles = context.assets.list("")?.toList()
            Log.d("CheckAssets", "File yang ditemukan di assets: $listFiles")
            val jsonString = context.assets.open("gym_history_seed.json")
                .bufferedReader()
                .use { it.readText() }

            val type = object : TypeToken<List<WorkoutPlan>>() {}.type
            gson.fromJson(jsonString, type) ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}