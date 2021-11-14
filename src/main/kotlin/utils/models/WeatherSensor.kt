package utils.models

import java.util.*

class WeatherSensor(
    weatherAccuracy: Double,
    weatherProvider: WeatherProvider
) {

    val weatherTime = (weatherAccuracy * weatherProvider.getWeatherTime().time).toLong()

    fun weatherCondition(time: Date): String {
        return if (time.time == weatherTime) {
            WeatherProvider.GOOD_WEATHER_CONDITION
        } else {
            WeatherProvider.TIME_ERROR
        }
    }
}