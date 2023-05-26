package pl.devnowak.elain.usecases

import android.app.Application
import androidx.room.Room
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
import pl.devnowak.elain.db.AppDatabase
import pl.devnowak.elain.model.NetworkClient
import pl.devnowak.elain.model.ShelterEntity
import timber.log.Timber

class FetchShelterUseCase(val client: NetworkClient) {

    val db = Room.databaseBuilder(
        Application().applicationContext,
        AppDatabase::class.java, "shelterdb"
    ).build()


    suspend fun fetch(count: Int): List<ShelterEntity> {
        delay(1000)
        val list: List<ShelterEntity> = client.getList(count)
        val shelterDao = db.shelterDao()
        list.forEach {
            shelterDao.insertAll(it)
        }


        return list
    }
}