package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.dao

import androidx.room.*
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.UserActivity

@Dao
interface UserActivityDao {

    @Query("SELECT * FROM UserActivities")
    suspend fun getUserActivities(): List<UserActivity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserActivity(userActivity: UserActivity)

    @Delete
    suspend fun removeUserActivity(userActivity: UserActivity)
}