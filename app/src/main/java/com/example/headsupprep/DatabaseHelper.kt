package com.example.headsupprep

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.headsupprep.apiModel.CelebritiesItem

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,"celebritie.db",null,2) {
    private val db: SQLiteDatabase = writableDatabase
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table celebrities (pk INTEGER PRIMARY KEY AUTOINCREMENT ,name Text , taboo1 Text ,taboo2 Text, taboo3 Text)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS celebrities")
        onCreate(p0)
    }

    fun saveData(name: String,taboo1: String,taboo2:String,taboo3:String){
        val values = ContentValues()
        values.put("name",name)
        values.put("taboo1",taboo1)
        values.put("taboo2",taboo2)
        values.put("taboo3",taboo3)
        db.insert("celebrities",null,values)
    }

    fun read():ArrayList<CelebritiesItem>{
        val list = arrayListOf<CelebritiesItem>()
        val cursor: Cursor = db.rawQuery("SELECT * FROM celebrities",null)
        if (cursor.count < 1){
            list.add(
                CelebritiesItem("No Celebrity yet",0,
               "","","")
            )
        }else{
            while (cursor.moveToNext()){
                list.add(
                    CelebritiesItem(cursor.getString(1),cursor.getInt(0),
                    cursor.getString(2),cursor.getString(3),cursor.getString(4))
                )
            }
        }
        return list
    }
}