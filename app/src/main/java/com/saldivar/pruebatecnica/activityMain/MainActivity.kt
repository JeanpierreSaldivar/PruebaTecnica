package com.saldivar.pruebatecnica.activityMain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.activityMain.fragment.ListTareasFragment
import com.saldivar.pruebatecnica.db.RoomDB
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list_tareas.*

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
        /*RoomDB.getDataBase(this).roomDAO().createTableTareas()*/
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
              openFragment(ListTareasFragment.newInstance())
          }
        }
    }
}