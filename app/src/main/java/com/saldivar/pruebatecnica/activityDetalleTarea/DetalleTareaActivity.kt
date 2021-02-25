package com.saldivar.pruebatecnica.activityDetalleTarea

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.activityDetalleTarea.fragment.ListComentariosFragment
import com.saldivar.pruebatecnica.activityMain.fragment.ListTareasFragment
import kotlinx.android.synthetic.main.activity_detalle_tarea.*

class DetalleTareaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_tarea)
        supportActionBar!!.hide()
        iu()
        openFragment(ListComentariosFragment.newInstance())
    }

    private fun iu() {
        textViewToolbar.text = DatosTareaElegidaDetalle.titulo
        creacionDetalle.text = "Creada: ${DatosTareaElegidaDetalle.creacion}"
        finalizacionDetalle.text = "Finaliza: ${DatosTareaElegidaDetalle.finalizacion}"
        descricionDetalle.text = DatosTareaElegidaDetalle.detalle
    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_comentarios,fragment)
            addToBackStack(null)
            commit() }}
}