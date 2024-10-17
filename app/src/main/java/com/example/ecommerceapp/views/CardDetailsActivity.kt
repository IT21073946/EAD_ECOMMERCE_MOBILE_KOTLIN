package com.example.ecommerceapp.views

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.controllers.OrderController
import com.example.ecommerceapp.databinding.ActivityCardDetailsBinding
import com.example.ecommerceapp.models.Cart
import com.example.ecommerceapp.models.Order
import com.example.ecommerceapp.models.Product
import com.example.ecommerceapp.models.ProductInOrder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CardDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardDetailsBinding
    private lateinit var orderController: OrderController

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding and OrderController
        binding = ActivityCardDetailsBinding.inflate(layoutInflater)
        orderController = OrderController(this)
        setContentView(binding.root)

        // Retrieve the total amount and userId from the intent
        val totalAmount = intent.getDoubleExtra("totalAmount", 0.0)
        val userId = intent.getStringExtra("userId") ?: "" // Retrieve userId from the intent
        val vendorId = intent.getStringExtra("vendorId") ?: "" // Retrieve vendorId from the intent

        val cart = Cart.getInstance()
        val cartProducts = cart.getProducts().toMutableList()

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
            val shippingAddress = binding.shippingAddress.text.toString().trim() // Retrieve the shipping address

            if (validateCardDetails(cardHolderName, cardNumber, expDate, cvv, shippingAddress)) {
                if (cartProducts.isEmpty()) {
                    Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show()
                } else {
                    // Create order after successful payment
                    createOrder(cartProducts, totalAmount, userId,shippingAddress)                }
            }
        }
    }

    // Create Order function
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createOrder(cartProducts: List<Product>, totalAmount: Double, userId: String, shippingAddress: String) {
        // Convert cartProducts to match the backend's ProductInOrder format
        val productsInOrder = cartProducts.map { product ->
            ProductInOrder(
                productId = product.id ?: "", // Use the product ID
                productName = product.productName,
                price = product.price,
                quantity = 1,
                vendorId = product.vendorId,
                isReady = false
            )
        }

        val order = Order(
            orderDate =  LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), // Example date, use actual date-time
            customerId = userId,
            products = productsInOrder, // Pass the correctly formatted products list
            totalAmount = totalAmount,
            status = 0,
            isCancelled = false,
            shippingAddress = shippingAddress, // Replace with actual shipping address
            cancellationNote = ""
        )

        orderController.createOrder(order) { success, message ->
            if (success) {
                Toast.makeText(this, "Order created successfully!", Toast.LENGTH_SHORT).show()
                Cart.getInstance().clearCart()
                finish() // Close CartActivity
            } else {
                Toast.makeText(this, message ?: "Failed to create order", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Restrict input fields and add formatting
    private fun setupInputRestrictions() {
        binding.cardNumber.filters = arrayOf(InputFilter.LengthFilter(16))
        binding.cvv.filters = arrayOf(InputFilter.LengthFilter(3))
        binding.expDate.filters = arrayOf(InputFilter.LengthFilter(5))

        binding.expDate.addTextChangedListener(object : TextWatcher {
            var isUpdating: Boolean = false

            override fun afterTextChanged(s: Editable?) {
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

    // Validation for card details and shipping address
    private fun validateCardDetails(cardHolderName: String, cardNumber: String, expDate: String, cvv: String, shippingAddress: String): Boolean {
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
            shippingAddress.isEmpty() -> {
                showToast("Shipping address is required")
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
