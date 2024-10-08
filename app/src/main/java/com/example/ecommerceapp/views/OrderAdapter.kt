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
        private val viewProductsButton: Button = itemView.findViewById(R.id.viewProductsButton)

        fun bind(order: Order) {
            // Set order details
            orderDateTextView.text = "Order Date: ${order.orderDate}"
            totalAmountTextView.text = "Total: ${order.totalAmount}"
            statusTextView.text = order.getStatusName()

            // Set color based on status
            when (order.status) {
                "Pending" -> statusTextView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.grey))
                "Cancelled" -> statusTextView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.red))
                "Processing" -> statusTextView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.yellow))
                "Delivered" -> statusTextView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.green))
                "PartiallyReady" -> statusTextView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.blue))
                "ReadyForShipment" -> statusTextView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.orange))
            }

            // Handle product view button click
//            viewProductsButton.setOnClickListener {
//                // Open a new activity to display products related to this order
//                val intent = Intent(itemView.context, OrderProductListActivity::class.java)
//                intent.putExtra("orderId", order.id)  // Pass the order ID to the next screen
//                itemView.context.startActivity(intent)
//            }
        }
    }
}
