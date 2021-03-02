package com.saldivar.pruebatecnica.modulo.HomeActivity.presenter

import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.helper.*
import com.saldivar.pruebatecnica.modulo.HomeActivity.model.ModelHome
import com.saldivar.pruebatecnica.modulo.HomeActivity.mvp.HomeMVP

class PresenterHome(private val view: HomeMVP.View) : HomeMVP.Presenter {
    private val model = ModelHome(this)

    override fun consultarListTareas(estadoOjo:Boolean):MutableList<Tareas> {
        val listaObtenida = model.consultarListaTareas(estadoOjo)
        if (listaObtenida.isEmpty()){
            view.mostrarEnRecycler(listaObtenida)
            toastMessage("No hay tareas disponibles")
        }
        else{
            ordenarMostrarTareas(listaObtenida)
        }
        return listaObtenida
    }

    private fun ordenarMostrarTareas(listaObtenida: MutableList<Tareas>) {
        val lista = mutableListOf<Tareas>()
        val listaOrdenada =listaObtenida.sortedByDescending{ it.id }
        for (i in listaOrdenada){
            lista.add(i)
        }
        view.mostrarEnRecycler(lista)

    }

    override fun insertarNuevaTarea(nuevaTarea: Tareas) {
        model.insertarNuevaTarea(nuevaTarea)
    }

    override fun validacion(titulo:String,contenido:String,fecha:String):String {
        var retornar=""
        if(titulo.isEmpty() || titulo.isBlank()){
            retornar= "Ingrese el titulo de la tarea"
        }else{
            if (contenido.isEmpty() || contenido.isBlank()){
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
                        when{
                            mesActual.toInt() == mesElegido.toInt() && diaElegido.toInt()>=diaActual.toInt()->{
                                insertar(titulo,contenido,fechaCreacion,textFinalizaSinYear)
                            }
                            mesActual.toInt() < mesElegido.toInt()->{
                                insertar(titulo,contenido,fechaCreacion,textFinalizaSinYear)
                            }
                            else->{
                                retornar =  "El dia elegido no es valido"
                            }
                        }
                    }else{
                        retornar = "El mes elegido no es valido"
                    }
                }
            }
        }
        return  retornar
    }

    override fun consultaEstadoTarea(tareaID: Int):Boolean {
        val tarea = model.consultarEstadoTarea(tareaID)
        return tarea.estado

    }

    override fun updateEstadoTarea(tarea: Tareas, valorActulizar: Boolean) {
        model.updateEstado(tarea, valorActulizar)
    }

    override fun eliminarTarea(idTarea: Int) {
        model.eliminarTarea(idTarea)
    }

    private  fun insertar(titulo:String,contenido:String,fechaCreacion:String,textFinalizaSinYear:String){
        val objectVal0 = Tareas(
                0, titulo, contenido,fechaCreacion,
                textFinalizaSinYear,false)
        model.insertarNuevaTarea(objectVal0)
        val ultimaTarea =model.consultarUltimaTareaInsertada()
        view.mostrarEnRecyclerAdd(ultimaTarea)
    }




}