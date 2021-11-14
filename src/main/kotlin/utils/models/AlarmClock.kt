package utils.models

import java.util.*

class AlarmClock(
    watchAccuracy: Double
) {
    var alarmAt: Date? = null
    val watchTime = Date((System.currentTimeMillis() * watchAccuracy).toLong())
    fun setUpAlarm(newTime: Date) {
        alarmAt = newTime
    }
}