package com.saldivar.pruebatecnica

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout
import com.saldivar.pruebatecnica.activityDetalleTarea.DatosTareaElegidaDetalle
import com.saldivar.pruebatecnica.activityDetalleTarea.DetalleTareaActivity
import com.saldivar.pruebatecnica.activityMain.fragment.ListTareasFragment
import com.saldivar.pruebatecnica.activityMain.fragment.ListTareasFragmentPresenter
import com.saldivar.pruebatecnica.activityMain.fragment.ListTareasFragmentPresenterInterface
import com.saldivar.pruebatecnica.db.Tareas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun ViewGroup.inflate(layoutId: Int)= LayoutInflater.from(context).inflate(layoutId, this, false)!!

fun showDialog( mDialogView: View, context: Context) {
    val mBuilder = AlertDialog.Builder(context).setView(mDialogView)
    val tituloAlert = mDialogView.findViewById(R.id.TituloDialog) as TextView
    val containerTituloAlert = mDialogView.findViewById(R.id.containerTituloAlert) as TextInputLayout
    val textTitulo = mDialogView.findViewById(R.id.tituloAlert) as EditText
    val containerContenidoAlert = mDialogView.findViewById(R.id.containerContenidoAlert) as TextInputLayout
    val textContenido= mDialogView.findViewById(R.id.contenidoAlert) as EditText
    val containerFinalizaAlert = mDialogView.findViewById(R.id.containerFinalizaAlert) as TextInputLayout
    val textFinaliza = mDialogView.findViewById(R.id.finalizaAlert) as EditText
    val aceptar = mDialogView.findViewById(R.id.botonAceptar) as Button
    val cancelar = mDialogView.findViewById(R.id.butonCancelar) as Button
    if(context is DetalleTareaActivity){
        tituloAlert.text = "Editar Actividad"
        containerTituloAlert.hint = "Titulo"
        textTitulo.setText(DatosTareaElegidaDetalle.titulo)
        containerContenidoAlert.hint = "Contenido"
        textContenido.setText(DatosTareaElegidaDetalle.detalle)
        containerFinalizaAlert.hint = "Finaliza"
        textFinaliza.setText("${DatosTareaElegidaDetalle.finalizacion}/2021")

    }
    val  mAlertDialog = mBuilder.show()
    val fechaCreacion = fechaActual()
    mAlertDialog.window?.setBackgroundDrawable(null)
    textFinaliza.setOnClickListener {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val dpd = DatePickerDialog(context,DatePickerDialog.OnDateSetListener{
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
        val fechaActual:List<String> = fechaCreacion.split("/")
        val diaActual = fechaActual[0]
        val mesActual = fechaActual[1]
        val fechaElegida:List<String> = textFinaliza.text.toString().split("/")
        val diaElegido = fechaElegida[0]
        val mesElegido = fechaElegida[1]
        val textFinalizaSinYear = "${fechaElegida[0]}/${fechaElegida[1]}"
        if (mesActual.toInt()<=mesElegido.toInt()){
            if(diaElegido.toInt()>=diaActual.toInt()){
                if(context is DetalleTareaActivity){
                    val activity = DetalleTareaActivity()
                    activity.updateTarea(textTitulo.text.toString(),textContenido.text.toString(),
                    textFinalizaSinYear,DatosTareaElegidaDetalle.id,context)
                    activity.consultarDatosNuevos(context,DatosTareaElegidaDetalle.id)
                }
                else{
                    val listObject = mutableListOf<Tareas>()
                    val objectVal0 = Tareas(
                            0, textTitulo.text.toString(), textContenido.text.toString(),fechaCreacion,
                            textFinalizaSinYear,false)
                    listObject.add(0,objectVal0)
                    val activity = ListTareasFragment()
                    activity.insertarBD(listObject,context,"ExtensionFunction")
                    activity.consultar(context,"ExtensionFunction")
                }
                mAlertDialog.dismiss()
            }else{
                Toast.makeText(context,"El dia elegido no es valido",Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(context,"El mes elegido no es valido",Toast.LENGTH_LONG).show()
        }

    }
    cancelar.setOnClickListener { mAlertDialog.dismiss() }
}

fun fechaActual():String {
    val date: Date = Calendar.getInstance().time
    val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
    val today: String = formatter.format(date)
    val partes :List<String> = today.split("/")
    return "${partes[0]}/${partes[1]}"
}

fun searchAutomaticSaldivar(repetitiveTask:()->Unit,successTask:()->Unit) {
    CoroutineScope(Dispatchers.Default).async {
        while (herramientaObservador.comentarioEnProceso.isEmpty() || herramientaObservador.comentarioEnProceso.isBlank()) {
            delay(1500)
            repetitiveTask()
        }
        herramientaObservador.comentarioEnProceso = ""
        successTask()
    }
}


