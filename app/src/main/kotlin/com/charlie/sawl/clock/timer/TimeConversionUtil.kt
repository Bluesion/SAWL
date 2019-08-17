package com.charlie.sawl.clock.timer

object TimeConversionUtil {
    fun getTimeStringFromMilliseconds(milliseconds: Long): String {
        var seconds = Integer.toString(getSecondsFromMilliseconds(milliseconds))
        var minutes = Integer.toString(getMinutesFromMilliseconds(milliseconds))
        var hours = Integer.toString(getHoursFromMilliseconds(milliseconds))
        for (i in 0..1) {
            if (seconds.length < 2) {
                seconds = "0$seconds"
            }
            if (minutes.length < 2) {
                minutes = "0$minutes"
            }
            if (hours.length < 2) {
                hours = "0$hours"
            }
        }
        return "$hours:$minutes:$seconds"
    }

    fun getHoursFromMilliseconds(milliseconds: Long): Int {
        val time = milliseconds / 1000
        return (time / 3600).toInt()
    }

    fun getMinutesFromMilliseconds(milliseconds: Long): Int {
        val time = milliseconds / 1000
        return (time % 3600 / 60).toInt()
    }

    fun getSecondsFromMilliseconds(milliseconds: Long): Int {
        val time = milliseconds / 1000
        return (time % 60).toInt()
    }

    fun convertStringToMilliseconds(time: String): Long {
        val seconds = getSecondsFromTimeString(time)
        val minutes = getMinutesFromTimeString(time)
        val hours = getHoursFromTimeString(time)
        return (seconds * 1000 + minutes * 60 * 1000 + hours * 60 * 60 * 1000).toLong()
    }

    fun getHoursFromTimeString(time: String): Int {
        return if (time.length > 4) {
            Integer.parseInt(time.substring(0, time.length - 4))
        } else {
            0
        }
    }

    fun getMinutesFromTimeString(time: String): Int {
        return if (time.length > 2) {
            if (time.length >= 4) {
                Integer.parseInt(time.substring(time.length - 4, time.length - 2))
            } else {
                Integer.parseInt(time.substring(0, time.length - 2))
            }
        } else {
            0
        }
    }

    fun getSecondsFromTimeString(time: String): Int {
        return when {
            time.length >= 2 -> Integer.parseInt(time.substring(time.length - 2))
            time.isNotEmpty() -> Integer.parseInt(time)
            else -> 0
        }
    }
}
