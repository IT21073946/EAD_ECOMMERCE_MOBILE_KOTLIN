package com.example.ecommerceapp.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.controllers.OrderController
import com.example.ecommerceapp.databinding.ActivityCartBinding
import com.example.ecommerceapp.models.Cart
import com.example.ecommerceapp.models.Order
import com.example.ecommerceapp.models.Product

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var orderController: OrderController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding and OrderController
        binding = ActivityCartBinding.inflate(layoutInflater)
        orderController = OrderController(this)
        setContentView(binding.root)

        // Set up the ActionBar with back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Get the cart instance and convert the list to a mutable list
        val cart = Cart.getInstance()
        val cartProducts = cart.getProducts().toMutableList()

        // Set up RecyclerView for cart products
        cartAdapter = CartAdapter(cartProducts)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = cartAdapter

        // Calculate total amount and display it
        val totalAmount = calculateTotalAmount(cartProducts)
        binding.totalAmountTextView.text = "Total Amount: $$totalAmount"

        // Handle Buy Now button click
        binding.buyNowButton.setOnClickListener {
            if (cartProducts.isEmpty()) {
                Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show()
            } else {
                // Navigate to Card Details Activity
                val intent = Intent(this, CardDetailsActivity::class.java)
                intent.putExtra("totalAmount", totalAmount) // Pass total amount to next activity
                startActivity(intent)
            }
        }

    }

    // Function to calculate the total amount of all items in the cart
    private fun calculateTotalAmount(products: List<Product>): Double {
        return products.sumOf { it.price }
    }

    // Handle back button click
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()  // Go back to the previous activity
        return true
    }
}
