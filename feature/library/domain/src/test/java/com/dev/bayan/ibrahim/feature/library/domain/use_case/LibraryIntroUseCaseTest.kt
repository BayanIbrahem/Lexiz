package com.dev.bayan.ibrahim.feature.library.domain.use_case

import com.dev.bayan.ibrahim.feature.library.domain.util.FuzzyPassedTime
import com.dev.bayan.ibrahim.feature.library.domain.util.toFuzzyPassedTime
import org.junit.Assert.*

import org.junit.Test

class LibraryIntroUseCaseTest {

    private val currentTimeMills = 1_000_000_000L

    @Test
    fun libraryIntroUseCase_momentsPassedTime_returnMomentsObject() {
        val targetTime = currentTimeMills - 1_000L
        val fuzzyPassedTime = targetTime.toFuzzyPassedTime(currentTimeMills)
        assertEquals(FuzzyPassedTime.MomentsAgo, fuzzyPassedTime)
    }

    @Test
    fun libraryIntroUseCase_minutesPassedTime_returnMinutesObject() {
        val targetTime = currentTimeMills - 1_000L * 60
        val fuzzyPassedTime = targetTime.toFuzzyPassedTime(currentTimeMills)
        assertEquals(FuzzyPassedTime.MinutesAgo(1), fuzzyPassedTime)
    }

    @Test
    fun libraryIntroUseCase_hoursPassedTime_returnHoursObject() {
        val targetTime = currentTimeMills - 1_000L * 60 * 60
        val fuzzyPassedTime = targetTime.toFuzzyPassedTime(currentTimeMills)
        assertEquals(FuzzyPassedTime.HoursAgo(1), fuzzyPassedTime)
    }

    @Test
    fun libraryIntroUseCase_daysPassedTime_returnDaysObject() {
        val targetTime = currentTimeMills - 1_000L * 60 * 60 * 24
        val fuzzyPassedTime = targetTime.toFuzzyPassedTime(currentTimeMills)
        assertEquals(FuzzyPassedTime.DaysAgo(1), fuzzyPassedTime)
    }

    @Test
    fun libraryIntroUseCase_weeksPassedTime_returnWeeksObject() {
        val targetTime = currentTimeMills - 1_000L * 60 * 60 * 24 * 7
        val fuzzyPassedTime = targetTime.toFuzzyPassedTime(currentTimeMills)
        assertEquals(FuzzyPassedTime.WeeksAgo(1), fuzzyPassedTime)
    }

    @Test
    fun libraryIntroUseCase_monthsPassedTime_returnMonthsObject() {
        val targetTime = currentTimeMills - 1_000L * 60 * 60 * 24 * 7 * 4
        val fuzzyPassedTime = targetTime.toFuzzyPassedTime(currentTimeMills)
        assertEquals(FuzzyPassedTime.MonthsAgo(1), fuzzyPassedTime)
    }

    @Test
    fun libraryIntroUseCase_yearsPassedTime_returnYearsObject() {
        val targetTime = currentTimeMills - 1_000L * 60 * 60 * 24 * 7 * 4 * 12
        val fuzzyPassedTime = targetTime.toFuzzyPassedTime(currentTimeMills)
        assertEquals(FuzzyPassedTime.YearsAgo(1), fuzzyPassedTime)
    }

    @Test
    fun libraryIntroUseCase_longPassedTime_returnLongObject() {
        val targetTime = currentTimeMills - 1_000L * 60 * 60 * 24 * 7 * 4 * 12 * 100
        val fuzzyPassedTime = targetTime.toFuzzyPassedTime(currentTimeMills)
        assertEquals(FuzzyPassedTime.LongTimeAgo, fuzzyPassedTime)
    }
}