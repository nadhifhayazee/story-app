package com.nadhif.hayazee.common.util

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    const val EEEE_dd_MMM_yyyy_HH_mm = "EEEE, dd MMM yyyy, HH:mm"
    const val TIME_FORMAT_WITH_TZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"


    fun convertDateBasedOnLocale(
        myDate: String,
        oldFormat: String,
        newFormat: String,
        context: Context
    ): String {
        val locale = context.resources.configuration.locale
        val sdf = SimpleDateFormat(oldFormat, locale)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val newSdf = SimpleDateFormat(newFormat, locale)
        val date = sdf.parse(myDate) ?: return ""
        return newSdf.format(date)

    }

    fun checkIsNight(): Boolean {
        val cal = Calendar.getInstance()
        val hour = cal[Calendar.HOUR_OF_DAY]
        return hour < 6 || hour > 18
    }

}