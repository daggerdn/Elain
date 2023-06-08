package pl.devnowak.elain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Entity(tableName = "kennel")
data class KennelEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name ="name") val name: String,
    @ColumnInfo(name = "description") val description: String
): Parcelable
