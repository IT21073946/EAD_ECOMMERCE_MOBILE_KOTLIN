package com.example.ecommerceapp.views

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.R
import com.example.ecommerceapp.controllers.UserController
import com.example.ecommerceapp.models.Review
import com.example.ecommerceapp.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VendorRatingActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var commentInput: EditText
    private lateinit var submitButton: Button
    private lateinit var vendorId: String
    private lateinit var userController: UserController
    private var userId: String? = null // Nullable, in case it fails to load

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor_rating)

        // Bind UI elements to the variables
        ratingBar = findViewById(R.id.ratingBar)
        commentInput = findViewById(R.id.commentInput)
        submitButton = findViewById(R.id.submitButton)
        userController = UserController(this)

        // Retrieve vendorId from the intent
        vendorId = intent.getStringExtra("vendorId") ?: ""

        // Retrieve the user ID from the user's profile (replace email with logged-in email)
        val loggedInEmail = getLoggedInUserEmail() // Replace this with a method to get the current logged-in email
        loadUserId(loggedInEmail)

        // Set up submit button listener
        submitButton.setOnClickListener {
            if (userId != null) {
                submitRating()
            } else {
                Toast.makeText(this, "Failed to load user ID", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadUserId(email: String) {
        // Call UserController to fetch the user data based on the email
        userController.getUserByEmail(email) { user: User?, errorMessage: String? ->
            if (user != null) {
                userId = user.id // Assuming `userId` is a field in the User model
                Toast.makeText(this, "User ID: $userId", Toast.LENGTH_SHORT).show() // Display the userId in a toast (for testing)
            } else {
                Toast.makeText(this, errorMessage ?: "Failed to load user ID", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun submitRating() {
        // Get the rating and comment entered by the user
        val rating = ratingBar.rating.toInt()
        val comment = commentInput.text.toString()

        // Create Review object
        if (userId != null) {
            val review = Review(id = null, userId!!, vendorId, rating, comment) // Ensure userId is not null

            // Post the review using the API
            ApiClient.reviewApi.postReview(review).enqueue(object : Callback<Review> {
                override fun onResponse(call: Call<Review>, response: Response<Review>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@VendorRatingActivity, "Review submitted successfully!", Toast.LENGTH_SHORT).show()
                        finish() // Close the activity and return to the previous screen
                    } else {
                        Toast.makeText(this@VendorRatingActivity, "Failed to submit review", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Review>, t: Throwable) {
                    Toast.makeText(this@VendorRatingActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "User ID is not available", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to retrieve the logged-in user's email from SharedPreferences
    private fun getLoggedInUserEmail(): String {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        return sharedPreferences.getString("logged_in_email", "") ?: ""
    }
}
