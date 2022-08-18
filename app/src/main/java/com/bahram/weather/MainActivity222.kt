package com.bahram.weather

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bahram.weather.adapter.ViewPagerAdapter
import com.bahram.weather.model.*
import com.bahram.weather.retrofit.Constants
import com.bahram.weather.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity222 : AppCompatActivity() {

    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var viewPager2: ViewPager2
    var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main222)

        viewPager2 = findViewById(R.id.view_pager_two)

//        var id: Int = intent.getIntExtra("int", 0)
        var cityName3 = intent.getStringExtra("rec")

        Log.i("kkkk", "$cityName3")
        if (cityName3 != null) {
            getCityWeather(cityName3)
        }

    }


    private fun getCityWeather(city: String) {
        RetrofitService.getInstance().getCityWeatherData(city, api_key = Constants.API_KEY, units = Constants.UNITS).enqueue(object :
            Callback<WeatherResponse> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {

                val responseBody = response.body()
                if (responseBody != null) {
                    setDataRecyclerView(responseBody)

                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setDataRecyclerView(response: WeatherResponse?) {

        val partOne = viewOne(response)
        val partTwo = viewTwo(response)
        val partThree = viewThree(response)


        val itemTotal = arrayListOf<Item>()
        itemTotal.add(Item(RowType.ONE, partOne))
        itemTotal.add(Item(RowType.TWO, partTwo))
        itemTotal.add(Item(RowType.THREE, partThree))



        viewPagerAdapter = ViewPagerAdapter(this, itemTotal)
        viewPager2.adapter = viewPagerAdapter
//        viewPager2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        viewPager2.setCurrentItem(id ?: 0, false)


    }


    fun viewOne(response: WeatherResponse?): Header {
        val tempCurrent = response?.list?.getOrNull(0)?.main?.temp ?: 0.0
        val tempMin1 = response?.list?.getOrNull(0)?.main?.tempMin ?: 0.0
        val tempMax1 = response?.list?.getOrNull(0)?.main?.tempMax ?: 0.0

        val partOne = Header(response?.city?.name ?: "*",
            response?.city?.country ?: "*",
            tempCurrent.toString().substringBefore(".") + "°",
            response?.list?.getOrNull(0)?.weather?.getOrNull(0)?.description?.capitalize(),
            tempMin1.toString().substringBefore(".") + "°",
            tempMax1.toString().substringBefore(".") + "°")
        return partOne
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun viewTwo(response: WeatherResponse?): ArrayList<Hours> {
        val partTwo = arrayListOf<Hours>()
        response?.list?.forEach {
            val hour2 = Util.timeStampToLocalHour(it.date ?: 0)

            val iconCode = it.weather?.getOrNull(0)?.icon
            val url = "https://openweathermap.org/img/wn/$iconCode@2x.png"

            val temp2 = it.main?.temp ?: 0.0

            partTwo.add(Hours(hour2, url, temp2.toString().substringBefore(".") + "°"))
        }
        return partTwo
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun viewThree(response: WeatherResponse?): ArrayList<Days> {
        val partThree = arrayListOf<Days>()
        response?.list?.forEachIndexed { index, weatherList ->

            if ((index == 2) or (index == 9) or (index == 17) or (index == 25) or (index == 33)) {
                val dayName = Util.timeStampToLocalDay(weatherList.date ?: 0).substring(0, 3).capitalize()
                val iconCode = weatherList.weather?.getOrNull(0)?.icon
                val url = "https://openweathermap.org/img/wn/$iconCode@2x.png"

                val tempMin3 = weatherList.main?.tempMin ?: 0.0

                val tempMax3 = weatherList.main?.tempMax ?: 0.0
                partThree.add(Days(dayName, url, tempMin3.toString().substringBefore(".") + "°", tempMax3.toString().substringBefore(".") + "°"))
            }
        }
        return partThree
    }


}