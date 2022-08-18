package com.bahram.weather.retrofit


import com.bahram.weather.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

//    @GET()
//    fun photo()
//
//    @GET("/photos")
//    fun photo(): Call<WeatherList<PhotoModel>>
//
//    @GET("weathers")
//    fun getCurrentWeather(
//        @Query("lat") latitude: Double,
//        @Query("lon") longitude: Double,
//        @Query("appid") api_key: String,
//    ): Call<WeatherResponse>

    //api.openweathermap.org/data/2.5/forecast?q={city name}&appid={API key}
    //https://api.openweathermap.org/data/2.5/forecast?q=London&appid=9df97601ed495d20b3c3bb2b45b6c646

    @GET("forecast")
    fun getCityWeatherData(
        @Query("q") cityName: String,
        @Query("appid") api_key: String,
        @Query("units") units: String,

        ): Call<WeatherResponse>

}