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

        val alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, AlarmReceiver.NOTIFICATION_ID, intent, PendingIntent.FLAG_IMMUTABLE)
        }

        // spinner 저장 시간 알람
        save!!.setOnClickListener(View.OnClickListener { v: View? ->
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return@OnClickListener

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            val hour = timePicker!!.hour
            val minute = timePicker!!.minute
            calendar[Calendar.HOUR_OF_DAY] = hour
            calendar[Calendar.MINUTE] = minute+1
//            if (calendar.before(Calendar.getInstance())) {
//                calendar.add(Calendar.DATE, 1)
//            }

            Log.e("ddddddd", calendar.time.dateToString("yyyy-MM-dd HH:mm:ss"))
            // 3초 뒤 알람 : 단일 재생
            /*alarmMgr?.set(
                AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime() +3000,
                alarmIntent
            )*/

            // 지정 시간 알람
            alarmMgr.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
            )

            // 3초 뒤 알람 : 반복 재생
/*
            alarmMgr?.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 3000L,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
            )
*/

            Toast.makeText(this@MainActivity, "알람이 저장되었습니다.", Toast.LENGTH_LONG).show()
//                alarmMgr.cancel(alarmIntent)
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