package com.example.passwordmanager

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.single

class Database constructor(protected val context: Context) {
    private val Context.dataStore by preferencesDataStore(
        name = "store",
    )

    fun getPasswords(): Flow<Preferences> {
        return context.dataStore.data
    }

  suspend fun savePassword(username: String, password: String) {
    context.dataStore.edit {
      it[stringPreferencesKey(username)] = password
    }
  }
}
