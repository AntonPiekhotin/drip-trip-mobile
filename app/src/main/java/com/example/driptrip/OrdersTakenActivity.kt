package com.example.driptrip

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView

class OrdersTakenActivity : AppCompatActivity() {

    private lateinit var toAvailableOrders: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.orders_taken)

        toAvailableOrders = findViewById(R.id.to_available_orders)

        toAvailableOrders.setOnClickListener{
            startActivity(Intent(this, OrdersAvailableActivity::class.java))
        }
    }
}