package com.saldivar.pruebatecnica.helper

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout
import com.saldivar.pruebatecnica.MyAplicationClass
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.modulo.detalleTarea.view.DetalleTareaActivity
import com.saldivar.pruebatecnica.modulo.HomeActivity.View.ListTareasFragment
import com.saldivar.pruebatecnica.db.Tareas
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
        while (herramientaObservador.comentarioEnProceso.isEmpty() || herramientaObservador.comentarioEnProceso.isBlank()) {
            delay(1500)
            repetitiveTask()
        }
        herramientaObservador.comentarioEnProceso = ""
        successTask()
    }
}

fun Context.preferences()= preferencesS(this,0,getString(R.string.nombre_preferences))

fun preferencesS(context: Context, privateMode:Int, namePreference:String): SharedPreferences {
    return context.getSharedPreferences(namePreference,privateMode)
}

