package com.onurdemir.composeweatherforecastapp.screens.main

import android.annotation.SuppressLint
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.onurdemir.composeweatherforecastapp.data.DataOrException
import com.onurdemir.composeweatherforecastapp.model.Weather

@Composable
fun MainScreen(navController: NavController,
               mainViewModel: MainViewModel = hiltViewModel()) {
    ShowData(mainViewModel)
}

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ShowData(mainViewModel: MainViewModel) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)) {
    value = mainViewModel.getWeatherData("Eskişehir")
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    }else if(weatherData.data != null) {
        Text(text = "MainScreen ${weatherData.data.toString()}")
    }

}