package com.appetiser.exam1.data


import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

/**
 * Entity for user's page activity
 */
@Entity(tableName = "user_activities")
data class UserActivity(
    @Nullable val trackId: String? = null,
    // track last visited. null if on the list page
    // TODO UTC time
    @PrimaryKey @ColumnInfo(name = "timestamp")
    val timestamp:Long = System.currentTimeMillis()
) {

    fun getDateTimeString(): String {
        return try {
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm:ss a")
            val netDate = Date(timestamp)
            "Last visited: " + sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }
}
