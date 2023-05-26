package pl.devnowak.elain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.devnowak.elain.model.ShelterEntity

@Database(entities = [ShelterEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shelterDao(): ShelterDao
}
