package com.appetiser.exam1.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the UserActivity class.
 */
@Dao
interface UserActivityDao {

    @Query("SELECT * FROM user_activities ORDER BY timestamp")
    fun getAll(): Flow<List<UserActivity>>

    // null searching for the list view; pass track id if on detail view
    @Query("SELECT * FROM user_activities WHERE trackId=:trackId  or (trackId is null and :trackId is null) ORDER BY timestamp DESC LIMIT 1")
    fun getLatest(trackId: String?): Flow<UserActivity?>

    // get absolute latest for restoring previous session
    @Query("SELECT * FROM user_activities ORDER BY timestamp DESC LIMIT 1")
    fun getLatest(): Flow<UserActivity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(collection: List<UserActivity>)
}