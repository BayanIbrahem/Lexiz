package com.dev.bayan.ibrahim.feature.library.domain.util

import kotlin.math.roundToInt

sealed class FuzzyPassedTime(val passedTime: String) {
    data object UnKnown : FuzzyPassedTime("")
    data object MomentsAgo : FuzzyPassedTime("")
    data class MinutesAgo(val minutesPassed: Int) : FuzzyPassedTime(minutesPassed.toString())
    data class HoursAgo(val hoursPassed: Int) : FuzzyPassedTime(hoursPassed.toString())

    data class DaysAgo(val daysPassed: Int) : FuzzyPassedTime(daysPassed.toString())
    data class WeeksAgo(val weeksPassed: Int) : FuzzyPassedTime(weeksPassed.toString())
    data class MonthsAgo(val monthsPassed: Int) : FuzzyPassedTime(monthsPassed.toString())
    data class YearsAgo(val yearsPassed: Int) : FuzzyPassedTime(yearsPassed.toString())

    data object LongTimeAgo : FuzzyPassedTime("")
}

fun Long?.toFuzzyPassedTime(currentTimeMillis: Long): FuzzyPassedTime {
    val milliDiff = currentTimeMillis - (this ?: return FuzzyPassedTime.UnKnown)

    val secDiff = milliDiff / 1000f
    if (secDiff < 60f) {
        return FuzzyPassedTime.MomentsAgo
    }

    val minDiff = secDiff / 60
    if (minDiff < 60f) {
        return FuzzyPassedTime.MinutesAgo(minDiff.roundToInt())
    }

    val hourDiff = minDiff / 60
    if (hourDiff < 24f) {
        return FuzzyPassedTime.HoursAgo(hourDiff.roundToInt())
    }

    val dayDiff = hourDiff / 24
    if (dayDiff < 7f) {
        return FuzzyPassedTime.DaysAgo(dayDiff.roundToInt())
    }

    val weekDiff = dayDiff / 7
    if (weekDiff < 4f) {
        return FuzzyPassedTime.WeeksAgo(weekDiff.roundToInt())
    }

    val monthDiff = weekDiff / 4
    if (monthDiff < 12f) {
        return FuzzyPassedTime.MonthsAgo(monthDiff.roundToInt())
    }

    val yearDiff = monthDiff / 12
    if (yearDiff < 10f) {
        return FuzzyPassedTime.YearsAgo(yearDiff.roundToInt())
    }

    return FuzzyPassedTime.LongTimeAgo
}