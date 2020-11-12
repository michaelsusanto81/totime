package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.repository

import android.content.Context
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.api.ApiFactory
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.api.CovidCaseApi
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.dao.CovidCaseDao
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.database.CovidCaseDatabase
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase

class CovidCaseRepository(context: Context) {

    private lateinit var api: CovidCaseApi
    private lateinit var dao: CovidCaseDao
    private val db: CovidCaseDatabase? = CovidCaseDatabase.getInstance(context)

    init {
        if (db != null) {
            dao = db.covidCaseDao()
        }
        api = ApiFactory.getCovidCaseApi(context)
    }

    suspend fun getSavedCovidCases(): List<CovidCase> {
        return dao.getSavedCovidCases()
    }

    suspend fun addCovidCase(covidCase: CovidCase) {
        dao.addCovidCase(covidCase)
    }

    suspend fun getCovidCase(): List<CovidCase> {
        return api.getCovidCase()
    }

    suspend fun removeSavedCovidCase(covidCase: CovidCase) {
        dao.removeSavedCovidCase(covidCase)
    }
}