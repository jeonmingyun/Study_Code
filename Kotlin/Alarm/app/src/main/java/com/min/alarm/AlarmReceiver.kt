package com.min.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Vibrator
import android.util.Log
import androidx.core.app.NotificationCompat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val ALARM_MILLISECONDS = 1000
        private var notificationManager: NotificationManager? = null
        const val NOTIFICATION_CHANNEL_ID = "ALARM"
        const val NOTIFICATION_ID = 100
    }

    lateinit var context: Context

    override fun onReceive(context: Context, intent: Intent) {
        Log.e("ddddddd", "onReceive()");
        this.context = context
        vibrateAlarm(ALARM_MILLISECONDS)
        pushAlarm()
        context.startService(Intent(context, MainActivity::class.java))
    }

    private fun vibrateAlarm(milliseconds: Int) {
        val vibe = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibe.vibrate(milliseconds.toLong())
    }

    private fun soundAlarm(milliseconds: Int) {
        val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val player: MediaPlayer = MediaPlayer.create(context, uri)
        if (player != null) {
            player.start()
            val timer = Timer()
            timer.scheduleAtFixedRate(object : TimerTask() {
                var count = milliseconds
                override fun run() {
                    count--
                    if (count == 0) {
                        timer.cancel()
                        player.stop()
                    }
                }
            }, 0, 1)
        }
    }

    private fun pushAlarm() {
        createNotificationChannel(NOTIFICATION_CHANNEL_ID, "testChannel", "this is a test Channel")
        displayNotification()
    }

    private fun createNotificationChannel(
        channelId: String,
        name: String,
        channelDescription: String
    ) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT // set importance
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = channelDescription
            }
            // Register the channel with the system
            notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }

    private fun displayNotification() {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Example")
            .setContentText("This is Notification Test")
            .build()

        notificationManager?.notify(NOTIFICATION_ID, notification)
    }

}