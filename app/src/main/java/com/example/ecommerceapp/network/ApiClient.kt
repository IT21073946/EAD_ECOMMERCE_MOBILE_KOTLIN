import com.example.ecommerceapp.AppContext
import com.example.ecommerceapp.controllers.UserController
import com.example.ecommerceapp.network.OrderApi
import com.example.ecommerceapp.network.ProductApi
import com.example.ecommerceapp.network.UserApi
import com.example.ecommerceapp.network.VendorApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://f6be-2402-4000-1200-5466-a91c-3b5c-3105-f2a2.ngrok-free.app"


    // Retrieve JWT token from shared preferences
    private val userController: UserController by lazy { UserController(AppContext.get()) }
    private val token: String? by lazy { userController.getToken() }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
        token?.let {
            request.addHeader("Authorization", "Bearer $it")
        }
        chain.proceed(request.build())
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(authInterceptor)
        .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userApi: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }
    
        // Initialize the Product API
    val productApi: ProductApi by lazy {
        retrofit.create(ProductApi::class.java)
    }

    // Initialize the Review API
    val reviewApi: ReviewApi by lazy {
        retrofit.create(ReviewApi::class.java)
    }

    // Initialize the Order API
    val orderApi: OrderApi by lazy {
        retrofit.create(OrderApi::class.java)
    }

    val vendorApi: VendorApi by lazy {
        retrofit.create(VendorApi::class.java)
    }
}

