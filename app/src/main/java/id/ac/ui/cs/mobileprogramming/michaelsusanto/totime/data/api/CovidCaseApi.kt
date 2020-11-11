package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.api

import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase
import retrofit2.http.GET

interface CovidCaseApi {

    @GET("indonesia")
    suspend fun getCovidCase(): List<CovidCase>
}