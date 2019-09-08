package com.example.canvastest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

const val DATABASE_NAME = "electro"
@TypeConverters(PointConverter::class)
@Database(entities = [ResistorEntity::class,FlowEntity::class,TensionEntity::class,Schema::class],version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun schemaDao():SchemaDao
    abstract fun resistorDao():ResistorDao
    abstract fun flowDao():FlowDao
    abstract fun tensionDao():TensionDao

    companion object{
        //for singleton
        @Volatile private var instance:AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .build().also { instance = it }
            }
        }
    }
}