package com.appetiser.exam1.di

import android.content.Context
import com.appetiser.exam1.data.AppDatabase
import com.appetiser.exam1.data.TrackDao
import com.appetiser.exam1.data.UserActivityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideTrackDao(appDatabase: AppDatabase): TrackDao {
        return appDatabase.trackDao()
    }

    @Provides
    fun provideUserActivityDao(appDatabase: AppDatabase): UserActivityDao {
        return appDatabase.userActivityDao()
    }
}
