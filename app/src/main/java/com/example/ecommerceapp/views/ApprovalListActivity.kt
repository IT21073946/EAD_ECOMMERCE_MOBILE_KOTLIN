package com.example.ecommerceapp.views

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.controllers.UserController

class ApprovalListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserApprovalAdapter
    private lateinit var userController: UserController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approval_list)

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.approvalRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        userAdapter = UserApprovalAdapter(mutableListOf())
        recyclerView.adapter = userAdapter

        // Fetch the list of pending user approvals from the server
        fetchUserApprovals()

        // Get reference to the common app bar's title TextView
        val titleTextView: TextView = findViewById(R.id.screenTitle)

        // Set title for Home screen
        titleTextView.text = "User Approval Requests"

        // Hide the back button in Home screen
        val backIcon: ImageView = findViewById(R.id.backIcon)
        backIcon.visibility = View.GONE

        // Handle clicks for profile or notification icons if necessary
        val profileIcon: ImageView = findViewById(R.id.profileIcon)
        profileIcon.setOnClickListener {
            // Do something when profile icon is clicked
        }
    }

    private fun fetchUserApprovals() {
        // You should call your API to get the list of pending user approvals.
        userController = UserController(this)

        userController.getPendingUsers { users ->
            if (users != null) {
                userAdapter.setUsers(users)
            }
        }
    }
}
