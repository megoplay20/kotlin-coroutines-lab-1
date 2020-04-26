package com.example.lab1_kotlincoroutineslab.models

import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request

suspend fun getHolidaysForManyCountries(countryCodesList: List<String>, year: Int): List<String?> {
    return withContext(Dispatchers.IO) {

        val client = OkHttpClient()
        supervisorScope {
            countryCodesList.map { Pair(ServiceUtils.formServiceUrl(year, it), it) }
                .map { Pair(async { getRequest(client, it.first) }, it.second) }.map {
                    try {
                        it.first.await()
                    }
                    catch(ce: java.lang.Exception){
                        println("Failed to fetch data for ${it.second}")
                        null
                    }
                }.filterNotNull()
        }


    }


}

suspend fun getRequestSingle(url: String): String? {
    val client = OkHttpClient()
    return withContext(Dispatchers.IO) {
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful)
            response.body?.string()
        else
            throw Exception("Not found")
    }
}

suspend fun getRequest(client: OkHttpClient, url: String): String? {
    return withContext(Dispatchers.IO) {
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful)
            response.body?.string()
        else
            throw Exception("Not found")
    }
}