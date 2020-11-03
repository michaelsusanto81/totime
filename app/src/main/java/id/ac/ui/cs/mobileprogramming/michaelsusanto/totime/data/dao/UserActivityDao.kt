package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.UserActivity

@Dao
interface UserActivityDao {

    @Query("SELECT * FROM UserActivities")
    suspend fun getUserActivities(): List<UserActivity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserActivity(userActivity: UserActivity)
}