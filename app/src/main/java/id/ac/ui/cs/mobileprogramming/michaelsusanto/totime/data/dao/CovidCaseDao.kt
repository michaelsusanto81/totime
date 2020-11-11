package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase

@Dao
interface CovidCaseDao {

    @Query("SELECT * FROM CovidCases")
    suspend fun getSavedCovidCases(): List<CovidCase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCovidCase(covidCase: CovidCase)
}