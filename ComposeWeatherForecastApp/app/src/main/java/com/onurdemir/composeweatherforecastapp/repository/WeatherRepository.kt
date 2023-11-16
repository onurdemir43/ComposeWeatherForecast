package com.onurdemir.composeweatherforecastapp.repository

import android.util.Log
import com.onurdemir.composeweatherforecastapp.data.DataOrException
import com.onurdemir.composeweatherforecastapp.model.Weather
import com.onurdemir.composeweatherforecastapp.model.WeatherObject
import com.onurdemir.composeweatherforecastapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(cityQuery: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery)
        }catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }
}