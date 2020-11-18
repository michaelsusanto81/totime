package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder

class TotimerService: Service() {

    companion object {
        const val TOTIMER_BR: String = "id.ac.ui.cs.mobileprogramming.michaelsusanto.totimer"
    }

    private val REFRESH_RATE: Long = 100;
    private val broadcastIntent: Intent = Intent(TOTIMER_BR)
    private val mHandler: Handler = Handler()
    private var stopped: Boolean = false;

    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var hrs: Long = 0
    private var mins: Long = 0
    private var secs: Long = 0

    private var hours: String = ""
    private var minutes: String = ""
    private var seconds: String = ""

    private val startTimer: Runnable = object : Runnable {
        override fun run() {
            elapsedTime = System.currentTimeMillis() - startTime
            updateTimer(elapsedTime)
            mHandler.postDelayed(this, REFRESH_RATE)
        }
    }

    private fun updateTimer(elapsedTime: Long) {
        secs = elapsedTime/1000
        mins = secs/60
        hrs = mins/60

        secs %= 60
        seconds = secs.toString().padStart(2, '0')
        mins %= 60
        minutes = mins.toString().padStart(2, '0')
        hours = hrs.toString().padStart(2, '0')

        broadcastIntent.putExtra("hours", hours)
        broadcastIntent.putExtra("minutes", minutes)
        broadcastIntent.putExtra("seconds", seconds)
        sendBroadcast(broadcastIntent)
    }

    override fun onCreate() {
        super.onCreate()

        if(stopped) {
            startTime = System.currentTimeMillis() - elapsedTime
        } else {
            startTime = System.currentTimeMillis()
        }

        mHandler.removeCallbacks(startTimer)
        mHandler.postDelayed(startTimer, 0)
    }

    override fun onDestroy() {
        mHandler.removeCallbacks(startTimer)
        stopped = true
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}