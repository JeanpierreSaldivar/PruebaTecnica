package com.saldivar.pruebatecnica.modulo.HomeActivity.presenter

import android.graphics.drawable.Drawable
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
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.MapperListaTareas

class PresenterHome(private val view: HomeMVP.View) : HomeMVP.Presenter {
    private val model = ModelHome(this)

    override fun cambiarEstadoOjo(): Drawable {
        UtilHome.ojo = !UtilHome.ojo
        return if (UtilHome.ojo){
           MyAplicationClass.ctx!!.resources.getDrawable(R.drawable.ic_baseline_visibility_off_24)
        } else{
            MyAplicationClass.ctx!!.resources.getDrawable(R.drawable.ic_baseline_remove_red_eye_24)
        }
    }

    override fun consultarListTareas(recyclerView: RecyclerView) {
        val pref = MyAplicationClass.ctx!!.preferences()
        val listaObtenida = model.consultarListaTareas()
        when{
            listaObtenida.isEmpty() && !pref.getBoolean("inicio",false) ->{
                val mutableList = insertDatosDefecto()
                model.insertarListaTareasDefectoBD(mutableList)
                ordenarMostrarTareas(listaObtenida,recyclerView)
                val edit = pref.edit()
                edit.putBoolean("inicio",true)
                edit.apply()
            }
            else->{
                ordenarMostrarTareas(listaObtenida,recyclerView)
            }
        }

    }

    private fun ordenarMostrarTareas(listaObtenida: MutableList<Tareas>, recyclerView: RecyclerView) {
        val listaOrdenada =MapperListaTareas().ordenarTareasDeMayorMenor(listaObtenida)
        setRecyclerView(listaOrdenada,recyclerView)
    }

    override fun insertarNuevaTarea(nuevaTarea: Tareas) {
        model.insertarNuevaTarea(nuevaTarea)
    }

    override fun validacion(titulo:String,contenido:String,fecha:String,mAlertDialog:androidx.appcompat.app.AlertDialog,
    recyclerView: RecyclerView) {
        if(titulo.isEmpty() || titulo.isBlank()){
            view.showToask("Ingrese el titulo de la tarea")
        }else{
            if (contenido.isEmpty() || contenido.isBlank()){
                view.showToask("Ingrese el contenido de la tarea")
            }
            else{
                if(fecha.isEmpty() || fecha.isBlank()){
                    view.showToask("Ingrese la fecha de la tarea")
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
                                insertar(titulo,contenido,fechaCreacion,textFinalizaSinYear,recyclerView,mAlertDialog)
                            }
                            mesActual.toInt() < mesElegido.toInt()->{
                                insertar(titulo,contenido,fechaCreacion,textFinalizaSinYear,recyclerView,mAlertDialog)
                            }
                            else->{
                                view.showToask("El dia elegido no es valido")
                            }
                        }
                    }else{
                        view.showToask("El mes elegido no es valido")
                    }
                }
            }
        }
    }

    private  fun insertar(titulo:String,contenido:String,fechaCreacion:String,textFinalizaSinYear:String,recyclerView:RecyclerView,mAlertDialog:androidx.appcompat.app.AlertDialog){
        val objectVal0 = Tareas(
                0, titulo, contenido,fechaCreacion,
                textFinalizaSinYear,false)
        model.insertarNuevaTarea(objectVal0)
        val listaTareas = model.consultarListaTareas()
        ordenarMostrarTareas(listaTareas,recyclerView)
        mAlertDialog.dismiss()
    }

    private fun insertDatosDefecto():MutableList<Tareas> {
        val listObject = mutableListOf<Tareas>()
        val objectVal0 = Tareas(1, "Ejercicio para casa", "Resolver problemas diversos sobre algebra",
            "17/02", "22/02", false)
        listObject.add(0, objectVal0)
        val objectVal1 = Tareas(2, "Tarea de Química", "Resolver problemas sobre teoremas sobre la" +
                "tabla periodica", "17/02", "22/02", false)
        listObject.add(1, objectVal1)
        val objectVal2 = Tareas(3, "Tarea de Matemáticas", "Resolver problemas sobre teoremas de" +
                "pitagoras", "17/02", "22/02", false)
        listObject.add(2, objectVal2)
        val objectVal3 = Tareas(4, "Tarea de Biologia", "Resolver problemas sobre Biologia", "17/02", "22/02", true)
        listObject.add(3, objectVal3)
         return listObject
    }

    private fun setRecyclerView(datosTarea: List<Tareas>,recycler: RecyclerView) {
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = LinearLayoutManager(MyAplicationClass.ctx)
        recycler.adapter = (TareasAdapter(
            datosTarea,
            object :
                RecyclerTareasListener {
                override fun onClick(tarea: Tareas, position: Int) {
                    setearValores(tarea)
                    view.nextActivity()
                }
                override fun change(tarea: Tareas, position: Int) {
                    val tarea=model.consultarEstadoTarea(tarea.id)
                    updateEstadoTarea(tarea)
                }
            }))
    }

    private fun setearValores(tarea: Tareas) {
        UtilHome.id = tarea.id
        UtilHome.titulo = tarea.titulo
        UtilHome.creacion = tarea.creacion
        UtilHome.detalle = tarea.descripcion
        UtilHome.finalizacion = tarea.finalizacion
    }

    private fun updateEstadoTarea(tarea: List<Tareas>) {
        val valorActulizar= !tarea[0].estado
        model.updateEstado(tarea[0], valorActulizar)
    }


}