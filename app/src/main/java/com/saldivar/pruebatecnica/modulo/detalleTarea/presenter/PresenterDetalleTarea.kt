package com.saldivar.pruebatecnica.modulo.detalleTarea.presenter

import com.saldivar.pruebatecnica.db.Comentarios
import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.helper.fechaActual
import com.saldivar.pruebatecnica.modulo.detalleTarea.model.ModelDetalleTarea
import com.saldivar.pruebatecnica.modulo.detalleTarea.mvp.DetalleTareaMVP

class PresenterDetalleTarea(private val view: DetalleTareaMVP.View) :DetalleTareaMVP.Presenter{
    private val model = ModelDetalleTarea(this)
    override fun getAllComentarios(idTarea: Int) {
        val listaComentarios = model.getAllComentarios(idTarea)
        if(listaComentarios.isNotEmpty()){
            ordenarMostrarComentarios(listaComentarios)
        }
    }

    override fun enviarNuevoComentario(comentario: String,idTarea:Int) {
        if(comentario.isNotEmpty() && comentario.isNotBlank()){
            val objectComentario = Comentarios(0, idTarea,
                    "anonimus",comentario)
            model.insertarComentarioBD(objectComentario)
            consultar(idTarea)
        }
    }

    override fun validacion(titulo:String,contenido:String,fecha:String,idTarea: Int):String {
        var retornar=""
        if(titulo.isEmpty() || titulo.isBlank()){
            retornar= "Ingrese el titulo de la tarea"
        }else{
            if (contenido.isEmpty() ||contenido.isBlank()){
                retornar = "Ingrese el contenido de la tarea"
            }
            else{
                if(fecha.isEmpty() || fecha.isBlank()){
                    retornar = "Ingrese la fecha de la tarea"
                }
                else{
                    val fechaCreacion = fechaActual()
                    val fechaActual:List<String> = fechaCreacion.split("/")
                    val diaActual = fechaActual[0]
                    val mesActual = fechaActual[1]
                    val fechaElegida:List<String> = fecha.split("/")
                    val diaElegido = fechaElegida[0]
                    val mesElegido = fechaElegida[1]
                    val textFinalizaSinYear = "${fechaElegida[0]}/${fechaElegida[1]}"
                    if (mesActual.toInt()<=mesElegido.toInt()){
                        if(diaElegido.toInt()>=diaActual.toInt()){
                            model.updateTarea(titulo,contenido, textFinalizaSinYear, idTarea)
                            val tareaActualizada= model.consultarDatosNuevosTarea(idTarea)
                            setearDatosVista(tareaActualizada)
                        }else{
                            retornar =  "El dia elegido no es valido"
                        }
                    }else{
                        retornar = "El mes elegido no es valido"
                    }
                }
            }
        }
        return retornar
    }

    override fun eliminarComentarios(idTarea: Int) {
        model.eliminarComentarios(idTarea)
    }

    private fun setearDatosVista(tareaActualizada: Tareas) {
        view.setearDatosVista(tareaActualizada)
    }


    private fun consultar(idTarea:Int) {
        val listaComentarios = model.getAllComentarios(idTarea)
        ordenarMostrarComentarios(listaComentarios)
    }

    private fun ordenarMostrarComentarios(listaObtenida: List<Comentarios>) {
        val lista = mutableListOf<Comentarios>()
        val listaOrdenada =listaObtenida.sortedByDescending { it.idComentario }
        for (i in listaOrdenada){
            lista.add(i)
        }
        view.setRecyclerView(lista)
    }

}