package com.min.ex_alarm.reminder

import android.app.*
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import com.min.ex_alarm.AddReminderActivity
import com.min.ex_alarm.R
import com.min.ex_alarm.data.AlarmReminderContract

class ReminderAlarmService : IntentService(TAG) {
    var cursor: Cursor? = null
    override fun onHandleIntent(intent: Intent?) {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val uri = intent!!.data

        //Display a notification to view the task details
        val action = Intent(this, AddReminderActivity::class.java)
        action.data = uri
        val operation: PendingIntent = TaskStackBuilder.create(this)
            .addNextIntentWithParentStack(action)
            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        //Grab the task description
        if (uri != null) {
            cursor = contentResolver.query(uri, null, null, null, null)
        }
        var description = ""
        try {
            if (cursor != null && cursor!!.moveToFirst()) {
                description = AlarmReminderContract.getColumnString(
                    cursor!!,
                    AlarmReminderContract.AlarmReminderEntry.KEY_TITLE
                )
            }
        } finally {
            if (cursor != null) {
                cursor!!.close()
            }
        }
        val note: Notification = Notification.Builder(this)
            .setContentTitle(getString(R.string.reminder_title))
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
            .setContentIntent(operation)
            .setAutoCancel(true)
            .build()
        manager.notify(NOTIFICATION_ID, note)
    }

    companion object {
        private val TAG = ReminderAlarmService::class.java.simpleName
        private const val NOTIFICATION_ID = 42

        //This is a deep link intent, and needs the task stack
        fun getReminderPendingIntent(context: Context?, uri: Uri?): PendingIntent {
            val action = Intent(context, ReminderAlarmService::class.java)
            action.data = uri
            return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }
}