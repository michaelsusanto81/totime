package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.home.useractivity

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.database.UserActivityDatabase
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.UserActivity
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.repository.UserActivityRepository
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.service.SessionManager
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.util.TimerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserActivityViewModel(private val context: Context): ViewModel() {

    private val pref: SessionManager = SessionManager(context)
    private val repository: UserActivityRepository = UserActivityRepository(context)
    private val TIMER_STATE = "TimerState"
    private var hours: Int = 0
    private var minutes: Int = 0
    private var seconds: Int = 0

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    fun getTimerState(): TimerState {
        val state = pref.fetchData(TIMER_STATE)

        return if(state != null) {
            when (state) {
                "Started" -> TimerState.STARTED
                "Stopped" -> TimerState.STOPPED
                else -> TimerState.SAVED
            }
        } else {
            TimerState.SAVED
        }
    }

    fun setTimerState(state: String) {
        pref.saveData(TIMER_STATE, state)
    }

    fun validateInput(activityName: String, place: String): Boolean {
        return activityName.isNotEmpty() && place.isNotEmpty()
    }

    fun setTimer(hours: Int, minutes: Int, seconds: Int) {
        this.hours = hours
        this.minutes = minutes
        this.seconds = seconds
    }

    fun getHours() = hours
    fun getMinutes() = minutes
    fun getSeconds() = seconds

    fun saveData(userActivity: UserActivity) {
        coroutineScope.launch {
            repository.addUserActivity(userActivity)
        }
    }
}