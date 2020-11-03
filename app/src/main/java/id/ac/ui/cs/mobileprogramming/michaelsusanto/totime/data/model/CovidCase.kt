package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model

data class CovidCase(
    val id: Int,
    val date: String,
    val death: Long,
    val recovered: Long,
    val positive: Long,
    val hospitalized: Long
)