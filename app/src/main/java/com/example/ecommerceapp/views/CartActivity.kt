package com.example.ecommerceapp.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.controllers.OrderController
import com.example.ecommerceapp.controllers.UserController
import com.example.ecommerceapp.databinding.ActivityCartBinding
import com.example.ecommerceapp.models.Cart
import com.example.ecommerceapp.models.Order
import com.example.ecommerceapp.models.Product

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var orderController: OrderController
    private lateinit var userController: UserController
    private lateinit var loggedInEmail: String // To store logged-in user's email

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding and OrderController
        binding = ActivityCartBinding.inflate(layoutInflater)
        orderController = OrderController(this)
        userController = UserController(this)
        setContentView(binding.root)

        // Set up the ActionBar with back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Get the logged-in user's email from SharedPreferences
        loggedInEmail = getLoggedInUserEmail()

        // Load and display the user ID
        loadUserId(loggedInEmail)

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
                val userId = binding.userIdTextView.text.toString().replace("User ID: ", "")
                // Navigate to CardDetailsActivity with userId and totalAmount
                val intent = Intent(this, CardDetailsActivity::class.java)
                intent.putExtra("totalAmount", totalAmount)
                intent.putExtra("userId", userId) // Pass the userId
                startActivity(intent)
            }
        }


    }

    // Function to calculate the total amount of all items in the cart
    private fun calculateTotalAmount(products: List<Product>): Double {
        return products.sumOf { it.price }
    }

    // Function to retrieve logged-in user email from SharedPreferences
    private fun getLoggedInUserEmail(): String {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("logged_in_email", "") ?: ""
    }

    // Function to load and display the user ID
    private fun loadUserId(email: String) {
        userController.getUserByEmail(email) { user, message ->
            if (user != null) {
                // Display the user ID in the TextView
                binding.userIdTextView.text = "User ID: ${user.id}"
            } else {
                Toast.makeText(this, message ?: "Failed to load user ID", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Handle back button click
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()  // Go back to the previous activity
        return true
    }
}