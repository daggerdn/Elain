package pl.devnowak.elain.model

interface NetworkClient {

    suspend fun getList(count: Int): List<ShelterEntity>

    suspend fun getDetails(): ShelterEntity
}