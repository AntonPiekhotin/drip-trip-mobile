package com.example.driptrip

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrdersTakenActivity : AppCompatActivity() {

    private lateinit var toAvailableOrders: AppCompatTextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.orders_taken)

        toAvailableOrders = findViewById(R.id.to_available_orders)
        recyclerView = findViewById(R.id.recycler_view_taken)
        dbHelper = DBHelper(this)

        //getting list of orders
        val takenOrdersList = mutableListOf<Order>()
        val database = dbHelper.readableDatabase
        val columns = arrayOf(
            "id", "adress", "status", "usercourier_id", "user_id",
            "full_name", "phone_number", "date"
        )
        val selection = "status = ? AND usercourier_id = ?"
        val selectionArgs = arrayOf("Active", DripTripApp.userId.toString())
        val cursor =
            database.query("drip_order", columns, selection, selectionArgs, null, null, null)

        var id = 0
        var address = ""
        var status = ""
        var userCourierId = 0
        var userId = 0
        var fullName = ""
        var phoneNumber = ""
        var date = ""

        if (cursor.moveToFirst()) {
            do {
                val idIndex = cursor.getColumnIndex("id")
                if (idIndex >= 0) {
                    id = cursor.getInt(idIndex)
                }
                val addressIndex = cursor.getColumnIndex("adress")
                if (addressIndex >= 0) {
                    address = cursor.getString(addressIndex)
                }

                val statusIndex = cursor.getColumnIndex("status")
                if (statusIndex >= 0) {
                    status = cursor.getString(statusIndex)
                }

                val userCourierIdIndex = cursor.getColumnIndex("usercourier_id")
                if (userCourierIdIndex >= 0) {
                    userCourierId = cursor.getInt(userCourierIdIndex)
                }

                val userIdIndex = cursor.getColumnIndex("user_id")
                if (userIdIndex >= 0) {
                    userId = cursor.getInt(userIdIndex)
                }

                val fullNameIndex = cursor.getColumnIndex("full_name")
                if (fullNameIndex >= 0) {
                    fullName = cursor.getString(fullNameIndex)
                }

                val phoneNumberIndex = cursor.getColumnIndex("phone_number")
                if (phoneNumberIndex >= 0) {
                    phoneNumber = cursor.getString(phoneNumberIndex)
                }

                val dateIndex = cursor.getColumnIndex("date")
                if (dateIndex >= 0) {
                    date = cursor.getString(dateIndex)
                }

                val order =
                    Order(id, address, status, userCourierId, userId, fullName, phoneNumber, date)
                takenOrdersList.add(order)
            } while (cursor.moveToNext())
        }
        cursor.close()
        database.close()

        mLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = mLayoutManager
        val adapter = TakenOrdersAdapter(takenOrdersList, this)
        recyclerView.adapter = adapter

        toAvailableOrders.setOnClickListener {
            startActivity(Intent(this, OrdersAvailableActivity::class.java))
        }
    }
}