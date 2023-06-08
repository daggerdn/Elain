package pl.devnowak.elain.db

import androidx.room.*
import pl.devnowak.elain.model.KennelEntity
import pl.devnowak.elain.model.ShelterEntity

@Dao
interface KennelDao {

    @Query("SELECT * FROM kennel")
    fun getAll(): List<KennelEntity>

    @Query("SELECT * FROM kennel WHERE id = :kennelId")
    fun loadAllById(kennelId: Int): KennelEntity?

    @Query("SELECT * FROM kennel WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): KennelEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg kennels: KennelEntity)

    @Delete
    fun delete(kennel: KennelEntity)
}