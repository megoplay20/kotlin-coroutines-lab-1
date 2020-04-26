package com.example.lab1_kotlincoroutineslab.models

import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

//https://developer.android.com/kotlin/ktx

class DateProcessor{
    companion object {
        private val formatter: SimpleDateFormat by lazy { SimpleDateFormat("dd.MM.yyyy") }
        private val parser: SimpleDateFormat by lazy { SimpleDateFormat("yyyy-MM-dd") }
        infix fun formatDate(d: Date) = formatter.format(d)
        infix fun parseDate(d:String) = parser.parse(d)
    }
}

object ServiceUtils {

    fun formServiceUrl(year: Int, countryCode: String): String {
        return "https://date.nager.at/api/v2/publicholidays/$year/$countryCode"
    }

    fun getMaxConsecutiveDays(dates: List<Date>): Long? {
        return ((0 until dates.count() - 1)
            .map { Duration.between(dates[it].toInstant(), dates[it + 1].toInstant()).toDays() }
            .fold(mutableListOf<Pair<Long, Boolean>>(), { dataList, element ->
                dataList.apply {
                    if (dataList.isEmpty()) {
                        dataList.add(1L to (element == 1L))
                    } else {
                        if (element == 1L) {
                            if (dataList.last().second) {
                                dataList[dataList.lastIndex] = dataList.last().first + 1 to true
                            } else {
                                dataList.add(1L to true)
                            }
                        } else {
                            dataList.add(1L to false)
                        }
                    }
                }
            }).filter { it.second }.map { it.first+1 }.maxBy { it }) ?: if(dates.isEmpty()) 0L else 1L

    }
}