package com.saldivar.pruebatecnica.activityDetalleTarea.fragment

import android.content.Context
import com.saldivar.pruebatecnica.db.Comentarios

interface ListComentariosFragmentModelInterface {
    fun getAllComentarios(context: Context, id:Int)

    fun insertarDatosDefecto(context:Context,listObject:MutableList<Comentarios>)
}