package com.min.ex_alarm

import android.app.LoaderManager
import android.content.ContentUris
import android.content.CursorLoader
import android.content.Intent
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.min.ex_alarm.data.AlarmReminderContract
import com.min.ex_alarm.data.AlarmReminderDbHelper

class MainActivity : AppCompatActivity(),
    LoaderManager.LoaderCallbacks<Cursor> {
    private var mAddReminderButton: FloatingActionButton? = null
    private var mToolbar: Toolbar? = null
    var mCursorAdapter: AlarmCursorAdapter? = null
    var alarmReminderDbHelper: AlarmReminderDbHelper = AlarmReminderDbHelper(this)
    var reminderListView: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mToolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(mToolbar)
        mToolbar!!.setTitle(R.string.app_name)
        reminderListView = findViewById(R.id.list) as ListView
        val emptyView: View = findViewById(R.id.empty_view)
        reminderListView!!.emptyView = emptyView
        mCursorAdapter = AlarmCursorAdapter(this, null)
        reminderListView!!.setAdapter(mCursorAdapter)
        reminderListView!!.onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->
                val intent = Intent(this@MainActivity, AddReminderActivity::class.java)
                val currentVehicleUri = ContentUris.withAppendedId(
                    AlarmReminderContract.AlarmReminderEntry.CONTENT_URI,
                    id
                )

                // Set the URI on the data field of the intent
                intent.data = currentVehicleUri
                startActivity(intent)
            }
        mAddReminderButton = findViewById(R.id.fab) as FloatingActionButton?
        mAddReminderButton!!.setOnClickListener(View.OnClickListener { v ->
            val intent = Intent(v.context, AddReminderActivity::class.java)
            startActivity(intent)
        })
        loaderManager.initLoader(VEHICLE_LOADER, null, this)
    }

    override fun onCreateLoader(i: Int, bundle: Bundle): Loader<Cursor> {
        val projection = arrayOf<String>(
            AlarmReminderContract.AlarmReminderEntry._ID,
            AlarmReminderContract.AlarmReminderEntry.KEY_TITLE,
            AlarmReminderContract.AlarmReminderEntry.KEY_DATE,
            AlarmReminderContract.AlarmReminderEntry.KEY_TIME,
            AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT,
            AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO,
            AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE,
            AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE
        )
        return CursorLoader(
            this,  // Parent activity context
            AlarmReminderContract.AlarmReminderEntry.CONTENT_URI,  // Provider content URI to query
            projection,  // Columns to include in the resulting Cursor
            null,  // No selection clause
            null,  // No selection arguments
            null
        ) // Default sort order
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor) {
        mCursorAdapter!!.swapCursor(cursor)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        mCursorAdapter!!.swapCursor(null)
    }

    companion object {
        private const val VEHICLE_LOADER = 0
    }
}
