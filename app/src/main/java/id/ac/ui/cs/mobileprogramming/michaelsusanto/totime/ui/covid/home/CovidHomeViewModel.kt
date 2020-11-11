package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.covid.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.repository.CovidCaseRepository
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.service.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.io.IOException
import retrofit2.HttpException

class CovidHomeViewModel(private val context: Context) : ViewModel() {

    private val pref: SessionManager = SessionManager(context)
    private val repository: CovidCaseRepository = CovidCaseRepository(context)
    val covidCase = MutableLiveData<CovidCase>()
    val error = MutableLiveData<String>()

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    fun updateData() {
        coroutineScope.launch {
            try {
                val data = repository.getCovidCase() as ArrayList
                covidCase.value = data[0]
                setCache(data[0])
            } catch (e: IOException) {
                error.value = "Check your Internet Connection"
                getFromCache()
            } catch (e: HttpException) {
                error.value = e.message()
                getFromCache()
            } catch (e: Exception) {
                error.value = "Unknown Error"
                getFromCache()
            }
        }
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