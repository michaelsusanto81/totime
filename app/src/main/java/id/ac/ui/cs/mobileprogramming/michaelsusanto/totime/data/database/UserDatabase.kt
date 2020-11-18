package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.dao.UserDao
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.User

@Database(entities = [User::class], exportSchema = false, version = 1)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: UserDatabase? = null
        private val DB_NAME = "UserData_DB"

        fun getInstance(context: Context): UserDatabase? {
            if(INSTANCE == null) {
                synchronized(UserDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        DB_NAME
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}