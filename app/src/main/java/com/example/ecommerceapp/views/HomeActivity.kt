package com.example.ecommerceapp.views

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.R

class HomeActivity : AppCompatActivity() {

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

        val invoiceButton = findViewById<LinearLayout>(R.id.invoiceButton)
        invoiceButton.setOnClickListener {
            // Navigate to UserApprovalScreen (ApprovalListActivity)
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        val orderButton = findViewById<LinearLayout>(R.id.orderButton)
        orderButton.setOnClickListener {
            // Navigate to UserApprovalScreen (ApprovalListActivity)
            val intent = Intent(this, OrderListActivity::class.java)
            startActivity(intent)
        }

        // You can add similar OnClickListeners for other buttons like Invoice, Quotations, etc.
    }
}
