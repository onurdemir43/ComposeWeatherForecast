package com.onurdemir.composeweatherforecastapp.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.onurdemir.composeweatherforecastapp.data.DataOrException
import com.onurdemir.composeweatherforecastapp.model.Weather
import com.onurdemir.composeweatherforecastapp.widgets.WeatherAppBar

@Composable
fun MainScreen(navController: NavController,
               mainViewModel: MainViewModel = hiltViewModel()) {

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)) {
        value = mainViewModel.getWeatherData("Eskişehir")
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    }else if(weatherData.data != null) {
        MainScaffold(weather = weatherData.data!!, navController)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ProduceStateDoesNotAssignValue", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    
    Scaffold(topBar = {
        WeatherAppBar(title = weather.city.name + ",${weather.city.country}",
        icon = Icons.Default.ArrowBack,
            navController = navController){
            Log.d("TAG", "MainScaffold: Button Clicked")
        }
    }) {
        Surface(modifier = Modifier.padding(100.dp)) {
            MainContent(data = weather)
        }

    }


}

@Composable
fun MainContent(data: Weather) {
    Text(text = data.city.name)

}
