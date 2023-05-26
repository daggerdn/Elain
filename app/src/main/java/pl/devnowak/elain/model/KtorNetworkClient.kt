package pl.devnowak.elain.model

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class KtorNetworkClient : NetworkClient {

    val client = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    override suspend fun getList(count: Int): List<ShelterEntity> {
        return client.get("http://45.148.29.215/shelters/$count").body()
    }

    override suspend fun getDetails(): ShelterEntity {
        TODO("Not yet implemented")
    }
}