package com.appetiser.exam1.workers
import android.content.Context
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.appetiser.exam1.api.ApiService
import com.appetiser.exam1.data.AppDatabase
import com.appetiser.exam1.data.UserActivity
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

class SeedDatabaseWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope{

        try {
            val database = AppDatabase.getInstance(applicationContext)
            val userActivities = ArrayList<UserActivity>()
            userActivities.add(UserActivity())
            database.userActivityDao().insertAll(userActivities)
            Result.success()
        } catch (ex: Exception) {
            Result.retry()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}
