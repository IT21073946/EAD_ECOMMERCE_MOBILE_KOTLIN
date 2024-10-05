package com.example.ecommerceapp.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivityProductDetailBinding
import com.example.ecommerceapp.models.Cart
import com.example.ecommerceapp.models.Product
import com.example.ecommerceapp.models.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var cart: Cart
    private var isCommentsVisible = false // Track visibility of the comments section

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding and Cart
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cart = Cart.getInstance()  // Get the singleton cart instance

        // Set up the ActionBar with back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Retrieve product passed from the previous activity
        val product = intent.getSerializableExtra("product") as Product

        // Set product details in the UI
        binding.productName.text = product.productName
        binding.productDescription.text = product.description
        binding.productPrice.text = "Price: $${product.price}"

        // Load image with Glide
        Glide.with(this).load(product.base64Image).into(binding.productImage)

        binding.venderId.text = product.vendorId

        // Handle Add To Cart button click
        binding.addToCartButton.setOnClickListener {
            cart.addProduct(product)
            Toast.makeText(this, "${product.productName} added to cart", Toast.LENGTH_SHORT).show()
        }

        // Handle Cart icon click to open CartActivity
        binding.cartIcon.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        // Handle Rate button click to open VendorRatingActivity
        binding.rateButton.setOnClickListener {
            val intent = Intent(this, VendorRatingActivity::class.java)
            intent.putExtra("vendorId", product.vendorId) // Passing vendorId to the rating activity
            startActivity(intent)
        }

        // Handle Dropdown icon click to show or hide comments
        binding.dropdownIcon.setOnClickListener {
            if (isCommentsVisible) {
                // Hide the comments section
                binding.commentsSection.visibility = View.GONE
                isCommentsVisible = false
            } else {
                // Show the comments section and load vendor reviews
                binding.commentsSection.visibility = View.VISIBLE
                isCommentsVisible = true
                loadVendorReviews(product.vendorId)
            }
        }
    }

    // Function to load vendor reviews from the API
    private fun loadVendorReviews(vendorId: String) {
        ApiClient.reviewApi.getReviewsForVendor(vendorId).enqueue(object : Callback<List<Review>> {
            override fun onResponse(call: Call<List<Review>>, response: Response<List<Review>>) {
                if (response.isSuccessful) {
                    val reviews = response.body() ?: emptyList()
                    displayReviews(reviews)
                } else {
                    Toast.makeText(this@ProductDetailActivity, "Failed to load reviews", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Review>>, t: Throwable) {
                Toast.makeText(this@ProductDetailActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Function to display reviews in the UI
// Function to display reviews in the UI with RatingBar and comment
    private fun displayReviews(reviews: List<Review>) {
        val commentsContainer: LinearLayout = findViewById(R.id.comments_container)
        commentsContainer.removeAllViews() // Clear previous reviews

        for (review in reviews) {
            // Create a LinearLayout to hold the RatingBar and TextView
            val reviewLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(0, 16, 0, 16)
            }

            // Create RatingBar to show the rating
            val ratingBar = RatingBar(this).apply {
                numStars = 5
                stepSize = 1f
                rating = review.rating.toFloat()
                scaleX = 0.7f
                scaleY = 0.7f
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            // Create TextView to show the comment
            val commentView = TextView(this).apply {
                text = "Comment: ${review.comment}"
                textSize = 16f
                setPadding(0, 8, 0, 0)
            }

            // Add the RatingBar and comment TextView to the review layout
            reviewLayout.addView(ratingBar)
            reviewLayout.addView(commentView)

            // Add the review layout to the comments container
            commentsContainer.addView(reviewLayout)
        }
    }


    // Handle back button click
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()  // Go back to the previous activity (Product List)
        return true
    }
}
