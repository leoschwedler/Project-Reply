package com.example.currencyconverter.data.db

import android.content.ContentValues
import android.content.Context
import com.example.currencyconverter.data.model.User
import com.example.currencyconverter.util.Const

class UserDAO(val context: Context): IUserDAO {

    val read = DataBaseHelper(context).readableDatabase
    val write = DataBaseHelper(context).writableDatabase

    override fun insertUser(user: User): Long {
        val values = ContentValues().apply {
            put(Const.COLUMN_EMAIL, user.email)
            put(Const.COLUMN_USERNAME, user.username)
            put(Const.COLUMN_PASSWORD, user.password)
        }
        return write.insert(Const.TABLE_NAME,null,values)
    }

    override fun readUser(user: User): Boolean {
        val selection = "${Const.COLUMN_EMAIL} = ? AND ${Const.COLUMN_PASSWORD} = ?"
        val selectionArgs = arrayOf(user.email,user.password)
        val cursor = read.query(Const.TABLE_NAME, null, selection, selectionArgs, null,null,null)
        val userExists = cursor.count > 0
        cursor.close()
        return userExists
    }


}