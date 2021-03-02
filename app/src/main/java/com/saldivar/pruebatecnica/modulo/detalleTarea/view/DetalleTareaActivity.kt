package com.saldivar.pruebatecnica.modulo.detalleTarea.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.saldivar.pruebatecnica.*
import com.saldivar.pruebatecnica.helper.Animation
import com.saldivar.pruebatecnica.helper.searchAutomatic
import com.saldivar.pruebatecnica.modulo.HomeActivity.View.HomeActivity
import kotlinx.android.synthetic.main.activity_detalle_tarea.*

class DetalleTareaActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_tarea)
        supportActionBar!!.hide()
        openFragment(ListComentariosFragment.newInstance())
        searchAutomatic(::repetitiveTask,::successTask)
    }

    override fun onBackPressed() {
        val fragment = ListComentariosFragment()
        fragment.backActivity("",this)
    }

    private fun successTask() {
        check.backgroundTintList = resources.getColorStateList(R.color.teal_700)
        searchAutomatic(::repetitiveTask,::successTask)
    }

    private fun repetitiveTask() {
        check.backgroundTintList = resources.getColorStateList(R.color.gris)
        Animation.comentarioEnProgreso = comentario_new.text.toString()
    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_comentarios,fragment)
                    .addToBackStack(null)
                    .commit() }}

}