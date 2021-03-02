package com.saldivar.pruebatecnica.modulo.detalleTarea.view

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.saldivar.pruebatecnica.helper.MyAplicationClass
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.db.Comentarios
import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.helper.*
import com.saldivar.pruebatecnica.modulo.HomeActivity.View.HomeActivity
import com.saldivar.pruebatecnica.modulo.detalleTarea.mvp.DetalleTareaMVP
import com.saldivar.pruebatecnica.modulo.detalleTarea.presenter.PresenterDetalleTarea
import com.saldivar.pruebatecnica.modulo.detalleTarea.util.ComentariosAdapter
import com.saldivar.pruebatecnica.modulo.detalleTarea.util.datosDevueltos
import kotlinx.android.synthetic.main.activity_detalle_tarea.*
import java.util.*


class ListComentariosFragment : Fragment(),View.OnClickListener ,DetalleTareaMVP.View {
    private lateinit var presenter: PresenterDetalleTarea
    private lateinit var recycler: RecyclerView
    private lateinit var textToolbar :TextView
    private lateinit var textCreacionDetalle : TextView
    private lateinit var textFinalizacionDetalle : TextView
    private lateinit var textDescripcionDetalle :TextView
    private lateinit var etNewComentario :EditText
    private lateinit var textNumeroComentarios:TextView
    private  var textoCreacion =""
    private  var textoFinalizacion =""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview =inflater.inflate(R.layout.fragment_list_comentarios, container, false)
        textToolbar = activity!!.findViewById(R.id.textViewToolbar)
        textCreacionDetalle = activity!!.findViewById(R.id.creacionDetalle)
        textFinalizacionDetalle = activity!!.findViewById(R.id.finalizacionDetalle)
        textDescripcionDetalle = activity!!.findViewById(R.id.descricionDetalle)
        etNewComentario = activity!!.findViewById(R.id.comentario_new)
        textNumeroComentarios = activity!!.findViewById(R.id.numeroComentarios)
        presenter = PresenterDetalleTarea(this)
        recycler = rootview.findViewById(R.id.recycler_comentarios)
        iu()
        val bundle = activity!!.intent.extras
        mostrarDatos(bundle)
        presenter.getAllComentarios(datosDevueltos.idTarea)
        return rootview
    }



    private fun mostrarDatos(bundle: Bundle?) {
        datosDevueltos.idTarea = bundle!!.getInt("tareaID",0)
        textToolbar.text = bundle.getString("tareaTitulo")
        textoCreacion = bundle.getString("tareaCreacion","")
        textoFinalizacion = bundle.getString("tareaFinalizacion","")
        textCreacionDetalle.text= "Creada: $textoCreacion"
        textFinalizacionDetalle.text = "Finaliza: $textoFinalizacion"
        textDescripcionDetalle.text= bundle.getString("tareaDetalle")
        datosDevueltos.positionTarea = bundle.getInt("position")
        datosDevueltos.estadoOjo = bundle.getBoolean("estadoOjo")
    }

    private fun iu() {
        activity!!.delete.setOnClickListener(this)
        activity!!.check.setOnClickListener(this)
        activity!!.edit.setOnClickListener(this)

    }

    companion object {
        fun newInstance(): ListComentariosFragment = ListComentariosFragment()
    }

    override fun setearDatosVista(tareaActualizada: Tareas) {
        datosDevueltos.idTarea                      = tareaActualizada.id
        textToolbar.text             = tareaActualizada.titulo
        textCreacionDetalle.text     =  "Creada: ${tareaActualizada.creacion}"
        textFinalizacionDetalle.text = "Finaliza: ${tareaActualizada.finalizacion}"
        textDescripcionDetalle.text  = tareaActualizada.descripcion
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.check->{
                val dato = etNewComentario.text.toString()
                presenter.enviarNuevoComentario(dato,datosDevueltos.idTarea)
                etNewComentario.setText("")
                etNewComentario.clearFocus()
                hideSoftKeyBoard(MyAplicationClass.ctx!!, etNewComentario)
            }
            R.id.edit->{
                showDialog()
            }
            R.id.delete->{
                showDialogDelete()
            }
        }
    }

    override fun setRecyclerView(datosComentario: MutableList<Comentarios>) {
        textNumeroComentarios.text=("Comentarios (${datosComentario.size})")
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = LinearLayoutManager(MyAplicationClass.ctx)
        recycler.adapter =(ComentariosAdapter(datosComentario))
    }


    private fun showDialog(){
        val mDialogView =LayoutInflater.from(this.activity!!).
        inflate(R.layout.alert_dialog_nueva_tarea,this.activity!!.findViewById(R.id.alertNuevaTarea))
        val mBuilder = AlertDialog.Builder(this.activity!!).setView(mDialogView)
        val tituloAlert = mDialogView.findViewById(R.id.TituloDialog) as TextView
        val containerTituloAlert = mDialogView.findViewById(R.id.containerTituloAlert) as TextInputLayout
        val textTitulo = mDialogView.findViewById(R.id.tituloAlert) as EditText
        val containerContenidoAlert = mDialogView.findViewById(R.id.containerContenidoAlert) as TextInputLayout
        val textContenido= mDialogView.findViewById(R.id.contenidoAlert) as EditText
        val containerFinalizaAlert = mDialogView.findViewById(R.id.containerFinalizaAlert) as TextInputLayout
        val textFinaliza = mDialogView.findViewById(R.id.finalizaAlert) as EditText
        val aceptar = mDialogView.findViewById(R.id.botonAceptar) as Button
        val cancelar = mDialogView.findViewById(R.id.butonCancelar) as Button
            tituloAlert.text = "Editar Actividad"
            containerTituloAlert.hint = "Titulo"
            textTitulo.setText(textToolbar.text.toString())
            containerContenidoAlert.hint = "Contenido"
            textContenido.setText(textDescripcionDetalle.text.toString())
            containerFinalizaAlert.hint = "Finaliza"
            textFinaliza.setText("$textoFinalizacion/2021")
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
            val titulo = textTitulo.text.toString()
            val contenido = textContenido.text.toString()
            val finaliza = textFinaliza.text.toString()
            val respuesta =presenter.validacion(titulo,contenido,finaliza,datosDevueltos.idTarea)
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
            mAlertDialog.dismiss()
        }
        cancelar.setOnClickListener { mAlertDialog.dismiss() }
    }

    private fun showDialogDelete(){
        val mDialogView =LayoutInflater.from(this.activity!!).
        inflate(R.layout.alert_dialog_delete, this.activity!!.findViewById(R.id.alertDeleteTarea))
        val mBuilder = AlertDialog.Builder(this.activity!!).setView(mDialogView)
        val titulo = mDialogView.findViewById(R.id.TituloDialogDelete) as TextView
        val recordatorio = mDialogView.findViewById(R.id.recordatorioDialogDelete) as TextView
        val aceptar = mDialogView.findViewById(R.id.botonConfirmar) as Button
        val cancelar = mDialogView.findViewById(R.id.butonCancelar) as Button
        val  mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawable(null)
        titulo.text = "¿Seguro que quieres eliminar ${textToolbar.text}?"
        recordatorio.text = "Recuerda: Se van a borrar todos los comentarios"
        aceptar.setOnClickListener {
            presenter.eliminarComentarios(datosDevueltos.idTarea)
            backActivity("eliminado")
            mAlertDialog.dismiss()
        }
        cancelar.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    internal fun backActivity(eliminar:String ="",context: Context= activity!!) {

        val bundle2 = Bundle()
        bundle2.putString("eliminar","SI")
        bundle2.putInt("position",datosDevueltos.positionTarea)
        bundle2.putInt("idTarea",datosDevueltos.idTarea)
        bundle2.putBoolean("estadoOjo",datosDevueltos.estadoOjo)
        if(eliminar=="eliminado"){
            bundle2.putString("eliminado",eliminar)
        }
        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtras(bundle2)
        context.startActivity(intent)
        (context as Activity).overridePendingTransition(
                R.anim.right_in, R.anim.right_out
        )
        (context as Activity).finish()
    }


}