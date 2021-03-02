package com.saldivar.pruebatecnica.modulo.detalleTarea.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.saldivar.pruebatecnica.*
import com.saldivar.pruebatecnica.helper.Animation
import com.saldivar.pruebatecnica.helper.searchAutomaticSaldivar
import com.saldivar.pruebatecnica.modulo.HomeActivity.View.HomeActivity
import kotlinx.android.synthetic.main.activity_detalle_tarea.*

class DetalleTareaActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_tarea)
        supportActionBar!!.hide()
        openFragment(ListComentariosFragment.newInstance())
        searchAutomaticSaldivar(::repetitiveTask,::successTask)
    }

    override fun onBackPressed() {
        backActivity()
    }

    private fun successTask() {
        check.backgroundTintList = resources.getColorStateList(R.color.teal_700)
        searchAutomaticSaldivar(::repetitiveTask,::successTask)
    }

    private fun repetitiveTask() {
        check.backgroundTintList = resources.getColorStateList(R.color.gris)
        Animation.comentarioEnProgreso= comentario_new.text.toString()
    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_comentarios,fragment)
            addToBackStack(null)
            commit() }}

    private fun backActivity(){
        val intent = Intent(this@DetalleTareaActivity, HomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(
                R.anim.right_in, R.anim.right_out
        )
        finish()
    }

}