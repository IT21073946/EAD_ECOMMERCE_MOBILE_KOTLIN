import com.example.ecommerceapp.models.Review
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewApi {

    @GET("api/Review/vendor/{vendorId}")
    fun getReviewsForVendor(@Path("vendorId") vendorId: String): Call<List<Review>>

    @POST("api/Review")
    fun postReview(@Body review: Review): Call<Review>
}
