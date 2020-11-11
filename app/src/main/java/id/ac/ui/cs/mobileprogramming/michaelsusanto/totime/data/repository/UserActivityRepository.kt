package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.repository

import android.content.Context
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.dao.UserActivityDao
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.database.UserActivityDatabase
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.UserActivity
import kotlinx.coroutines.Deferred

class UserActivityRepository(context: Context) {

    private lateinit var dao: UserActivityDao
    private val db: UserActivityDatabase? = UserActivityDatabase.getInstance(context)

    init {
        if (db != null) {
            dao = db.userActivityDao()
        }
    }

    suspend fun getUserActivities(): List<UserActivity> {
        return dao.getUserActivities()
    }

    suspend fun addUserActivity(userActivity: UserActivity) {
        dao.addUserActivity(userActivity)
    }
}