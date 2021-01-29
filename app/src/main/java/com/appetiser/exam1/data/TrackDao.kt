package com.appetiser.exam1.data


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the Track class.
 */
@Dao
interface TrackDao {
    @Query("SELECT * FROM tracks ORDER BY id")
    fun getAll(): Flow<List<Track>>

    @Query("SELECT * FROM tracks WHERE id = :id")
    fun getOne(id: String): Flow<Track>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(collection: List<Track>)
}