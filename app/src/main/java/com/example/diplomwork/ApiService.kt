import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.gson.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ApiService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson()
        }
    }

    private const val BASE_URL = "http://10.0.2.2:5001/auth"

    suspend fun register(user: User): ApiResponse? {
        return try {
            client.post("$BASE_URL/sign-up") {
                setBody(user)
            }.body()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun login(user: User): ApiResponse? {
        return try {
            client.post("$BASE_URL/sign-in") {
                contentType(ContentType.Application.Json)
                setBody(user)
            }
                .body()
        } catch (e: Exception) {
            null
        }
    }
}

// Data models
data class User(val identifier: String, val password: String)
data class ApiResponse(val accessToken: String)