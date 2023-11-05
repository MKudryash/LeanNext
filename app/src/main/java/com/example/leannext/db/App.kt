package com.example.leannext.db

import android.app.Application

class App : Application() {
    val database by lazy { MainDb.createDataBase(this) }
}