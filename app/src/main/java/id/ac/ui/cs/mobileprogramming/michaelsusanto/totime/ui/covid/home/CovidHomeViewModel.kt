package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.covid.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.repository.CovidCaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CovidHomeViewModel(private val context: Context): ViewModel() {

    private val repository: CovidCaseRepository = CovidCaseRepository(context)
    val covidCase = MutableLiveData<CovidCase>()

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    fun updateData() {
        coroutineScope.launch {
            val data = repository.getCovidCase() as ArrayList
            covidCase.value = data[0]
        }
    }
}