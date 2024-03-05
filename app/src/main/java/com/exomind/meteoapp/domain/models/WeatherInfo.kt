package com.exomind.meteoapp.domain.models

data class WeatherInfo(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)