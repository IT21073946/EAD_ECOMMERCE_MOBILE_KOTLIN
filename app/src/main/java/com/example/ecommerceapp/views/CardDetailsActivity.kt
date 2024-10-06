package com.example.ecommerceapp.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.controllers.OrderController
import com.example.ecommerceapp.databinding.ActivityCardDetailsBinding
import com.example.ecommerceapp.models.Cart
import com.example.ecommerceapp.models.Order
import com.example.ecommerceapp.models.Product

class CardDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardDetailsBinding
    private lateinit var orderController: OrderController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding and OrderController
        binding = ActivityCardDetailsBinding.inflate(layoutInflater)
        orderController = OrderController(this)
        setContentView(binding.root)

        // Retrieve the total amount from the intent
        val totalAmount = intent.getDoubleExtra("totalAmount", 0.0)

        // Update the total amount TextView
        binding.totalAmountTextView.text = "Total: $$totalAmount"

        // Set up validation and input restrictions for card fields
        setupInputRestrictions()

        // Handle Pay Button Click
        binding.payButton.setOnClickListener {
            val cardHolderName = binding.cardHolderName.text.toString().trim()
            val cardNumber = binding.cardNumber.text.toString().trim()
            val expDate = binding.expDate.text.toString().trim()
            val cvv = binding.cvv.text.toString().trim()

            if (validateCardDetails(cardHolderName, cardNumber, expDate, cvv)) {
                // Create order after successful payment
                createOrder(totalAmount)
            }
        }
    }

    // Create Order function
    private fun createOrder(totalAmount: Double) {
        val cart = Cart.getInstance()
        val cartProducts = cart.getProducts()

        // Hardcoded values
        val customerId = "66ed76db4441e38a23821c0f" // Replace with real value
        val shippingAddress = "456 Comfort Blvd, Springfield, IL 62701"
        val status = "Shipped"
        val isCancelled = false

        val order = Order(
            customerId = customerId,
            products = cartProducts,
            totalAmount = totalAmount,
            status = status,
            isCancelled = isCancelled,
            shippingAddress = shippingAddress,
            vendorId = cartProducts.first().vendorId // Assuming all products have the same vendorId
        )

        orderController.createOrder(order) { success, message ->
            if (success) {
                Toast.makeText(this, "Order created successfully!", Toast.LENGTH_SHORT).show()
                finish() // Close the activity after creating the order
            } else {
                Toast.makeText(this, "Failed to create order: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Restrict input fields and add formatting
    private fun setupInputRestrictions() {
        binding.cardNumber.filters = arrayOf(android.text.InputFilter.LengthFilter(16))
        binding.cvv.filters = arrayOf(android.text.InputFilter.LengthFilter(3))
        binding.expDate.filters = arrayOf(android.text.InputFilter.LengthFilter(5))

        binding.expDate.addTextChangedListener(object : android.text.TextWatcher {
            var isUpdating: Boolean = false

            override fun afterTextChanged(s: android.text.Editable?) {
                if (isUpdating) return
                val input = s.toString().replace("/", "")
                if (input.length >= 2) {
                    val formatted = "${input.substring(0, 2)}/${input.substring(2)}"
                    isUpdating = true
                    binding.expDate.setText(formatted)
                    binding.expDate.setSelection(formatted.length)
                    isUpdating = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun validateCardDetails(cardHolderName: String, cardNumber: String, expDate: String, cvv: String): Boolean {
        return when {
            cardHolderName.isEmpty() -> {
                showToast("Card holder name is required")
                false
            }
            cardNumber.length != 16 -> {
                showToast("Card number must be 16 digits")
                false
            }
            !expDate.matches(Regex("(0[1-9]|1[0-2])/([0-9]{2})")) -> {
                showToast("Expiration date must be in MM/YY format")
                false
            }
            cvv.length != 3 -> {
                showToast("CVV must be 3 digits")
                false
            }
            else -> true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
