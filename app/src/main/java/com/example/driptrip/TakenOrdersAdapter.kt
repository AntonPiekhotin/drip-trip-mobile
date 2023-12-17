package com.example.driptrip

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TakenOrdersAdapter(private val takenOrderList: List<Order>, private val mContext: Context) :
    RecyclerView.Adapter<TakenOrdersAdapter.TakenOrderViewHolder>() {
    private var dbHelper: DBHelper = DBHelper(mContext)

    class TakenOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.findViewById(R.id.orderImageViewTaken)
        var sellerTextView: TextView = itemView.findViewById(R.id.sellerTextViewTaken)
        var buyerTextView: TextView = itemView.findViewById(R.id.buyerTextViewTaken)
        var toTextView: TextView = itemView.findViewById(R.id.toTextViewTaken)
        var orderIdTextView: TextView = itemView.findViewById(R.id.orderIdTextViewTaken)
        var dateTextView: TextView = itemView.findViewById(R.id.dateTextViewTaken)
        var doneButton: Button = itemView.findViewById(R.id.doneButton)
        var cancelButton: Button = itemView.findViewById(R.id.cancelButton)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TakenOrderViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.taken_list_item, parent, false)
        return TakenOrderViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return takenOrderList.size;
    }

    override fun onBindViewHolder(holder: TakenOrderViewHolder, position: Int) {
        holder.bindData(takenOrderList[position], mContext);
        holder.cancelButton.setOnClickListener {
            dbHelper.cancelOrder(takenOrderList[position].orderId)
            Toast.makeText(mContext, "Ви відмовились від замовлення.", Toast.LENGTH_SHORT).show()
            mContext.startActivity(Intent(mContext, OrdersTakenActivity::class.java))
        }
        holder.doneButton.setOnClickListener {
            dbHelper.doneOrder(takenOrderList[position].orderId)
            Toast.makeText(mContext, "Замовлення виконано!", Toast.LENGTH_SHORT).show()
            mContext.startActivity(Intent(mContext, OrdersTakenActivity::class.java))
        }
    }
}