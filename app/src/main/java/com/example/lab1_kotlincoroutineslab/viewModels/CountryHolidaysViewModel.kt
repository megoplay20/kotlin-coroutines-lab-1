package com.example.lab1_kotlincoroutineslab.viewModels

import androidx.lifecycle.*
import com.example.lab1_kotlincoroutineslab.models.*
import com.example.lab1_kotlincoroutineslab.models.extensions.convertToDisplay
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.lang.Exception

class CountryHolidaysViewModel : ViewModel() {

    val holidays: MutableLiveData<List<DisplayHoliday>> = MutableLiveData()
    val errorsMessageFeed: MutableLiveData<String> = MutableLiveData()



    fun fetchHolidaysForCountry(countryCode: String){
        viewModelScope.launch {
            supervisorScope {
                try {
                    val holidaysRawString = async {
                        getRequestSingle(ServiceUtils.formServiceUrl(2020, countryCode))
                    }.await()
                    val holidayList: List<Holiday> = Gson().fromJson(
                        holidaysRawString,
                        object : TypeToken<List<Holiday>>() {}.type
                    )
                    holidays.value = holidayList.map {
                        it.convertToDisplay({ DateProcessor formatDate it },
                            { DateProcessor parseDate it })
                    }
                } catch (e: Exception) {
                    errorsMessageFeed.value =
                        "Не удалось получить праздники для страны $countryCode"
                }
            }
        }
    }



}