package pl.devnowak.elain.model

interface NetworkClient {

    suspend fun getShelters(count: Int): List<ShelterEntity>
    suspend fun getKennels(count: Int): List<KennelEntity>

    suspend fun getDetails(): ShelterEntity
}