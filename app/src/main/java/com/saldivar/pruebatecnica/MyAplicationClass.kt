package com.saldivar.pruebatecnica

import android.app.Application
import android.content.Context
import androidx.recyclerview.widget.RecyclerView

class MyAplicationClass : Application() {

    companion object {
        var ctx: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext

    }
}