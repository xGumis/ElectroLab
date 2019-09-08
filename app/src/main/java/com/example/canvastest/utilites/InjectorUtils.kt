package com.example.canvastest.utilites

import android.content.Context
import com.example.canvastest.database.AppDatabase
import com.example.canvastest.view_models.MainActivityViewModelFactory

object InjectorUtils {

    private fun getDatabase(context: Context): AppDatabase {
           return AppDatabase.getInstance(context.applicationContext)
    }

    fun provideMainActivityViewModelFactory(context: Context): MainActivityViewModelFactory {
        val repository = getDatabase(context)
        return MainActivityViewModelFactory(repository)
    }

}