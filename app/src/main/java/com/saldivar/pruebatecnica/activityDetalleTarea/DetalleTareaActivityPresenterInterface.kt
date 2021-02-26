package com.saldivar.pruebatecnica.activityDetalleTarea

import android.content.Context
import com.saldivar.pruebatecnica.db.Comentarios

interface DetalleTareaActivityPresenterInterface {
    fun insertarComentarioBD(context: Context, list:MutableList<Comentarios>)
}