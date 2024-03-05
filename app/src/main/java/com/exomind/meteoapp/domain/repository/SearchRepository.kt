package com.exomind.meteoapp.domain.repository

import com.exomind.meteoapp.common.Config
import com.exomind.meteoapp.domain.models.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchRepository {
    /**
     * Method to get weather
     * **/
    @GET(Config.Endpoint.Meteo.weather)
    @Headers("Content-Type.kt: application/json;charset=UTF-8")
    fun getWeather(
        @Query("q") state: String,
        @Query("APPID") appID: String
    ) : Call<Weather>
}