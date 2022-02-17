package com.example.yogiyo.db

import android.provider.BaseColumns

class DbTable {
    /*회원*/
    object Users : BaseColumns {
        const val TABLENAME = "users"
        const val COLUMN_LOGIN = "login"
        const val COLUMN_ID = "id"
        const val COLUMN_NODE_ID = "node_id"
        const val COLUMN_AVATAR_URL = "avatar_url"
        const val COLUMN_HTML_URL = "html_url"
        const val QUERY_CREATE = ("create table IF NOT EXISTS " + TABLENAME + "("
                + COLUMN_LOGIN + " text primary key,"
                + COLUMN_ID + " integer,"
                + COLUMN_NODE_ID + " text,"
                + COLUMN_AVATAR_URL + " text,"
                + COLUMN_HTML_URL + " text);")
        const val QUERY_DROP = "drop table if exists " + TABLENAME
    }
}