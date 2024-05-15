package com.adishakti.repohunt.util


fun String?.toStringOrEmpty(): String {
    return this ?: ""
}