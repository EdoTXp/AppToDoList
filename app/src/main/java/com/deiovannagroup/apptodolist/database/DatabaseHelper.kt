package com.deiovannagroup.apptodolist.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION,
) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "task.db"
        const val TASK_TABLE = "tasks"

        const val TASK_ID = "task_id"
        const val DESCRIPTION = "description"
        const val CREATION_DATE = "creation_date"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val sql = "CREATE TABLE IF NOT EXISTS $TASK_TABLE (" +
                "$TASK_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "$DESCRIPTION VARCHAR(70) NOT NULL," +
                "$CREATION_DATE DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");"
        try {
            db?.execSQL(sql)
            Log.i("info_db", "Success on create a table v: $DATABASE_VERSION")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Error on create a table ${e.message}")
        }

    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
    }
}