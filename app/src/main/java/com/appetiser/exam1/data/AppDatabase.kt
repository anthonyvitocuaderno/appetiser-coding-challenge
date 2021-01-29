package com.appetiser.exam1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.appetiser.exam1.utilities.DATABASE_NAME
import com.appetiser.exam1.workers.FetchTracksWorker
import com.appetiser.exam1.workers.SeedDatabaseWorker

/**
 * The Room database for this app
 */
@Database(entities = [Track::class, UserActivity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun userActivityDao(): UserActivityDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            // Create and pre-populate the database. See this article for more details:
                            // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }
    }
}
