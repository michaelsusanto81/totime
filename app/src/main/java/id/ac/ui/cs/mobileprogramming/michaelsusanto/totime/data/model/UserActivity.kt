package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "UserActivities")
data class UserActivity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "hours") val hours: Int,
    @ColumnInfo(name = "minutes") val minutes: Int,
    @ColumnInfo(name = "seconds") val seconds: Int,
    @ColumnInfo(name = "dateStart") var dateStart: String,
    @ColumnInfo(name = "place") val place: String
) : Parcelable