package pl.devnowak.elain.usecases

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import pl.devnowak.elain.model.ShelterEntity

class FetchShelterUseCase() {

    val data: List<ShelterEntity> = List(15) {
        ShelterEntity(
            id = it,
            name = "Name $it",
            description = "Description $it"
        )
    }

    suspend fun fetch(count: Int): List<ShelterEntity> {
        println("IN FETCH CLIENT")
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

        // count - ile schronisk pobrać?
        println("IN FETCH CALL")
        val response: HttpResponse = client.get("http://45.148.29.215/shelters/$count")
        // zaczekać 5 sekund
        println("RESPONSE: $response")
        delay(1000)

        // zwrócić listę
        println("IN FETCH LIST")
        val list: List<ShelterEntity> = response.body()
        client.close()

        return list
    }
}