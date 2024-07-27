package com.example.currencyconverter.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.currencyconverter.util.Const

class DataBaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, Const.DATABASE_NAME, null, Const.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = ("CREATE TABLE ${Const.TABLE_NAME} (" +
                "${Const.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Const.COLUMN_USERNAME} TEXT, " +
                "${Const.COLUMN_PASSWORD} TEXT," +
                "${Const.COLUMN_EMAIL} TEXT)")

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "DROP TABLE IF EXISTS ${Const.TABLE_NAME}"
        db?.execSQL(dropTable)
        onCreate(db)
    }
}