package com.example.passwordmanager

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class AddPasswordViewModel(val database: Database) : ViewModel() {

  val events = Channel<Int>()

  fun savePassword(username: String, password: String) {
    viewModelScope.launch {
      database.savePassword(username, password)
      events.send(100)
    }
  }
}