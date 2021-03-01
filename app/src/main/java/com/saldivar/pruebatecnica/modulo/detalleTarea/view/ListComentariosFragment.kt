package com.saldivar.pruebatecnica.modulo.detalleTarea.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.saldivar.pruebatecnica.helper.MyAplicationClass
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.helper.*
import com.saldivar.pruebatecnica.modulo.HomeActivity.View.HomeActivity
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.UtilHome
import com.saldivar.pruebatecnica.modulo.detalleTarea.mvp.DetalleTareaMVP
import com.saldivar.pruebatecnica.modulo.detalleTarea.presenter.PresenterDetalleTarea
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
        presenter.getAllComentarios(recycler)
        iu()
        return rootview
    }

    private fun iu() {
        setearDatos()
        activity!!.delete.setOnClickListener(this)
        activity!!.check.setOnClickListener(this)
        activity!!.edit.setOnClickListener(this)

    }

    private fun setearDatos() {
        textToolbar.text = UtilHome.titulo
        textCreacionDetalle.text = "Creada: ${UtilHome.creacion}"
        textFinalizacionDetalle.text = "Finaliza: ${UtilHome.finalizacion}"
        textDescripcionDetalle.text = UtilHome.detalle
    }

    companion object {
        fun newInstance(): ListComentariosFragment = ListComentariosFragment()
    }

    override fun cantidadComentarios(cantidadComentarios: String) {
        textNumeroComentarios.text= cantidadComentarios
    }

    override fun setearDatosVista() {
        setearDatos()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.check->{
                val dato = etNewComentario.text.toString()
                presenter.enviarNuevoComentario(dato,recycler)
                etNewComentario.setText("")
                etNewComentario.clearFocus()
                OcultarTeclado.hideSoftKeyBoard(MyAplicationClass.ctx!!, etNewComentario)
            }
            R.id.edit->{
                showDialog(dialog(),recycler)
            }
            R.id.delete->{
                showDialogDelete(dialogDelete())
            }
        }
    }

    private fun dialog() = LayoutInflater.from(this.activity!!).
    inflate(R.layout.alert_dialog_nueva_tarea,this.activity!!.findViewById(R.id.alertNuevaTarea))

    private fun dialogDelete() = LayoutInflater.from(this.activity!!).
    inflate(R.layout.alert_dialog_delete, this.activity!!.findViewById(R.id.alertDeleteTarea))

    private fun showDialog( mDialogView: View,recyclerView:RecyclerView){
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
            textTitulo.setText(UtilHome.titulo)
            containerContenidoAlert.hint = "Contenido"
            textContenido.setText(UtilHome.detalle)
            containerFinalizaAlert.hint = "Finaliza"
            textFinaliza.setText("${UtilHome.finalizacion}/2021")
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

    private fun showDialogDelete( mDialogView: View){
        val mBuilder = AlertDialog.Builder(this.activity!!).setView(mDialogView)
        val titulo = mDialogView.findViewById(R.id.TituloDialogDelete) as TextView
        val recordatorio = mDialogView.findViewById(R.id.recordatorioDialogDelete) as TextView
        val aceptar = mDialogView.findViewById(R.id.botonConfirmar) as Button
        val cancelar = mDialogView.findViewById(R.id.butonCancelar) as Button
        val  mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawable(null)
        titulo.text = "¿Seguro que quieres eliminar ${UtilHome.titulo}?"
        recordatorio.text = "Recuerda: Se van a borrar todos los comentarios"
        aceptar.setOnClickListener {
            presenter.eliminarComentarios()
            presenter.eliminarTarea()
            backActivity()
            mAlertDialog.dismiss()
        }
        cancelar.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    private fun backActivity() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        activity!!.overridePendingTransition(
                R.anim.right_in, R.anim.right_out
        )
        activity!!.finish()
    }

}