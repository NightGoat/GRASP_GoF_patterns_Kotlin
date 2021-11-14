package utils.models

import java.util.*

class WeatherProvider {
    fun getWeatherTime() = Date()
    fun getWeatherCondition(time: Date): String {
        return if (time == getWeatherTime()) {
            GOOD_WEATHER_CONDITION
        } else {
            TIME_ERROR
        }
    }

    companion object {
        const val GOOD_WEATHER_CONDITION = "It's Always Sunny in Philadelphia"
        const val TIME_ERROR = "NO CONDITION FOR THIS TIME"
    }
}