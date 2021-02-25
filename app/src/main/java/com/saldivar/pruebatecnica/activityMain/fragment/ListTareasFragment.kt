package com.saldivar.pruebatecnica.activityMain.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.pruebatecnica.activityDetalleTarea.DatosTareaElegidaDetalle
import com.saldivar.pruebatecnica.activityDetalleTarea.DetalleTareaActivity
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.activityMain.TareaObject
import com.saldivar.pruebatecnica.activityMain.TareasAdapter
import com.saldivar.pruebatecnica.activityMain.estadoVisualizadorTareas
import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.showDialog
import kotlinx.android.synthetic.main.fragment_list_tareas.view.*


class ListTareasFragment : Fragment(), ListTareasFragmentViewInterface,View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootview = inflater.inflate(R.layout.fragment_list_tareas, container, false)
        rootview.flotanButton.setOnClickListener(this)
        presenter = ListTareasFragmentPresenter(this)
        recycler = rootview.recycler_tareas as RecyclerView
        consultar(context!!)
        return rootview
    }
    companion object {
        private lateinit var presenter: ListTareasFragmentPresenterInterface
        private lateinit var recycler: RecyclerView
        private lateinit var adapter: TareasAdapter
        fun newInstance(): ListTareasFragment = ListTareasFragment()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.flotanButton->{
                showDialog(dialog(),this.activity!!)
            }
        }
    }

    internal fun consultar(context: Context,origenllamada:String ="ListTareasFragment") {
        if(origenllamada=="ListTareasFragment"){
            presenter.consultarListTareas(context,estadoVisualizadorTareas.ojo)
        }else{
            presenter = ListTareasFragmentPresenter(this)
            presenter.consultarListTareas(context,estadoVisualizadorTareas.ojo)
        }
    }

    override fun resultadoConsultaListTareas(listaTareas: List<Tareas>) {
        if(listaTareas.isNotEmpty()){
            val tareaObject = MapperListTareas().listTareas(listaTareas)
            setRecyclerView(tareaObject)
        }
        else{
            insertDatosDefecto()
            consultar(context!!)
        }
    }


    private fun insertDatosDefecto() {
        val listObject = mutableListOf<Tareas>()
        val objectVal0 = Tareas(0,"Ejercicio para casa","Resolver problemas diversos sobre algebra",
            "17/02","22/02",false)
        listObject.add(0,objectVal0)
        val objectVal1 = Tareas(0,"Tarea de Química","Resolver problemas sobre teoremas sobre la" +
                "tabla periodica","17/02","22/02",false)
        listObject.add(1,objectVal1)
        val objectVal2 = Tareas(0,"Tarea de Matemáticas","Resolver problemas sobre teoremas de" +
                "pitagoras","17/02","22/02",false)
        listObject.add(2,objectVal2)
        val objectVal3 = Tareas(0,"Tarea de Biologia","Resolver problemas sobre Biologia"
            ,"17/02","22/02",true)
        listObject.add(3,objectVal3)
        insertarBD(listObject,context!!)
    }

    internal fun insertarBD(listObject: MutableList<Tareas>,context:Context, origenllamada: String="ListTareasFragment") {
        if(origenllamada == "ListTareasFragment" ){
            presenter.insertarDatosDefecto(this.activity!!,listObject)
        }
        else{
            presenter = ListTareasFragmentPresenter(this)
            presenter.insertarDatosDefecto(context,listObject)
        }
    }

    private fun setRecyclerView(datosTarea: MutableList<TareaObject>) {
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = LinearLayoutManager(context)
        adapter=(TareasAdapter(
            datosTarea,
            object :
                RecyclerTareasListener {
                override fun onClick(flight: TareaObject, position: Int) {
                    setearValores(flight)
                    startActivity(Intent(context, DetalleTareaActivity::class.java))
                    activity!!.overridePendingTransition(
                        R.anim.left_in, R.anim.left_out
                    )
                    activity!!.finish()
                }

                override fun onDelete(flight: TareaObject, position: Int) {
                }

                override fun change(flight: TareaObject, position: Int, context: Context) {
                    consultarEstado(flight,context)
                }

            }))
        recycler.adapter = adapter
    }

    private fun setearValores(flight: TareaObject) {
        DatosTareaElegidaDetalle.id = flight.id
        DatosTareaElegidaDetalle.titulo = flight.titulo!!
        DatosTareaElegidaDetalle.creacion = flight.creacionFecha!!
        DatosTareaElegidaDetalle.detalle = flight.descripcion!!
        DatosTareaElegidaDetalle.finalizacion = flight.finalizacionFecha!!
    }

    private fun consultarEstado(flight: TareaObject,context: Context) {
        presenter.consultarEstado(flight.id,context)
    }
    override fun respuestaConsultaEstado(listaTareas: List<Tareas>, id:Int,context: Context) {
        val list = MapperListTareas().listTareas(listaTareas)
        if (list[0].estado){
            updateEstado(list[0],context,false)
        }
        else{
            updateEstado(list[0],context,true)
        }
    }

    private fun updateEstado(flight: TareaObject,context: Context,estado: Boolean,) {
        presenter.actualizarEstadoTarea(flight.id,context,estado)
    }

    private fun dialog() = LayoutInflater.from(this.activity!!).
    inflate(R.layout.alert_dialog_nueva_tarea, this.activity?.findViewById(R.id.alertNuevaTarea))



}