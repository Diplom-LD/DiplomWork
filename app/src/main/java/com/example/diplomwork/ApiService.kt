import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/auth/register")
    suspend fun register(@Body user: User): Response<ApiResponse>

    @POST("/auth/login")
    suspend fun login(@Body user: User): Response<ApiResponse>
}

// Data models
data class User(val email: String, val password: String)
data class ApiResponse(val success: Boolean, val message: String)

// Retrofit instance
object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://your-api-url.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
