package com.saldivar.pruebatecnica.modulo.HomeActivity.View

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.saldivar.pruebatecnica.helper.MyAplicationClass
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.helper.*
import com.saldivar.pruebatecnica.modulo.HomeActivity.mvp.HomeMVP
import com.saldivar.pruebatecnica.modulo.HomeActivity.presenter.PresenterHome
import com.saldivar.pruebatecnica.modulo.detalleTarea.view.DetalleTareaActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list_tareas.view.*
import java.util.*


class ListTareasFragment : Fragment(),View.OnClickListener,HomeMVP.View {
    private lateinit var presenter: PresenterHome
    private lateinit var recycler: RecyclerView
    private lateinit var visualizadorOjo: ImageView
    private  lateinit var floatingBtn:FloatingActionButton
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootview = inflater.inflate(R.layout.fragment_list_tareas, container, false)
        visualizadorOjo = activity!!.findViewById(R.id.visualizador)
        floatingBtn= rootview.findViewById(R.id.flotanButton)
        rootview.flotanButton.setOnClickListener(this)
        activity!!.visualizador.setOnClickListener(this)
        presenter = PresenterHome(this)
        recycler = rootview.recycler_tareas as RecyclerView
        consultar()
        return rootview
    }
    companion object {
        fun newInstance(): ListTareasFragment = ListTareasFragment()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.visualizador->{
                visualizadorOjo.background = presenter.cambiarEstadoOjo()
                consultar()
            }
            R.id.flotanButton -> {
                showDialogFragment(dialog(),recycler)
            }
        }
    }

    internal fun consultar() {
        presenter.consultarListTareas(recycler)
    }

    internal fun insertarDatosNuevaTarea(nuevaTarea:Tareas){
        presenter.insertarNuevaTarea(nuevaTarea)
    }

    private fun dialog() = LayoutInflater.from(this.activity!!).
    inflate(R.layout.alert_dialog_nueva_tarea,this.activity!!.findViewById(R.id.alertNuevaTarea))

    override fun nextActivity() {
        val intent = Intent(context, DetalleTareaActivity::class.java)
        context!!.startActivity(intent)
        (context as Activity).overridePendingTransition(
            R.anim.left_in, R.anim.left_out)
    }

    override fun showToask(message: String) {
        Toast.makeText(MyAplicationClass.ctx, message, Toast.LENGTH_SHORT).show()}



    fun showDialogFragment( mDialogView: View,recyclerView: RecyclerView){
        val mBuilder = AlertDialog.Builder(this.activity!!).setView(mDialogView)
        val textTitulo = mDialogView.findViewById(R.id.tituloAlert) as EditText
        val textContenido= mDialogView.findViewById(R.id.contenidoAlert) as EditText
        val textFinaliza = mDialogView.findViewById(R.id.finalizaAlert) as EditText
        val aceptar = mDialogView.findViewById(R.id.botonAceptar) as Button
        val cancelar = mDialogView.findViewById(R.id.butonCancelar) as Button
        val  mAlertDialog = mBuilder.show()
        mAlertDialog.setCanceledOnTouchOutside(false)
        mAlertDialog.window?.setBackgroundDrawable(null)
        textFinaliza.setOnClickListener {
            OcultarTeclado.hideSoftKeyBoard(this.activity!!, textFinaliza)
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
            val titulo = textTitulo.text.toString()
            val contenido = textContenido.text.toString()
            val fecha = textFinaliza.text.toString()
           presenter.validacion(titulo,contenido,fecha,mAlertDialog,recyclerView)

        }
        cancelar.setOnClickListener { mAlertDialog.dismiss() }
    }

}