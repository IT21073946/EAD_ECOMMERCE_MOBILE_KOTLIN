//package com.example.ecommerceapp.views
//
//import android.os.Bundle
//import android.view.View
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.ecommerceapp.R
//import com.example.ecommerceapp.views.OrderAdapter
//import com.example.ecommerceapp.controllers.OrderController
//import com.example.ecommerceapp.models.Order
//
//class OrderListActivity : AppCompatActivity() {
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var orderAdapter: OrderAdapter
//    private lateinit var orderController: OrderController
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_order_list)
//
//        // Initialize RecyclerView and Adapter
//        recyclerView = findViewById(R.id.orderRecyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        orderAdapter = OrderAdapter(mutableListOf())
//        recyclerView.adapter = orderAdapter
//
//        // Fetch the list of orders from the server
//        fetchOrders()
//
//        // Set title for Order screen
//        val titleTextView: TextView = findViewById(R.id.screenTitle)
//        titleTextView.text = "Order History"
//
//        // Hide the back button if not needed
//        val backIcon: ImageView = findViewById(R.id.backIcon)
//        backIcon.visibility = View.GONE
//
//        // Handle clicks for profile or notification icons if necessary
//        val profileIcon: ImageView = findViewById(R.id.profileIcon)
//        profileIcon.setOnClickListener {
//            // Do something when profile icon is clicked
//        }
//    }
//
//    private fun fetchOrders() {
//        orderController = OrderController(this)
//
//        orderController.getOrders { orders, error ->
//            if (orders != null) {
//                orderAdapter.setOrders(orders)
//            }
//        }
//    }
//}
