package com.example.headsupprep.roomModel

import android.content.Context
import androidx.room.*

@Database(entities = [CelebritiesRoom::class],version = 1,exportSchema = false)
abstract class CelebritiesDB: RoomDatabase() {
    abstract fun dao(): CelebritiesDao

    companion object{
        @Volatile
        private var INSTANCE: CelebritiesDB? = null
        fun getDB(context: Context): CelebritiesDB{
            val temp = INSTANCE
            if (temp != null){return temp}
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CelebritiesDB::class.java,
                    "celebrities")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}