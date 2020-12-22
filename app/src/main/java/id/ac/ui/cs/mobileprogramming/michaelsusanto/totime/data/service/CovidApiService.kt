package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.api.ApiFactory
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.api.CovidCaseApi
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CovidApiService: Service() {

    companion object {
        const val COVID_BR: String = "id.ac.ui.cs.mobileprogramming.michaelsusanto.totimer.covid"
    }

    private val broadcastIntent: Intent = Intent(COVID_BR)
    private lateinit var api: CovidCaseApi

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    private fun getData() {
        coroutineScope.launch {
            try {
                val data = api.getCovidCase() as ArrayList
                returnData(data[0], "")
            } catch (e: HttpException) {
                returnData(null, e.message())
            } catch (e: Exception) {
                returnData(null, resources.getString(R.string.unknown_error))
            }
        }
    }

    private fun returnData(covidCase: CovidCase?, errorMsg: String) {
        if(covidCase != null) {
            broadcastIntent.putExtra("covidCase", covidCase)
        }
        broadcastIntent.putExtra("errorMsg", errorMsg)
        sendBroadcast(broadcastIntent)
        stopSelf()
    }

    override fun onCreate() {
        super.onCreate()
        api = ApiFactory.getCovidCaseApi(baseContext)
        getData()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}