package com.example.lab1_kotlincoroutineslab.viewModels

import androidx.lifecycle.*
import com.example.lab1_kotlincoroutineslab.models.*
import com.example.lab1_kotlincoroutineslab.models.extensions.convertToDisplay
import com.example.lab1_kotlincoroutineslab.models.extensions.getDisplayHolidayList
import com.example.lab1_kotlincoroutineslab.models.extensions.getLongestHoliday
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.lang.Exception

class LongestHolidaysViewModel : ViewModel() {

    val longestHolidays: MutableLiveData<List<LongestHoliday>> = MutableLiveData()


    fun collectHolidaysStatistics(countryCodes: String, errorCallback:(msg: String)->Unit) {

        val codes = countryCodes.split(",").filter { it.isNotBlank() }
        viewModelScope.launch {
            val holidaysForManyCountries = getHolidaysForManyCountries(
                codes, 2020
            )
            val longestCountryHolidays = holidaysForManyCountries
                .mapNotNull { Gson().fromJson<List<Holiday>>(it, object : TypeToken<List<Holiday>>() {}.type) }
                .map { it.getDisplayHolidayList() }
                .map { it.getLongestHoliday() }

            if (longestCountryHolidays.isEmpty())
                errorCallback("Не удалось получить данные ни для одной страны!")

            longestHolidays.value = longestCountryHolidays
        }
    }
}



