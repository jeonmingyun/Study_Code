package com.min.ex_alarm.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AlarmReminderDbHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        // Create a String that contains the SQL statement to create the reminder table
        val SQL_CREATE_ALARM_TABLE =
            ("CREATE TABLE " + AlarmReminderContract.AlarmReminderEntry.TABLE_NAME.toString() + " ("
                    + AlarmReminderContract.AlarmReminderEntry._ID.toString() + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + AlarmReminderContract.AlarmReminderEntry.KEY_TITLE.toString() + " TEXT NOT NULL, "
                    + AlarmReminderContract.AlarmReminderEntry.KEY_DATE.toString() + " TEXT NOT NULL, "
                    + AlarmReminderContract.AlarmReminderEntry.KEY_TIME.toString() + " TEXT NOT NULL, "
                    + AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT.toString() + " TEXT NOT NULL, "
                    + AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO.toString() + " TEXT NOT NULL, "
                    + AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE.toString() + " TEXT NOT NULL, "
                    + AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE.toString() + " TEXT NOT NULL " + " );")

        // Execute the SQL statement
        sqLiteDatabase.execSQL(SQL_CREATE_ALARM_TABLE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {}

    companion object {
        private const val DATABASE_NAME = "alarmreminder.db"
        private const val DATABASE_VERSION = 1
    }
}
