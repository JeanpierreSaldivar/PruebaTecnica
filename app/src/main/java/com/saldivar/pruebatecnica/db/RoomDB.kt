package com.saldivar.pruebatecnica.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tareas::class],version = 1)
abstract class RoomDB: RoomDatabase() {
    abstract fun roomDAO():RoomDao

    companion object{
        private lateinit var context: Context
        private val database : RoomDB by lazy (LazyThreadSafetyMode.SYNCHRONIZED){
            Room.databaseBuilder(context,RoomDB::class.java,"room.db")
                .allowMainThreadQueries().build()
        }
        fun getDataBase(context:Context):RoomDB{
            Companion.context = context.applicationContext
            return database
        }
    }
}