import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.models.Review

class ReviewAdapter(
    private val reviews: List<Review>,
    private val onEditClicked: (Review) -> Unit,
    private val onDeleteClicked: (Review) -> Unit
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val reviewRatingBar: RatingBar = view.findViewById(R.id.reviewRatingBar)  // RatingBar to display stars
        val reviewComment: TextView = view.findViewById(R.id.reviewComment)  // TextView to display comment
        val editButton: Button = view.findViewById(R.id.editButton)  // Button to edit the review
        val deleteButton: Button = view.findViewById(R.id.deleteButton)  // Button to delete the review
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]

        // Bind the rating to the RatingBar
        holder.reviewRatingBar.rating = review.rating.toFloat()

        // Bind the comment to the TextView
        holder.reviewComment.text = review.comment


        // Handle the Edit button click
        holder.editButton.setOnClickListener {
            onEditClicked(review)
        }

        // Handle the Delete button click
        holder.deleteButton.setOnClickListener {
            onDeleteClicked(review)
        }

    }

    override fun getItemCount(): Int {
        return reviews.size
    }
}
