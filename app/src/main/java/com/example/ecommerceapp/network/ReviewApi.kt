import com.example.ecommerceapp.models.Review
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewApi {

    @GET("api/Review/vendor/{vendorId}")
    fun getReviewsForVendor(@Path("vendorId") vendorId: String): Call<List<Review>>

    @POST("api/Review")
    fun postReview(@Body review: Review): Call<Review>

    @GET("api/Review")  // Update endpoint to fetch all reviews
    fun getAllReviews(): Call<List<Review>>

    // Add the update method
    @PUT("api/Review/{reviewId}")
    fun updateReview(@Path("reviewId") reviewId: String, @Body updatedReview: Review): Call<Review>

    // Add the delete method
    @DELETE("api/Review/{reviewId}")
    fun deleteReview(@Path("reviewId") reviewId: String): Call<Void>

}
