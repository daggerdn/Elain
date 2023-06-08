package pl.devnowak.elain.screens.kennel.list

import kotlinx.coroutines.delay
import pl.devnowak.elain.db.KennelDao
import pl.devnowak.elain.model.KennelEntity
import pl.devnowak.elain.model.NetworkClient

class FetchKennelUseCase(val client: NetworkClient, val dao: KennelDao) {

    suspend fun fetch(count: Int): List<KennelEntity> {
        delay(1000)
        val list: List<KennelEntity> = client.getKennels(count)
        dao.insertAll(*list.toTypedArray())

        return list
    }
}