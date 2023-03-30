package com.example.passwordmanager

import android.annotation.SuppressLint
import android.app.Application

@SuppressLint("StaticFieldLeak")
var database: Database? = null

class PasswordManagerApp: Application() {
  override fun onCreate() {
    super.onCreate()
    database = Database(this)
  }
}
