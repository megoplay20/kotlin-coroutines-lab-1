package com.example.lab1_kotlincoroutineslab.models.extensions

import com.example.lab1_kotlincoroutineslab.models.DateProcessor
import com.example.lab1_kotlincoroutineslab.models.DisplayHoliday
import com.example.lab1_kotlincoroutineslab.models.Holiday
import com.example.lab1_kotlincoroutineslab.models.LongestHoliday
import com.example.lab1_kotlincoroutineslab.models.ServiceUtils.getMaxConsecutiveDays
import java.util.*

fun Holiday.convertToDisplay(dateFormatter: (date: Date) -> String, dateParser: (strDate: String) -> Date): DisplayHoliday {
    val displayHoliday =
        DisplayHoliday(
            this.localName,
            this.countryCode
        )
    return displayHoliday.apply {
        englishName = this@convertToDisplay.name
        launchYear = this@convertToDisplay.launchYear
        type = this@convertToDisplay.type
        date = dateParser(this@convertToDisplay.date)
        description = "Праздник: ${this.localName} Дата: ${dateFormatter(this.date)} ${if (this@convertToDisplay.global) "Мировой" else "Локальный"}"
    }
}

fun List<Holiday>.getDisplayHolidayList(): List<DisplayHoliday> {
    return this.map { it.convertToDisplay({ DateProcessor formatDate it },
        { DateProcessor parseDate it }) }
}

fun List<DisplayHoliday>.stats():List<DisplayHoliday>?  {
    return this.groupBy { it.localName }
        .maxBy { getMaxConsecutiveDays(it.value.map { it.date }) ?: 0L }
        ?.value?.sortedBy { it.date }}

fun List<DisplayHoliday>.getLongestHoliday(): LongestHoliday {
    val holidayStats = this.stats()
    return LongestHoliday(
        holidayStats?.first()?.countryCode,
        holidayStats?.first()?.localName,
        holidayStats?.first()?.date,
        holidayStats?.last()?.date,
        0,
        holidayStats?.map { it.date })
}