package com.saldivar.pruebatecnica.activityMain

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.activityDetalleTarea.DetalleTareaActivity
import com.saldivar.pruebatecnica.activityMain.fragment.ListTareasFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        iu()
        openFragment(ListTareasFragment.newInstance())
    }

    private fun iu() {
        visualizador.setOnClickListener(this)
    }
    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_tareas,fragment)
            addToBackStack(null)
            commit() }}

    override fun onClick(v: View) {
        when(v.id){
          R.id.visualizador->{
              estadoVisualizadorTareas.ojo = !estadoVisualizadorTareas.ojo
              if (estadoVisualizadorTareas.ojo){
                  visualizador.background = resources.getDrawable(R.drawable.ic_baseline_visibility_off_24)
              }
              else{
                  visualizador.background = resources.getDrawable(R.drawable.ic_baseline_remove_red_eye_24)
              }
              openFragment(ListTareasFragment.newInstance())
          }
        }
    }


}