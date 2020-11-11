package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase

@Dao
interface CovidCaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCovidCase(covidCase: CovidCase)
}