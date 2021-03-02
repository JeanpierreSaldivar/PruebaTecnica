package com.saldivar.pruebatecnica.modulo.HomeActivity.View

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.saldivar.pruebatecnica.helper.MyAplicationClass
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.helper.*
import com.saldivar.pruebatecnica.modulo.HomeActivity.mvp.HomeMVP
import com.saldivar.pruebatecnica.modulo.HomeActivity.presenter.PresenterHome
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.RecyclerTareasListener
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.TareasAdapter
import com.saldivar.pruebatecnica.modulo.detalleTarea.view.DetalleTareaActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list_tareas.view.*
import java.util.*


class ListTareasFragment : Fragment(),View.OnClickListener,HomeMVP.View {
    private lateinit var presenter: PresenterHome
    private lateinit var recycler: RecyclerView
    private lateinit var visualizadorOjo: ImageView
    private  lateinit var floatingBtn:FloatingActionButton
    private lateinit var adapter:TareasAdapter
    private var bundle: Bundle ?=null
    private var estadoOjo = false
    private var listTareas = mutableListOf<Tareas>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootview = inflater.inflate(R.layout.fragment_list_tareas, container, false)
        visualizadorOjo = activity!!.findViewById(R.id.visualizador)
        floatingBtn= rootview.findViewById(R.id.flotanButton)
        rootview.flotanButton.setOnClickListener(this)
        activity!!.visualizador.setOnClickListener(this)
        presenter = PresenterHome(this)
        recycler = rootview.findViewById(R.id.recycler_tareas)
        bundle = activity!!.intent.extras
        inicio()
        return rootview
    }

    private fun inicio() {
        consultar()
        eliminarTarea()
    }

    private fun eliminarTarea() {
        if(bundle?.getString("eliminar","NO") =="SI"){
            presenter.eliminarTarea(bundle?.getInt("idTarea")!!)
            val positionTarea = bundle?.getInt("position")
            mostrarEnRecyclerDelete(positionTarea!!)
            if (listTareas.size==1){
                toastMessage("No hay tareas disponibles")
            }
        }
    }

    private fun mostrarEnRecyclerDelete(position: Int) {
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = LinearLayoutManager(MyAplicationClass.ctx)
        adapter.removeItem(position)
    }

    companion object {
        fun newInstance(): ListTareasFragment = ListTareasFragment()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.visualizador->{
                if (estadoOjo){
                    estadoOjo= false
                    visualizadorOjo.background = resources.getDrawable(R.drawable.ic_baseline_remove_red_eye_24)
                } else{
                    estadoOjo= true
                    visualizadorOjo.background = resources.getDrawable(R.drawable.ic_baseline_visibility_off_24)
                }
                consultar()
            }
            R.id.flotanButton -> {
                showDialogFragment(estadoOjo)
            }
        }
    }

    private fun consultar() {
        listTareas=presenter.consultarListTareas(estadoOjo)

    }

    override fun mostrarEnRecycler(list: MutableList<Tareas>) {
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = LinearLayoutManager(MyAplicationClass.ctx)
        adapter =TareasAdapter(object:
            RecyclerTareasListener{
            override fun onClick(tarea: Tareas, position: Int) {
                nextActivity(tarea,position)
            }
            override fun change(tarea: Tareas, position: Int) {
                presenter.consultaEstadoTarea(tarea.id)
            }
        })
        recycler.adapter = adapter
        adapter.setDataList(list)
        adapter.notifyDataSetChanged()
    }

    override fun mostrarEnRecyclerAdd(ultimaTarea: Tareas) {
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = LinearLayoutManager(MyAplicationClass.ctx)
        adapter.addItem(0,ultimaTarea)
    }

    private fun nextActivity(tarea:Tareas,position:Int) {
        val bundle = Bundle()
        bundle.putInt("tareaID",tarea.id)
        bundle.putString("tareaTitulo",tarea.titulo)
        bundle.putString("tareaDetalle",tarea.descripcion)
        bundle.putString("tareaCreacion",tarea.creacion)
        bundle.putString("tareaFinalizacion",tarea.finalizacion)
        bundle.putInt("position",position)
        val intent = Intent(this.activity, DetalleTareaActivity::class.java)
        intent.putExtras(bundle)
        this.activity?.startActivity(intent)
        this.activity?.overridePendingTransition(
            R.anim.left_in, R.anim.left_out)
        this.activity?.finish()
    }


    private fun showDialogFragment(estadoOjo: Boolean){
        val dialog =LayoutInflater.from(this.activity!!).
        inflate(R.layout.alert_dialog_nueva_tarea,this.activity!!.findViewById(R.id.alertNuevaTarea))
        val mBuilder = AlertDialog.Builder(this.activity!!).setView(dialog)
        val textTitulo = dialog.findViewById(R.id.tituloAlert) as EditText
        val textContenido= dialog.findViewById(R.id.contenidoAlert) as EditText
        val textFinaliza = dialog.findViewById(R.id.finalizaAlert) as EditText
        val aceptar = dialog.findViewById(R.id.botonAceptar) as Button
        val cancelar = dialog.findViewById(R.id.butonCancelar) as Button
        val  mAlertDialog = mBuilder.show()
        mAlertDialog.setCanceledOnTouchOutside(false)
        mAlertDialog.window?.setBackgroundDrawable(null)
        textFinaliza.setOnClickListener {
            hideSoftKeyBoard(this.activity!!, textFinaliza)
            val c = Calendar.getInstance()
            val day = c.get(Calendar.DAY_OF_MONTH)
            val month = c.get(Calendar.MONTH)
            val year = c.get(Calendar.YEAR)
            val dpd = DatePickerDialog(this.activity!!, DatePickerDialog.OnDateSetListener{
                _, mYear, mMonth, dayOfMonth ->
                val mes = mMonth+1
                var mesString =""
                mesString = if (mes<10){
                    "0$mes"
                }else{
                    mes.toString()
                }
                textFinaliza.setText("$dayOfMonth/$mesString/$mYear")
            },year,month,day)
            dpd.show()
        }
        aceptar.setOnClickListener {
            val titulo= textTitulo.text.toString()
            val contenido = textContenido.text.toString()
            val finalizacion = textFinaliza.text.toString()
           val respuesta = presenter.validacion(titulo,contenido,finalizacion)
            when(respuesta){
                "Ingrese el titulo de la tarea"->{
                    toastMessage(respuesta)
                    textTitulo.text.clear()

                }
                "Ingrese el contenido de la tarea"->{
                    toastMessage(respuesta)
                    textContenido.text.clear()
                }
                "Ingrese la fecha de la tarea"->{
                    toastMessage(respuesta)
                    textFinaliza.text.clear()
                }
                "El dia elegido no es valido"->{
                    toastMessage(respuesta)
                }
                "El mes elegido no es valido"->{
                    toastMessage(respuesta)
                }
                else ->{
                    mAlertDialog.dismiss()
                }
            }
        }
        cancelar.setOnClickListener { mAlertDialog.dismiss() }
    }

}