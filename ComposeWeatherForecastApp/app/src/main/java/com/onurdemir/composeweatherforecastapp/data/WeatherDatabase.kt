package com.onurdemir.composeweatherforecastapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.onurdemir.composeweatherforecastapp.model.Favorite
import com.onurdemir.composeweatherforecastapp.model.Unit

@Database([Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}