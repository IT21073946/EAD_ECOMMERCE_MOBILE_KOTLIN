package com.example.ecommerceapp.views

import android.os.Bundle
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
