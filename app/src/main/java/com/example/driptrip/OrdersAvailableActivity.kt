package com.example.driptrip

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView

class OrdersAvailableActivity : AppCompatActivity() {

    private lateinit var toTakenOrders: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.orders_available)

        toTakenOrders = findViewById(R.id.to_taken_orders)

        toTakenOrders.setOnClickListener {
            startActivity(Intent(this, OrdersTakenActivity::class.java))
        }

    }
}