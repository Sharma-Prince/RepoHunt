package com.adishakti.repohunt.util

import android.net.Uri
import com.google.gson.Gson


inline fun <reified T> convertJsonToDataClass(json: String): T {
    return Gson().fromJson(json, T::class.java)
}

inline fun <reified T> convertDataClassToJson(data: T): String {
    return Uri.encode(Gson().toJson(data))
}
