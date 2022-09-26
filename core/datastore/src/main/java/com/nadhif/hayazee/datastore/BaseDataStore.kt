package com.nadhif.hayazee.datastore

import android.content.SharedPreferences
import com.google.gson.Gson
import javax.inject.Inject

open class BaseDataStore @Inject constructor(
    protected val sharedPreferences: SharedPreferences
) {

    fun <T> saveDataObject(key: String, data: T) {
        val gson = Gson()
        sharedPreferences.edit()
            .putString(key, gson.toJson(data))
            .apply()
    }

    fun <T> getSavedDataObject(key: String, classOfT: Class<T>): T? {
        val gson = Gson()
        val savedData = sharedPreferences.getString(key, null) ?: return null
        return gson.fromJson(savedData, classOfT)
    }

}