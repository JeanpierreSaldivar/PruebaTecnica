package com.saldivar.pruebatecnica.activityDetalleTarea

import android.content.Context
import com.saldivar.pruebatecnica.activityDetalleTarea.fragment.ListComentariosFragmentModel
import com.saldivar.pruebatecnica.activityDetalleTarea.fragment.ListComentariosFragmentModelInterface
import com.saldivar.pruebatecnica.activityDetalleTarea.fragment.ListComentariosFragmentPresenterInterface
import com.saldivar.pruebatecnica.activityDetalleTarea.fragment.ListComentariosFragmentViewInterface
import com.saldivar.pruebatecnica.db.Comentarios
import com.saldivar.pruebatecnica.db.Tareas

class DetalleTareaActivityPresenter(private val view: DetalleTareaActivityViewInterface) :
    DetalleTareaActivityPresenterInterface {
    private val model: DetalleTareaActivityModelInterface
    init {
        model = DetalleTareaActivityModel(this)
    }

    override fun insertarComentarioBD(context: Context, list:MutableList<Comentarios>) {
        model.insertarComentarioBD(context,list)
    }

    override fun actualizarTarea(titulo: String, descripcion: String, finalizacion: String, id: Int,context:Context) {
        model.actualizarTarea(titulo,descripcion,finalizacion,id,context)
    }

    override fun consultarDatosNuevos(context: Context, id: Int) {
        model.consultarDatosNuevos(context,id)
    }

    override fun datosNuevosTareaObtenido(tareaActualizada: List<Tareas>) {
        view.datosNuevosTareaObtenido(tareaActualizada)
    }
}