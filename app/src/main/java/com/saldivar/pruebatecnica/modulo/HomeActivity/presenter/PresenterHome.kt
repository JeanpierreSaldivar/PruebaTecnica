package com.saldivar.pruebatecnica.modulo.HomeActivity.presenter

import android.graphics.drawable.Drawable
import android.widget.EditText
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.pruebatecnica.helper.MyAplicationClass
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.helper.*
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.RecyclerTareasListener
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.TareasAdapter
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.UtilHome
import com.saldivar.pruebatecnica.modulo.HomeActivity.model.ModelHome
import com.saldivar.pruebatecnica.modulo.HomeActivity.mvp.HomeMVP

class PresenterHome(private val view: HomeMVP.View) : HomeMVP.Presenter {
    private val model = ModelHome(this)

    override fun consultarListTareas(estadoOjo:Boolean) {
        val listaObtenida = model.consultarListaTareas(estadoOjo)
        if (listaObtenida.isEmpty()){
            toastMessage("No hay tareas disponibles")
            view.mostrarEnRecycler(listaObtenida)

        }
        else{
            ordenarMostrarTareas(listaObtenida)
        }
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

    override fun validacion(titulo:EditText,contenido:EditText,fecha:EditText,estadoOjo: Boolean) {
        if(titulo.text.toString().isEmpty() || titulo.text.toString().isBlank()){
            toastMessage("Ingrese el titulo de la tarea")
            titulo.text.clear()
        }else{
            if (contenido.text.toString().isEmpty() || contenido.text.toString().isBlank()){
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
                        when{
                            mesActual.toInt() == mesElegido.toInt() && diaElegido.toInt()>=diaActual.toInt()->{
                                insertar(titulo.text.toString(),contenido.text.toString(),fechaCreacion,textFinalizaSinYear,estadoOjo)
                            }
                            mesActual.toInt() < mesElegido.toInt()->{
                                insertar(titulo.text.toString(),contenido.text.toString(),fechaCreacion,textFinalizaSinYear,estadoOjo)
                            }
                            else->{
                                toastMessage("El dia elegido no es valido")
                            }
                        }
                    }else{
                        toastMessage("El mes elegido no es valido")
                    }
                }
            }
        }
    }

    override fun consultaEstadoTarea(tareaID: Int) {
        val tarea = model.consultarEstadoTarea(tareaID)
        updateEstadoTarea(tarea)
    }

    private  fun insertar(titulo:String,contenido:String,fechaCreacion:String,textFinalizaSinYear:String,estadoOjo: Boolean){
        val objectVal0 = Tareas(
                0, titulo, contenido,fechaCreacion,
                textFinalizaSinYear,false)
        model.insertarNuevaTarea(objectVal0)
        val ultimaTarea =model.consultarUltimaTareaInsertada()
        view.mostrarEnRecyclerAdd(ultimaTarea)
    }

    private fun updateEstadoTarea(tarea: Tareas) {
        val valorActulizar= !tarea.estado
        model.updateEstado(tarea, valorActulizar)
    }


}