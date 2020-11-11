package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "CovidCases")
data class CovidCase(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "date") val date: String = "Date",
    @ColumnInfo(name = "country") @Json(name = "name") val country: String = "Indonesia",
    @ColumnInfo(name = "death") @Json(name = "meninggal") val death: String,
    @ColumnInfo(name = "recovered") @Json(name = "sembuh") val recovered: String,
    @ColumnInfo(name = "positive") @Json(name = "positif") val positive: String,
    @ColumnInfo(name = "hospitalized") @Json(name = "dirawat") val hospitalized: String
) : Parcelable