package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model

data class UserActivity(
    val id: Int,
    val name: String,
    val hours: Int,
    val minutes: Int,
    val seconds: Int,
    val dateStart: String,
    val place: String
)