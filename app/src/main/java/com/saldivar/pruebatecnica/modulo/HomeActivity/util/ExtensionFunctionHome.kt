package com.saldivar.pruebatecnica.modulo.HomeActivity.util

import android.content.Context
import android.content.SharedPreferences

fun preferences(context: Context): SharedPreferences {
    val privateMode = 0
    val preferenceName = "HomePreferences"
    return context.getSharedPreferences(preferenceName,privateMode)
}