package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.home.history

import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.UserActivity

interface HistoryCommunicator {
    fun clickedItem(userActivity: UserActivity)
}