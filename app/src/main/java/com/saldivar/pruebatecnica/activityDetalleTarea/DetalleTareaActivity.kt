package com.saldivar.pruebatecnica.activityDetalleTarea

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.saldivar.pruebatecnica.*
import com.saldivar.pruebatecnica.activityDetalleTarea.fragment.ListComentariosFragment
import com.saldivar.pruebatecnica.activityMain.MainActivity
import com.saldivar.pruebatecnica.activityMain.estadoVisualizadorTareas
import com.saldivar.pruebatecnica.db.Comentarios
import com.saldivar.pruebatecnica.db.Tareas
import kotlinx.android.synthetic.main.activity_detalle_tarea.*

class DetalleTareaActivity : AppCompatActivity(), View.OnClickListener,
    DetalleTareaActivityViewInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_tarea)
        supportActionBar!!.hide()
        iu()
        estadoVisualizadorTareas.ojo = false
        presenter = DetalleTareaActivityPresenter(this)
        openFragment(ListComentariosFragment.newInstance())
        searchAutomaticSaldivar(::repetitiveTask,::successTask)
    }
    companion object{
        private lateinit var presenter: DetalleTareaActivityPresenterInterface
    }

    override fun onBackPressed() {
        backActivity(this)
    }
    private fun iu() {
        setearDatosVista()
        delete.setOnClickListener(this)
        check.setOnClickListener(this)
        edit.setOnClickListener(this)
    }

    private fun setearDatosVista() {
        textViewToolbar.text = DatosTareaElegidaDetalle.titulo
        creacionDetalle.text = "Creada: ${DatosTareaElegidaDetalle.creacion}"
        finalizacionDetalle.text = "Finaliza: ${DatosTareaElegidaDetalle.finalizacion}"
        descricionDetalle.text = DatosTareaElegidaDetalle.detalle
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.check->{
                val dato = comentario_new.text.toString()
                if(dato.isNotEmpty()){
                    val list = mutableListOf<Comentarios>()
                    val objectComentario = Comentarios(0,DatosTareaElegidaDetalle.id,
                    "anonimus",dato)
                    list.add(0,objectComentario)
                    presenter.insertarComentarioBD(this,list)
                    val fragment = ListComentariosFragment()
                    fragment.consultar(this)
                    OcultarTeclado.hideSoftKeyBoard(this, comentario_new)
                    comentario_new.setText("")
                    comentario_new.clearFocus()
                }
            }
            R.id.edit->{
                com.saldivar.pruebatecnica.showDialog(dialog(),this)
            }
            R.id.delete->{
                showDialogDelete(dialogDelete(),this)
            }
        }
    }

    internal fun eliminarComentarios(context: Context, id: Int) {
        presenter.eliminarComentarios(context,id)
    }

    internal fun eliminarTarea(context: Context,id: Int){
        presenter.eliminarTarea(context,id)
    }

    private fun successTask() {
        check.backgroundTintList = resources.getColorStateList(R.color.teal_700)
        searchAutomaticSaldivar(::repetitiveTask,::successTask)
    }

    private fun repetitiveTask() {
        check.backgroundTintList = resources.getColorStateList(R.color.gris)
        herramientaObservador.comentarioEnProceso = comentario_new.text.toString()
    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_comentarios,fragment)
            addToBackStack(null)
            commit() }}

    private fun dialog() = LayoutInflater.from(this).
    inflate(R.layout.alert_dialog_nueva_tarea, this.findViewById(R.id.alertNuevaTarea))

    internal fun updateTarea(titulo:String,descripcion:String,finalizacion:String,id:Int,context: Context){
        presenter.actualizarTarea(titulo,descripcion,finalizacion,id,context)
    }
    internal fun consultarDatosNuevos(context: Context,id: Int){
        presenter.consultarDatosNuevos(context,id)
    }

    override fun datosNuevosTareaObtenido(tareaActualizada: List<Tareas>) {
        setearValoresEnObject(tareaActualizada)
        setearDatosVista()
    }

    private fun setearValoresEnObject(tareaActualizada: List<Tareas>) {
        DatosTareaElegidaDetalle.id = tareaActualizada[0].id
        DatosTareaElegidaDetalle.titulo = tareaActualizada[0].titulo
        DatosTareaElegidaDetalle.detalle = tareaActualizada[0].descripcion
        DatosTareaElegidaDetalle.creacion = tareaActualizada[0].creacion
        DatosTareaElegidaDetalle.finalizacion = tareaActualizada[0].finalizacion
    }


    private fun dialogDelete() = LayoutInflater.from(this).
    inflate(R.layout.alert_dialog_delete, this.findViewById(R.id.alertDeleteTarea))

    internal fun backActivity(context: Context){
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        (context as Activity).overridePendingTransition(
                R.anim.right_in, R.anim.right_out
        )
        (context as Activity).finish()
    }

}