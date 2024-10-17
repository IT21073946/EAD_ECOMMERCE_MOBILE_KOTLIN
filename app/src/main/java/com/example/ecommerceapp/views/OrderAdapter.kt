package com.example.ecommerceapp.views

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.models.Order
import java.text.SimpleDateFormat
import java.util.Locale

class OrderAdapter(
    private var orders: MutableList<Order>
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    // Function to update the order list
    fun setOrders(orderList: List<Order>) {
        this.orders = orderList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int = orders.size

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderDateTextView: TextView = itemView.findViewById(R.id.orderDate)
        private val totalAmountTextView: TextView = itemView.findViewById(R.id.totalAmount)
        private val statusTextView: TextView = itemView.findViewById(R.id.status)
        private val shippingAddressTextView: TextView = itemView.findViewById(R.id.shippingAddress)


        fun bind(order: Order) {
            // Format the date to show only the date part
            val formattedDate = formatDate(order.orderDate)
            orderDateTextView.text = "Order Date: $formattedDate"
            totalAmountTextView.text = "Total: ${order.totalAmount}"
            statusTextView.text = order.getStatusName()
            shippingAddressTextView.text = "Shipment Address: ${order.shippingAddress}"

            // Set color based on status
            when (order.status) {
                0 -> statusTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.grey))
                7 -> statusTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                1 -> statusTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.yellow))
                6 -> statusTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
                3 -> statusTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.blue))
                2 -> statusTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.orange))
            }
        }


        // Helper method to format the date string
        private fun formatDate(orderDate: String): String {
            // Parse the date string and format it to "yyyy-MM-dd"
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(orderDate)
            return outputFormat.format(date)
        }
    }
}
