package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.dao.UserActivityDao
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.UserActivity

@Database(entities = [UserActivity::class], exportSchema = false, version = 1)
abstract class UserActivityDatabase: RoomDatabase() {

    abstract fun userActivityDao(): UserActivityDao

    companion object {
        private var INSTANCE: UserActivityDatabase? = null
        private val DB_NAME = "UserActivityData_DB"

        fun getInstance(context: Context): UserActivityDatabase? {
            if(INSTANCE == null) {
                synchronized(UserActivityDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserActivityDatabase::class.java,
                        DB_NAME
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}