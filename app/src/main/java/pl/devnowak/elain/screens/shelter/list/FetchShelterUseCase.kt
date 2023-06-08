package pl.devnowak.elain.screens.shelter.list

import kotlinx.coroutines.delay
import pl.devnowak.elain.db.ShelterDao
import pl.devnowak.elain.model.NetworkClient
import pl.devnowak.elain.model.ShelterEntity

class FetchShelterUseCase(val client: NetworkClient, val dao: ShelterDao) {




    suspend fun fetch(count: Int): List<ShelterEntity> {
        delay(1000)
        val list: List<ShelterEntity> = client.getShelters(count)
        dao.insertAll(*list.toTypedArray())

        return list
    }
}