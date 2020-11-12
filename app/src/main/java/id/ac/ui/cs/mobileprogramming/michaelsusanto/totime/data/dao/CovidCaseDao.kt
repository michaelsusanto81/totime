package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.dao

import androidx.room.*
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase

@Dao
interface CovidCaseDao {

    @Query("SELECT * FROM CovidCases")
    suspend fun getSavedCovidCases(): List<CovidCase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCovidCase(covidCase: CovidCase)

    @Delete
    suspend fun removeSavedCovidCase(covidCase: CovidCase)
}