package pl.devnowak.elain.db

import androidx.room.*
import pl.devnowak.elain.model.ShelterEntity

@Dao
interface ShelterDao {
    @Query("SELECT * FROM shelter")
    fun getAll(): List<ShelterEntity>

    @Query("SELECT * FROM shelter WHERE id = :shelterId")
    fun loadAllById(shelterId: Int): ShelterEntity?

    @Query("SELECT * FROM shelter WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): ShelterEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg shelters: ShelterEntity)

    @Delete
    fun delete(shelter: ShelterEntity)
}
