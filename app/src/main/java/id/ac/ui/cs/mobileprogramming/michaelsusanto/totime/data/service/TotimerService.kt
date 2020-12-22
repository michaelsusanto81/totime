package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.service

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.MainActivity
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R

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

    private val ONGOING_NOTIFICATION_ID = 1
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager

    private val startTimer: Runnable = object : Runnable {
        override fun run() {
            elapsedTime = System.currentTimeMillis() - startTime
            updateTimer(elapsedTime)
            notificationBuilder.setContentText("$hours:$minutes:$seconds")
            notificationManager.notify(ONGOING_NOTIFICATION_ID, notificationBuilder.build())
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

        buildNotification()
    }

    private fun buildNotification() {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("totimer_service", "Totimer Service")
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }

        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        notificationBuilder = NotificationCompat.Builder(this,
            channelId
        )
            .setContentTitle(getText(R.string.totimer))
            .setContentText("$hours:$minutes:$seconds")
            .setSmallIcon(R.drawable.ic_totimer)
            .setContentIntent(pendingIntent)
            .setCategory(Notification.CATEGORY_SERVICE)

        startForeground(ONGOING_NOTIFICATION_ID, notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(chan)
        return channelId
    }

    override fun onDestroy() {
        mHandler.removeCallbacks(startTimer)
        stopForeground(true)
        stopSelf()
        stopped = true
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}