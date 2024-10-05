import com.example.ecommerceapp.AppContext
import com.example.ecommerceapp.controllers.UserController
import com.example.ecommerceapp.network.UserApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://8dec-103-21-166-85.ngrok-free.app"

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

    private val retrofit: Retrofit by lazy {
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

}