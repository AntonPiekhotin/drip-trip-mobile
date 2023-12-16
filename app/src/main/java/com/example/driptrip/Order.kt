package com.example.driptrip

import java.util.Date

data class Order(
    val orderId: Int,
//    val orderAmount: Double,
    val clientAddress: String,
    val status: String,
    val courierId: Int,
    val sellerId: Int,
    val buyerFullName: String,
    val phoneNumber: String,
    val date: String
)