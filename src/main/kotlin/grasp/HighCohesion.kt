package grasp

import utils.models.AlarmClock
import utils.models.WeatherProvider
import utils.models.WeatherSensor
import java.util.*

/**
 * High cohesion is an evaluative pattern that attempts to keep objects appropriately focused, manageable and
 * understandable. High cohesion is generally used in support of low coupling. High cohesion means that the
 * responsibilities of a given element are strongly related and highly focused. Breaking programs into classes
 * and subsystems is an example of activities that increase the cohesive properties of a system. Alternatively,
 * low cohesion is a situation in which a given element has too many unrelated responsibilities. Elements with low
 * cohesion often suffer from being hard to comprehend, reuse, maintain and change
 * */


/** This code has low cohesion because it hase two Entities: Clock and Weather sensor. If someone would like to make
 * Weather Sensor, it would be difficult to untie everything, so programmer would simply copypaste your code,
 * so you would have 2 classes with same code, and if you would fix bugs in your code, this will not affect
 * copypasted code.
 * */
interface LowCohesionExample

class BadSmartWatch(
    watchAccuracy: Double,
    weatherAccuracy: Double,
    weatherProvider: WeatherProvider
) : LowCohesionExample {
    var alarmAt: Date? = null

    val watchTime = System.currentTimeMillis() * watchAccuracy
    val weatherTime = weatherProvider.getWeatherTime().time * weatherAccuracy

    val weatherCondition: String
        get() = if (watchTime == weatherTime) {
            GOOD_WEATHER_CONDITION
        } else {
            TIME_ERROR
        }

    fun setUpAlarm(newTime: Date) {
        alarmAt = newTime
    }

    companion object {
        const val GOOD_WEATHER_CONDITION = "It's Always Sunny in Philadelphia"
        const val TIME_ERROR = "NO CONDITION FOR THIS TIME"
    }
}

interface HighCohesionExample

/** Single responsibility in work: no code dublication and every bug fix in clock and weather sensor affects smart watch */
class GoodSmartWatch(
    private val clock: AlarmClock,
    private val weatherSensor: WeatherSensor,
) : HighCohesionExample {
    var alarmAt: Date? = clock.alarmAt
    val watchTime = clock.watchTime
    val weatherTime = weatherSensor.weatherTime
    val weatherCondition: String
        get() = weatherSensor.weatherCondition(watchTime)

    fun setUpAlarm(newTime: Date) {
        clock.setUpAlarm(newTime)
    }
}