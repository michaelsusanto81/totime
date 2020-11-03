package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.home.useractivity

import android.content.Context
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.service.SessionManager
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.util.TimerState

class UserActivityViewModel: ViewModel() {

    private lateinit var pref: SessionManager
    private val TIMER_STATE = "TimerState"

    fun getTimerState(context: Context): TimerState {
        pref = SessionManager(context)
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

    fun setTimerState(context: Context, state: String) {
        pref = SessionManager(context)
        pref.saveData(TIMER_STATE, state)
    }

    fun validateInput(activityName: String, place: String): Boolean {
        return activityName.isNotEmpty() && place.isNotEmpty()
    }
}