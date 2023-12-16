package com.example.driptrip

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AvailableOrdersAdapter(private val orderList: List<Order>, private val mContext: Context) :
    RecyclerView.Adapter<AvailableOrdersAdapter.OrderViewHolder>() {


    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.findViewById(R.id.orderImageView)
        var sellerTextView: TextView = itemView.findViewById(R.id.sellerTextView)
        var buyerTextView: TextView = itemView.findViewById(R.id.buyerTextView)
        var toTextView: TextView = itemView.findViewById(R.id.toTextView)
        var orderIdTextView: TextView = itemView.findViewById(R.id.orderIdTextView)
        var dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private lateinit var dbHelper: DBHelper


        fun bindData(order: Order, mContext: Context) {
            dbHelper = DBHelper(mContext)
            val database = dbHelper.readableDatabase
            val columnsSellerName = arrayOf("first_name", "last_name")
            val selection = "id = ?"
            val selectionArgs = arrayOf(order.sellerId.toString())
            val cursor = database.query(
                "auth_user",
                columnsSellerName,
                selection,
                selectionArgs,
                null,
                null,
                null
            )
            var firstName = ""
            var lastName = ""

            if (cursor.moveToFirst()) {
                val firstNameIndex = cursor.getColumnIndex("first_name")
                if (firstNameIndex >= 0) {
                    firstName = cursor.getString(firstNameIndex)
                }

                val lastNameIndex = cursor.getColumnIndex("last_name")
                if (lastNameIndex >= 0) {
                    lastName = cursor.getString(lastNameIndex)
                }
                cursor.close()
            }
            database.close()

            val sellerName = firstName + lastName
            sellerTextView.text = "Seller: " + sellerName
            buyerTextView.text = "Buyer: " + order.buyerFullName
            toTextView.text = "Address: " + order.clientAddress
            orderIdTextView.text = "Order id: " + order.orderId.toString()
            dateTextView.text = order.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.available_list_item, parent, false)
        return OrderViewHolder(itemView)
    }


    //fill the list
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bindData(orderList[position], mContext);
    }

    override fun getItemCount(): Int {
        return orderList.size;
    }
}