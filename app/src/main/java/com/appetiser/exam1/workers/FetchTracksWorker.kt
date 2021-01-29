package com.appetiser.exam1.workers

import android.content.Context
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.appetiser.exam1.api.ApiService
import com.appetiser.exam1.data.AppDatabase
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

class FetchTracksWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val apiService: ApiService) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope{

        try {
            val response = apiService.searchTracks()

            val database = AppDatabase.getInstance(applicationContext)
            database.trackDao().insertAll(response.results)
            Log.d(TAG, "Success seeding database")
            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.retry()
        }
    }

    companion object {
        private const val TAG = "FetchTracksWorker"
    }
}
