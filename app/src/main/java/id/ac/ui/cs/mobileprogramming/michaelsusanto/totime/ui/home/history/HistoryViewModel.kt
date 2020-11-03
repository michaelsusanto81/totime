package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.home.history

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.UserActivity
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.repository.UserActivityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HistoryViewModel(private val context: Context): ViewModel() {

    private val repository: UserActivityRepository = UserActivityRepository(context)
    val liveData = MutableLiveData<List<UserActivity>>()

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    fun fetchData() {
        coroutineScope.launch {
            val userActivities = repository.getUserActivities()
            liveData.value = userActivities
        }
    }
}