package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.repository

import android.content.Context
import android.content.Intent
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.dao.CovidCaseDao
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.database.CovidCaseDatabase
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.service.CovidApiService

class CovidCaseRepository(private val context: Context) {

    private lateinit var dao: CovidCaseDao
    private val db: CovidCaseDatabase? = CovidCaseDatabase.getInstance(context)

    init {
        if (db != null) {
            dao = db.covidCaseDao()
        }
    }

    suspend fun getSavedCovidCases(): List<CovidCase> {
        return dao.getSavedCovidCases()
    }

    suspend fun addCovidCase(covidCase: CovidCase) {
        dao.addCovidCase(covidCase)
    }

    fun getCovidCase() {
        context.startService(Intent(context, CovidApiService::class.java))
    }

    suspend fun removeSavedCovidCase(covidCase: CovidCase) {
        dao.removeSavedCovidCase(covidCase)
    }
}