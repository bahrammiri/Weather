package com.bahram.weather

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather.adapter.FragmentSplashAdapter
import com.bahram.weather.model.*
import com.bahram.weather.retrofit.Constants
import com.bahram.weather.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentSplash : Fragment()  {

    lateinit var rvFragmentSplash: RecyclerView
    lateinit var fragmentSplashAdapter: FragmentSplashAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val viewFragment = LayoutInflater.from(requireContext()).inflate(R.layout.splash_fragment,container,false)

        rvFragmentSplash = viewFragment.findViewById(R.id.rv_fragment_splash)

        return viewFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var value2 = arguments?.getString("YourKey2", "Default String")
        getCityWeather(value2.toString())
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


//
//        @Parcelize
//        data class Sports(
//        val id: Int,
//        val banner: Int,
//        val title: String,
//        val subtitle: String,
//        val about: String
//    ) :
//
//        Parcelable {
//
//        companion object {
//            fun sportsList(context: Context): List<Sports> {
//                return listOf(
//                    Sports(
//                        0, R.drawable.ic_rugby,
//                        context.getString(R.string.title_rugby),
//                        context.getString(R.string.subtitle_rugby),
//                        context.getString(R.string.about_rugby)
//                    ),
//                    Sports(
//                        1, R.drawable.ic_cricket,
//                        context.getString(R.string.title_cricket),
//                        context.getString(R.string.subtitle_cricket),
//                        context.getString(R.string.about_cricket)
//                    ),
//
//
//                )
//            }
//        }
//    }




    @RequiresApi(Build.VERSION_CODES.O)
    fun setDataRecyclerView(response: WeatherResponse?) {

        val partOne = viewOne(response)
        val partTwo = viewTwo(response)
        val partThree = viewThree(response)


        val itemTotal = arrayListOf<Item>()
        itemTotal.add(Item(RowType.ONE, partOne))
        itemTotal.add(Item(RowType.TWO, partTwo))
        itemTotal.add(Item(RowType.THREE, partThree))



        fragmentSplashAdapter = FragmentSplashAdapter(requireContext(), itemTotal)
        rvFragmentSplash.adapter = fragmentSplashAdapter
        rvFragmentSplash.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
//        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL


//        fragmentSplashAdapter.notifyDataSetChanged()


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