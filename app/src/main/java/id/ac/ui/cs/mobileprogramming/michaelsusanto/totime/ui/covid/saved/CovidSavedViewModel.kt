package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.covid.saved

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.repository.CovidCaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CovidSavedViewModel(private val context: Context): ViewModel() {

    private val repository: CovidCaseRepository = CovidCaseRepository(context)
    val liveData = MutableLiveData<List<CovidCase>>()

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    fun fetchData() {
        coroutineScope.launch {
            val savedCovidCases = repository.getSavedCovidCases()
            liveData.value = savedCovidCases
        }
    }
}