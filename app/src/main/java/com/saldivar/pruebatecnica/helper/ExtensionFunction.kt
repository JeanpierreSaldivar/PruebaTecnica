package com.saldivar.pruebatecnica.helper

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.UtilHome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun ViewGroup.inflate(layoutId: Int)= LayoutInflater.from(context).inflate(layoutId, this, false)!!


fun toastMessage(mensaje:String){
    Toast.makeText(MyAplicationClass.ctx,mensaje,Toast.LENGTH_LONG).show()
}
fun fechaActual():String {
    val date: Date = Calendar.getInstance().time
    val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
    val today: String = formatter.format(date)
    val partes :List<String> = today.split("/")
    return "${partes[0]}/${partes[1]}"
}

fun searchAutomaticSaldivar(repetitiveTask:()->Unit,successTask:()->Unit) {
    CoroutineScope(Dispatchers.Default).async {
        while (UtilHome.comentarioEnProceso.isEmpty() || UtilHome.comentarioEnProceso.isBlank()) {
            delay(1500)
            repetitiveTask()
        }
        UtilHome.comentarioEnProceso = ""
        successTask()
    }
}

