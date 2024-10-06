import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.R
import com.example.ecommerceapp.models.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewController : AppCompatActivity() {
    private lateinit var ratingBar: RatingBar
    private lateinit var commentInput: EditText
    private lateinit var submitButton: Button
    private lateinit var reviewApi: ReviewApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor_rating)

        ratingBar = findViewById(R.id.ratingBar)
        commentInput = findViewById(R.id.commentInput)
        submitButton = findViewById(R.id.submitButton)

        reviewApi = ApiClient.retrofit.create(ReviewApi::class.java)

        submitButton.setOnClickListener {
            submitReview()
        }
    }

    private fun submitReview() {
        val rating = ratingBar.rating.toInt()
        val comment = commentInput.text.toString()
        val userId = "66fdf2312956d4cb29ac6fb7" // Replace with actual user ID
        val vendorId = "66edabc4692d23e8ebfdec69" // Replace with actual vendor ID

        val newReview = Review(userId, vendorId, rating, comment)

        reviewApi.postReview(newReview).enqueue(object : Callback<Review> {
            override fun onResponse(call: Call<Review>, response: Response<Review>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ReviewController, "Review submitted!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ReviewController, "Failed to submit review", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Review>, t: Throwable) {
                Toast.makeText(this@ReviewController, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
