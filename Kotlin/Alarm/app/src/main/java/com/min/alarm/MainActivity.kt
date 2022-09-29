package com.min.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var save: Button? = null
    private var timePicker: TimePicker? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timePicker = findViewById(R.id.time_picker)
        save = findViewById(R.id.save)

        var alarmMgr: AlarmManager? = null
        lateinit var alarmIntent: PendingIntent
        alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        }
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val hour = timePicker!!.hour
        val minute = 0
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }

        Log.e("ddddddd", calendar.time.dateToString("yyyy-MM-dd HH:mm:ss"))
        alarmMgr.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )
        // 테스트 알람
/*        alarmMgr?.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() +5000,
            alarmIntent
        )*/

        save!!.setOnClickListener(View.OnClickListener { v: View? ->
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return@OnClickListener

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            val hour = timePicker!!.hour
            val minute = timePicker!!.minute
            calendar[Calendar.HOUR_OF_DAY] = hour
            calendar[Calendar.MINUTE] = minute
            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.DATE, 1)
            }

            Log.e("ddddddd", calendar.time.dateToString("yyyy-MM-dd HH:mm:ss"))
            val alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager
            if (alarmManager != null) {
                val intent = Intent(this, AlarmReceiver::class.java)
                val alarmIntent =
                    PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY, alarmIntent
                )
                Toast.makeText(this@MainActivity, "알람이 저장되었습니다.", Toast.LENGTH_LONG).show()
//                alarmManager.cancel(alarmIntent)
            }
        })
    }

    private fun Date.dateToString(format: String): String {
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())

        return dateFormatter.format(this)
    }

    private fun Long.longToString(format: String): String {
        val date = Date(this)
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())

        return dateFormatter.format(date)
    }

}