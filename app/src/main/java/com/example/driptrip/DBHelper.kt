package com.example.driptrip

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDateTime

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        const val DATABASE_NAME = "db.sqlite3"

        // Таблиця користувачів
        const val TABLE_USERS = "auth_user"

        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_LAST_LOGIN = "last_login"
        const val COLUMN_FIRST_NAME = "first_name"
        const val COLUMN_LAST_NAME = "last_name"
        const val COLUMN_IS_STAFF = "is_staff"
        const val COLUMN_IS_SUPERUSER = "is_superuser"
        const val COLUMN_IS_ACTIVE = "is_active"
        const val COLUMN_DATE_JOINED = "date_joined"
    }

    override fun onCreate(db: SQLiteDatabase?) {
//        val createTableQuery =
//            ("CREATE TABLE IF NOT EXISTS $TABLE_USERS ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    "$COLUMN_USERNAME TEXT, $COLUMN_PASSWORD TEXT, " +
//                    "$COLUMN_EMAIL TEXT, $COLUMN_FIRST_NAME TEXT, " +
//                    "$COLUMN_LAST_NAME TEXT, $COLUMN_DATE_JOINED DATETIME)")
//        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun setOrderStatusToActive(orderId: Int) {
        val values = ContentValues().apply {
            put("status", "Active")
            put("usercourier_id", DripTripApp.userId)
        }
        val selection = "id = ?"
        val selectionArgs = arrayOf(orderId.toString())

        writableDatabase.update("drip_order", values, selection, selectionArgs)
    }

    fun getUserId(email: String): Int {
        val db = this.readableDatabase
        var userId = -1

        val query = "SELECT id FROM auth_user WHERE email = ?"
        val cursor = db.rawQuery(query, arrayOf(email))

        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex("id")
            if (idIndex >= 0) {
                userId = cursor.getInt(idIndex)
            }
        }
        cursor.close()
        db.close()
        return userId
    }

    fun cancelOrder(orderId: Int): Boolean {
        val values = ContentValues().apply {
            put("status", "New")
            put("usercourier_id", "")
        }
        val selection = "id = ?"
        val selectionArgs = arrayOf(orderId.toString())
        writableDatabase.update("drip_order", values, selection, selectionArgs)
        return true;
    }

    fun doneOrder(orderId: Int) {
        val values = ContentValues().apply {
            put("status", "Done")
        }
        val selection = "id = ?"
        val selectionArgs = arrayOf(orderId.toString())
        writableDatabase.update("drip_order", values, selection, selectionArgs)
    }
}