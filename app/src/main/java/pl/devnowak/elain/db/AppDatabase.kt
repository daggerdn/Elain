package pl.devnowak.elain.db

import android.app.Application
import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.devnowak.elain.model.KennelEntity
import pl.devnowak.elain.model.ShelterEntity

@Database(entities = [
    ShelterEntity::class,
    KennelEntity::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shelterDao(): ShelterDao
    abstract fun kennelDao(): KennelDao
}


class RoomDatabase(applicationContext: Context) {
    private val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "shelterdb"
    ).build()

    fun getShelterDao(): ShelterDao {
        return db.shelterDao()
    }

    fun getKennelDao(): KennelDao {
        return db.kennelDao()
    }
}
