package pl.devnowak.elain.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pl.devnowak.elain.model.ShelterEntity

@Dao
interface ShelterDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<ShelterEntity>

    @Query("SELECT * FROM shelter WHERE id IN (:shelterIds)")
    fun loadAllByIds(shelterIds: IntArray): List<ShelterEntity>

    @Query("SELECT * FROM shelter WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): ShelterEntity

    @Insert
    fun insertAll(vararg users: ShelterEntity)

    @Delete
    fun delete(user: ShelterEntity)
}
