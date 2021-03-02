package com.saldivar.pruebatecnica.modulo.detalleTarea.presenter

import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.pruebatecnica.helper.MyAplicationClass
import com.saldivar.pruebatecnica.db.Comentarios
import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.modulo.detalleTarea.util.ComentariosAdapter
import com.saldivar.pruebatecnica.helper.fechaActual
import com.saldivar.pruebatecnica.helper.toastMessage
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.UtilHome
import com.saldivar.pruebatecnica.modulo.detalleTarea.model.ModelDetalleTarea
import com.saldivar.pruebatecnica.modulo.detalleTarea.mvp.DetalleTareaMVP

class PresenterDetalleTarea(private val view: DetalleTareaMVP.View) :DetalleTareaMVP.Presenter{
    private val model = ModelDetalleTarea(this)
    override fun getAllComentarios(recyclerView: RecyclerView) {
        val listaComentarios = model.getAllComentarios()
        if(listaComentarios.isNotEmpty()){
            ordenarMostrarComentarios(listaComentarios,recyclerView)
        }
    }

    override fun enviarNuevoComentario(comentario: String,recyclerView: RecyclerView) {
        if(comentario.isNotEmpty() && comentario.isNotBlank()){
            val objectComentario = Comentarios(0, UtilHome.id,
                    "anonimus",comentario)
            model.insertarComentarioBD(objectComentario)
            consultar(recyclerView)
        }
    }

    override fun validacion(titulo:EditText,contenido:EditText,fecha:EditText,mAlertDialog:AlertDialog,recyclerView:RecyclerView) {
        if(titulo.text.toString().isEmpty() || titulo.text.toString().isBlank()){
            toastMessage("Ingrese el titulo de la tarea")
            titulo.text.clear()
        }else{
            if (contenido.text.toString().isEmpty() ||contenido.text.toString().isBlank()){
                toastMessage("Ingrese el contenido de la tarea")
                contenido.text.clear()
            }
            else{
                if(fecha.text.toString().isEmpty() || fecha.text.toString().isBlank()){
                    toastMessage("Ingrese la fecha de la tarea")
                    fecha.text.clear()
                }
                else{
                    val fechaCreacion = fechaActual()
                    val fechaActual:List<String> = fechaCreacion.split("/")
                    val diaActual = fechaActual[0]
                    val mesActual = fechaActual[1]
                    val fechaElegida:List<String> = fecha.text.toString().split("/")
                    val diaElegido = fechaElegida[0]
                    val mesElegido = fechaElegida[1]
                    val textFinalizaSinYear = "${fechaElegida[0]}/${fechaElegida[1]}"
                    if (mesActual.toInt()<=mesElegido.toInt()){
                        if(diaElegido.toInt()>=diaActual.toInt()){
                            model.updateTarea(titulo.text.toString(),contenido.text.toString(), textFinalizaSinYear, UtilHome.id)
                            val tareaActualizada= model.consultarDatosNuevosTarea(UtilHome.id)
                            setearDatosVista(tareaActualizada)
                            mAlertDialog.dismiss()
                        }else{
                            toastMessage("El dia elegido no es valido")
                        }
                    }else{
                        toastMessage("El mes elegido no es valido")
                    }
                }
            }
        }
    }

    override fun eliminarComentarios() {
        model.eliminarComentarios(UtilHome.id)
    }

    override fun eliminarTarea() {
        model.eliminarTarea(UtilHome.id)
    }

    private fun setearDatosVista(tareaActualizada: Tareas) {
        setearDatosObject(tareaActualizada)
        view.setearDatosVista()
    }

    private fun setearDatosObject(tareaActualizada: Tareas) {
        UtilHome.id = tareaActualizada.id
        UtilHome.titulo =tareaActualizada.titulo
        UtilHome.creacion = tareaActualizada.creacion
        UtilHome.detalle = tareaActualizada.descripcion
        UtilHome.finalizacion = tareaActualizada.finalizacion
    }

    private fun consultar(recyclerView:RecyclerView) {
        val listaComentarios = model.getAllComentarios()
        ordenarMostrarComentarios(listaComentarios,recyclerView)
    }

    private fun ordenarMostrarComentarios(listaObtenida: List<Comentarios>, recyclerView: RecyclerView) {
        val listaOrdenada =listaObtenida.sortedByDescending { it.idComentario }
        setRecyclerView(listaOrdenada,recyclerView)
    }
    private fun setRecyclerView(datosComentario: List<Comentarios>, recycler: RecyclerView) {
        view.cantidadComentarios("Comentarios (${datosComentario.size})")
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = LinearLayoutManager(MyAplicationClass.ctx)
        recycler.adapter =(ComentariosAdapter(datosComentario))
    }
}