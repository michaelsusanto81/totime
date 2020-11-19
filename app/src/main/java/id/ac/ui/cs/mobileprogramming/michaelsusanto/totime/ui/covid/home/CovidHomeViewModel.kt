package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.covid.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.repository.CovidCaseRepository
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.service.CovidApiService
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.service.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class CovidHomeViewModel(private val context: Context) : ViewModel() {

    private val pref: SessionManager = SessionManager(context)
    private val repository: CovidCaseRepository = CovidCaseRepository(context)
    val covidCase = MutableLiveData<CovidCase>()
    val error = MutableLiveData<String>()

    private val br: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            unwrapData(intent)
        }
    }

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    fun saveData(savedCovidCase: CovidCase) {
        coroutineScope.launch {
            savedCovidCase.date = getDate()
            repository.addCovidCase(savedCovidCase)
        }
    }

    private fun unwrapData(intent: Intent?) {
        if(intent?.extras != null) {
            val covidCase = intent.getParcelableExtra<CovidCase>("covidCase")
            val errorMsg = intent.getStringExtra("errorMsg")

            if(covidCase != null) {
                this.covidCase.value = covidCase
                setCache(covidCase)
            }

            if(errorMsg != null && errorMsg != "") {
                error.value = errorMsg
                getFromCache()
            }
        }
        context.unregisterReceiver(br)
    }

    private fun getDate(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return "$year/$month/$day"
    }

    fun updateData() {
        error.value = ""
        context.registerReceiver(br, IntentFilter(CovidApiService.COVID_BR))
        repository.getCovidCase()
    }

    private fun getFromCache() {
        val death = pref.fetchData("death")
        val hospitalized = pref.fetchData("hospitalized")
        val recovered = pref.fetchData("recovered")
        val positive = pref.fetchData("positive")
        if (death != null && hospitalized != null && recovered != null && positive != null) {
            val covidCaseData = CovidCase(
                death = death,
                hospitalized = hospitalized,
                recovered = recovered,
                positive = positive
            )
            covidCase.value = covidCaseData
        }
    }

    private fun setCache(covidCase: CovidCase) {
        pref.saveData("death", covidCase.death)
        pref.saveData("hospitalized", covidCase.hospitalized)
        pref.saveData("recovered", covidCase.recovered)
        pref.saveData("positive", covidCase.positive)
    }
}