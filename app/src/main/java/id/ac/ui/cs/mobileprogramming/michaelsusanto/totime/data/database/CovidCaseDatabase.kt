package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.dao.CovidCaseDao
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase

@Database(entities = [CovidCase::class], exportSchema = false, version = 1)
abstract class CovidCaseDatabase: RoomDatabase() {

    abstract fun covidCaseDao(): CovidCaseDao

    companion object {
        private var INSTANCE: CovidCaseDatabase? = null
        private val DB_NAME = "CovidCaseData_DB"

        fun getInstance(context: Context): CovidCaseDatabase? {
            if(INSTANCE == null) {
                synchronized(CovidCaseDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CovidCaseDatabase::class.java,
                        DB_NAME
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}