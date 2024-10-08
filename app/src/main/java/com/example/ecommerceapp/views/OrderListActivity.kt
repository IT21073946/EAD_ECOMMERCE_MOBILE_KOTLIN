package com.example.ecommerceapp.views

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.controllers.OrderController
import com.example.ecommerceapp.controllers.UserController

class OrderListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var orderController: OrderController
    private lateinit var userController: UserController
    private lateinit var loggedInEmail: String
    private lateinit var loggedInUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)

        // Retrieve logged-in user's email from SharedPreferences
        loggedInEmail = getLoggedInUserEmail()

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.orderRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        orderAdapter = OrderAdapter(mutableListOf())
        recyclerView.adapter = orderAdapter

        // Fetch userId using the logged-in email
        fetchUserIdByEmail()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()  // Navigate back to the previous screen
        }

    }

    private fun fetchUserIdByEmail() {
        userController = UserController(this)

        // Get user by email
        userController.getUserByEmail(loggedInEmail) { user, error ->
            if (user != null) {
                loggedInUserId = user.id ?: ""
                // Now fetch the orders based on the userId
                fetchOrders()
            } else {
                Toast.makeText(this, error ?: "Failed to retrieve user details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchOrders() {
        orderController = OrderController(this)

        // Fetch orders using the logged-in user's userId
        orderController.getOrdersByCustomerId(loggedInUserId) { orders, error ->
            if (orders != null) {
                orderAdapter.setOrders(orders)
            } else {
                Toast.makeText(this, error ?: "Failed to fetch orders", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Retrieve the logged-in user's email from SharedPreferences
    private fun getLoggedInUserEmail(): String {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("logged_in_email", "") ?: ""
    }
}
