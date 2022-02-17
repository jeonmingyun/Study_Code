package com.example.yogiyo.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.annotation.Nullable

class DbOpenHelper(@Nullable context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_VERSION = 1
        private const val DB_NAME = "sqlite.db"
        private var sInstance: DbOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): DbOpenHelper? {
            if (sInstance == null) {
                sInstance = DbOpenHelper(context)
            }
            return sInstance
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DbTable.Users.QUERY_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DbTable.Users.QUERY_DROP)
    }

    /**
     * SQL
     */
    fun selectAllUsers(): Cursor {
        val mdb = this.readableDatabase
        val sql = "select * from "+ DbTable.Users.TABLENAME
        return mdb.rawQuery(sql, null)
    }

    fun insertOrReplaceUser(
        login: String?,
        id: Int?,
        node_id: String?,
        avatar_url: String?,
        html_url: String?
    ) {
        val mdb = this.writableDatabase
        val sql = "INSERT OR REPLACE INTO " +
                DbTable.Users.TABLENAME+
                " (${DbTable.Users.COLUMN_LOGIN},${DbTable.Users.COLUMN_ID},${DbTable.Users.COLUMN_NODE_ID}," +
                "${DbTable.Users.COLUMN_AVATAR_URL},${DbTable.Users.COLUMN_HTML_URL}) " +
                "VALUES (\"${login}\",${id},\"${node_id}\",\"${avatar_url}\",\"${html_url}\");"

        mdb.execSQL(sql);
    }

    fun deleteUser(login: String): Boolean {
        val mdb = this.writableDatabase
        val result = mdb.delete(
            DbTable.Users.TABLENAME,
            DbTable.Users.COLUMN_LOGIN.toString() + "=?",
            arrayOf(login)
        )
        return result != 0 // success
    }

}