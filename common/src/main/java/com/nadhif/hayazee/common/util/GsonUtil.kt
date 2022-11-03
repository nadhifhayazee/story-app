package com.nadhif.hayazee.common.util

import com.google.gson.Gson

object GsonUtil {
    fun <T> fromJson(json: String, classOfT: Class<T>): T? {
        val gson = Gson()
        return gson.fromJson(json, classOfT)
    }
}