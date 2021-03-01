package com.saldivar.pruebatecnica.modulo.HomeActivity.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object UtilHome {
    var ojo:Boolean = false

    var id :Int = 0
    var titulo :String =""
    var creacion:String =""
    var finalizacion:String = ""
    var detalle:String =""

    var comentarioEnProceso:String =""

    fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            // TODO: handle exception
            e.printStackTrace()
        }

    }
}