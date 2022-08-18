package com.bahram.weather.model

enum class RowType(val id: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
}

data class Header(
    val cityName: String?,
    val country: String?,
    val currentTemp: String?,
    val description: String?,
    val tempMin: String?,
    val tempMax: String?,
)

data class Hours(
    val hour: String?,
    val icon: String?,
    val temp: String?,
)

data class Days(
    val day: String?,
    val iconDay: String?,
    val tempMinDay: String?,
    val tempMaxDay: String?,
)

data class ViewFour(
    val speed: Double? = null,
    val deg: Int? = null,
    val gust: Double? = null,
)

data class Item(val type: RowType, val data: Any)









