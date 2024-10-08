//package com.example.ecommerceapp.views
//
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.ecommerceapp.R
//import com.example.ecommerceapp.controllers.VendorController
//import com.example.ecommerceapp.models.Order
//import com.example.ecommerceapp.models.Vendor
//
//class OrderAdapter(
//    private var orders: MutableList<Order>
//) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
//
//    // Function to update the order list
//    fun setOrders(orderList: List<Order>) {
//        this.orders = orderList.toMutableList()
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
//        return OrderViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
//        val order = orders[position]
//        holder.bind(order)
//    }
//
//    override fun getItemCount(): Int = orders.size
//
//    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val vendorNameTextView: TextView = itemView.findViewById(R.id.vendorName)
//        private val orderDateTextView: TextView = itemView.findViewById(R.id.orderDate)
//        private val totalAmountTextView: TextView = itemView.findViewById(R.id.totalAmount)
//        private val statusTextView: TextView = itemView.findViewById(R.id.status)
//        private val viewProductsIcon: ImageView = itemView.findViewById(R.id.viewProductsIcon)
//
//        // Reference to VendorController to fetch vendor details
//        private val vendorController = VendorController()
//
//        fun bind(order: Order) {
//            // Fetch vendor name based on vendorId and set it to vendorNameTextView
//            vendorController.getVendorById(order.vendorId) { vendor ->
//                vendor?.let {
//                    vendorNameTextView.text = "Vendor: ${it.vendorName}"
//                }
//            }
//
//            // Set other order details
//            orderDateTextView.text = "Order Date: ${order.orderDate}"
//            totalAmountTextView.text = "Total: ${order.totalAmount}"
//            statusTextView.text = "Status: ${order.status}"
//
//            // Handle product view icon click
//            viewProductsIcon.setOnClickListener {
//                // Open a new activity to display products related to this order
//                val intent = Intent(itemView.context, OrderProductListActivity::class.java)
//                intent.putExtra("orderId", order.id)  // Pass the order ID to the next screen
//                itemView.context.startActivity(intent)
//            }
//        }
//    }
//}
