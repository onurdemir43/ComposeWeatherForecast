package com.onurdemir.composeweatherforecastapp.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.onurdemir.composeweatherforecastapp.R
import com.onurdemir.composeweatherforecastapp.data.DataOrException
import com.onurdemir.composeweatherforecastapp.model.Weather
import com.onurdemir.composeweatherforecastapp.model.WeatherItem
import com.onurdemir.composeweatherforecastapp.utils.formatDate
import com.onurdemir.composeweatherforecastapp.utils.formatDateTime
import com.onurdemir.composeweatherforecastapp.utils.formatDecimals
import com.onurdemir.composeweatherforecastapp.widgets.HumidityWindPressureRow
import com.onurdemir.composeweatherforecastapp.widgets.SunRow
import com.onurdemir.composeweatherforecastapp.widgets.WeatherAppBar
import com.onurdemir.composeweatherforecastapp.widgets.WeatherDetailRow
import com.onurdemir.composeweatherforecastapp.widgets.WeatherStateImage

@Composable
fun MainScreen(navController: NavController,
               mainViewModel: MainViewModel = hiltViewModel()) {

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)) {
        value = mainViewModel.getWeatherData("izmir")
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
        WeatherAppBar(title = weather.city.name + ", ${weather.city.country}",
        icon = Icons.Default.ArrowBack,
            navController = navController){
            navController.popBackStack()
        }
    }) {
            Surface(modifier = Modifier.padding(top = 60.dp)) {
                Divider()
                MainContent(data = weather)

            }


    }


}

@Composable
fun MainContent(data: Weather) {
    val weatherItem = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = formatDate(weatherItem.dt),
        fontSize = 15.sp,
        color = Color.DarkGray,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(6.dp))

        Surface(modifier = Modifier
            .padding()
            .size(200.dp),
            shape = CircleShape,
        color = Color(0xFFFFC400)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl)
                Text(text = formatDecimals(weatherItem.temp.day) + "\u00B0",fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
                Text(text = weatherItem.weather[0].main, fontSize = 20.sp, fontStyle = FontStyle.Italic)
            }        

        }
        HumidityWindPressureRow(weather = weatherItem)
        Divider()
        SunRow(weather = weatherItem)
        Text(text = "This Week",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold)
        
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(2.dp),
            contentPadding = PaddingValues(0.dp)) {
                items(items = data.list) {
                    WeatherDetailRow(weather = it)
                }
            }
        }
    }

}






