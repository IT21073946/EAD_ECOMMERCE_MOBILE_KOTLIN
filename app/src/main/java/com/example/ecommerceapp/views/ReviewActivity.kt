package com.example.ecommerceapp.views

import ReviewAdapter
import ReviewApi
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.controllers.UserController
import com.example.ecommerceapp.models.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewActivity : AppCompatActivity() {

    private lateinit var reviewsRecyclerView: RecyclerView
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var reviewApi: ReviewApi
    private lateinit var userController: UserController
    private lateinit var userIdTextView: TextView  // TextView to display userId
    private lateinit var loggedInEmail: String  // To store logged-in user's email
    private lateinit var loggedInUserId: String // To store the logged-in user's ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        // Initialize views and controllers
        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView)
        userIdTextView = findViewById(R.id.userIdTextView)  // Initialize userId TextView
        userController = UserController(this)

        // Set up RecyclerView
        reviewsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize ReviewApi using Retrofit
        reviewApi = ApiClient.retrofit.create(ReviewApi::class.java)

        // Get the logged-in user's email
        loggedInEmail = getLoggedInUserEmail()

        // Load and display the user ID
        loadUserId(loggedInEmail)
    }

    // Function to fetch all reviews
    private fun fetchAllReviews(loggedInUserId: String) {
        reviewApi.getAllReviews().enqueue(object : Callback<List<Review>> {
            override fun onResponse(call: Call<List<Review>>, response: Response<List<Review>>) {
                if (response.isSuccessful) {
                    val reviews = response.body() ?: emptyList()

                    // Filter reviews where userId matches the logged-in user's ID
                    val filteredReviews = reviews.filter { it.userId == loggedInUserId }

                    // Set up the adapter with the filtered reviews
                    reviewAdapter = ReviewAdapter(
                        filteredReviews,
                        onEditClicked = { review -> showEditReviewDialog(review) },
                        onDeleteClicked = { review -> deleteReview(review) }
                    )
                    reviewsRecyclerView.adapter = reviewAdapter

                    // Show a message if no reviews are found for this user
                    if (filteredReviews.isEmpty()) {
                        Toast.makeText(this@ReviewActivity, "No reviews found for this user", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this@ReviewActivity, "Failed to load reviews", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Review>>, t: Throwable) {
                Toast.makeText(this@ReviewActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Function to show edit dialog for review
    @SuppressLint("MissingInflatedId")
    private fun showEditReviewDialog(review: Review) {
        // Create an AlertDialog builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Edit Review")

        // Inflate the custom layout for the dialog
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_review, null)
        builder.setView(dialogView)

        // Initialize the views from the dialog layout
        val ratingBar = dialogView.findViewById<RatingBar>(R.id.dialogRatingBar)
        val commentEditText = dialogView.findViewById<EditText>(R.id.dialogCommentEditText)

        // Set the current values of the review to the views
        ratingBar.rating = review.rating.toFloat()
        commentEditText.setText(review.comment)

        // Set up the buttons for the dialog
        builder.setPositiveButton("Update") { dialog, _ ->
            // Get the updated rating and comment
            val updatedRating = ratingBar.rating.toInt()
            val updatedComment = commentEditText.text.toString()

            // Update the review object
            val updatedReview = review.copy(rating = updatedRating, comment = updatedComment)

            // Call updateReview with the updated review
            updateReview(updatedReview)

            dialog.dismiss()  // Close the dialog
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()  // Close the dialog without making changes
        }

        // Show the dialog
        builder.create().show()
    }

    // Function to update a review
    private fun updateReview(review: Review) {
        reviewApi.updateReview(review.id.toString(), review).enqueue(object : Callback<Review> {
            override fun onResponse(call: Call<Review>, response: Response<Review>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ReviewActivity, "Review updated successfully!", Toast.LENGTH_SHORT).show()
                    fetchAllReviews(loggedInUserId)  // Refresh the reviews list
                } else {
                    Toast.makeText(this@ReviewActivity, "Failed to update review", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Review>, t: Throwable) {
                Toast.makeText(this@ReviewActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Function to delete a review
    private fun deleteReview(review: Review) {
        review.id?.let {
            reviewApi.deleteReview(it).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ReviewActivity, "Review deleted successfully!", Toast.LENGTH_SHORT).show()
                        fetchAllReviews(loggedInUserId)  // Refresh the reviews list
                    } else {
                        Toast.makeText(this@ReviewActivity, "Failed to delete review", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@ReviewActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }


    // Function to retrieve logged-in user email from SharedPreferences
    private fun getLoggedInUserEmail(): String {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("logged_in_email", "") ?: ""
    }

    // Function to load and display the user ID based on email
    private fun loadUserId(email: String) {
        userController.getUserByEmail(email) { user, message ->
            if (user != null) {
                // Store the logged-in user's ID
                loggedInUserId = user.id.toString()
                userIdTextView.text = "User ID: ${user.id}"

                // Fetch and filter reviews based on this userId
                fetchAllReviews(loggedInUserId)
            } else {
                Toast.makeText(this, message ?: "Failed to load user ID", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
