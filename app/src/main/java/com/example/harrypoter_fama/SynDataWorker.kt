package com.example.harrypoter_fama

import android.content.Context
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.harrypoter_fama.Utils.Companion.toCharacterEntity
import com.example.harrypoter_fama.models.api.ApiAdapter
import com.example.harrypoter_fama.models.database.AppDatabase

class SyncDataWorker(context: Context, workerParams: WorkerParameters)
    : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val apiResponse = ApiAdapter.apiClient.getCharacters()

            if (apiResponse.isSuccessful && apiResponse.code() == 200) {
                val db by lazy {
                    Room.databaseBuilder(
                        applicationContext,
                        AppDatabase::class.java, "app_database.db"
                    ).build()
                }

                apiResponse.body()?.let {
                    val dataToInsert = it.toCharacterEntity()
                    db.characterDao.inserAll(dataToInsert)
                }

                Result.success()
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
