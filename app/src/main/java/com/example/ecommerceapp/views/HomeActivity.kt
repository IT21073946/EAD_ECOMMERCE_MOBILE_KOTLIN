package com.example.ecommerceapp.views

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.R

class HomeActivity : AppCompatActivity() {

    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Find the Sales Order button and set an OnClickListener
        val salesOrderButton = findViewById<LinearLayout>(R.id.salesOrderButton)
        salesOrderButton.setOnClickListener {
            // Navigate to UserApprovalScreen (ApprovalListActivity)
            val intent = Intent(this, ApprovalListActivity::class.java)
            startActivity(intent)
        }

        // Retrieve userId from LoginActivity
        userId = intent.getStringExtra("userId") ?: ""

        val productListButton = findViewById<LinearLayout>(R.id.productListButton)
        productListButton.setOnClickListener {
            // Pass the userId to ProductListActivity
            val intent = Intent(this, ProductListActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }


        // You can add similar OnClickListeners for other buttons like Invoice, Quotations, etc.
    }
}
