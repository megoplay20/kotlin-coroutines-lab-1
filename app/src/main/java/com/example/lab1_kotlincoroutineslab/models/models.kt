package com.example.lab1_kotlincoroutineslab.models

import com.example.lab1_kotlincoroutineslab.models.ServiceUtils.getMaxConsecutiveDays
import java.util.*
import kotlin.reflect.KProperty

data class Holiday(val date: String,
                   val localName: String,
                   val name: String,
                   val countryCode: String,
                   val fixed: Boolean,
                   val global: Boolean,
                   val counties: List<String>?,
                   val launchYear: Int?, var type: String?) {
}

data class DisplayHoliday(val localName: String,val countryCode: String) {
    var date: Date = Date()
    var englishName: String = "";
    var description: String? = "";
    var type: String? by DelegatesExtensions.holidayTypeConverter()
    var launchYear: Int? = null
}


data class LongestHoliday(val countryCode: String?, val localName: String?, val startDate: Date? = null, val endDate: Date? = null, var days: Long? = 0, val dates: List<Date>? = null) {

    init {
        dates?.let {
            this.days = getMaxConsecutiveDays(dates) ?: 1
        }

    }
}

object DelegatesExtensions {
    fun holidayTypeConverter() =
        HolidayTypeConverterDelegate()
}

class HolidayTypeConverterDelegate() {
    private var value: String = ""
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
        this.value = when (value) {
            "Public" -> "Общественный"
            "Bank" -> "Банковский"
            "School" -> "Школьный"
            "Authorities" -> "Государственный"
            "Optional" -> "Не обязательный"
            "Observance" -> "Религиозный"
            else -> "Нет данных"
        }
    }
}