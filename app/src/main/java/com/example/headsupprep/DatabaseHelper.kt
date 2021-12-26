package com.example.headsupprep

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,"celebritie.db",null,1) {
    private val db: SQLiteDatabase = writableDatabase
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table celebrities (name Text , taboo1 Text ,taboo2 Text, taboo3 Text)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun saveData(name: String,taboo1: String,taboo2:String,taboo3:String){
        val values = ContentValues()
        values.put("name",name)
        values.put("taboo1",taboo1)
        values.put("taboo2",taboo2)
        values.put("taboo3",taboo3)
        db.insert("celebrities",null,values)
    }
}