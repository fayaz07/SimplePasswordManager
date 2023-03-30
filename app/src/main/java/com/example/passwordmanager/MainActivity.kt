package com.example.passwordmanager

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.passwordmanager.ui.theme.PasswordManagerTheme
import com.example.passwordmanager.ui.theme.Typography

class MainActivity : ComponentActivity() {

  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      PasswordManagerTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          Scaffold(
            topBar = {
              TopAppBar(
                title = { Text(text = "Password Manager App") }
              )
            },
            floatingActionButton = {
              FloatingActionButton(onClick = {
                val intent = Intent(this, AddPasswordActivity::class.java)
                startActivity(intent)
              }) {
                Icon(Icons.Default.Add, "add")
              }
            }
          ) {
            Content(it, database!!)
          }
        }
      }
    }
  }
}

@Composable
private fun Content(paddingValues: PaddingValues, db: Database) {
  val passwords = remember { mutableStateOf(emptyList<Credential>()) }
  LaunchedEffect(Unit) {
    db.getPasswords().collect {
      val list = mutableListOf<Credential>()
      it.asMap().entries.forEach { entry ->
        list.add(Credential(entry.key.name, entry.value.toString()))
      }
      passwords.value = list.toList()
    }
  }
  LazyColumn(
    modifier = Modifier.padding(paddingValues)
  ) {
    items(passwords.value.size) {
      var item by remember { mutableStateOf(passwords.value[it]) }
      Column(
        modifier = Modifier.padding(vertical = 4.dp)
      ) {
        Row {
          Column(
            modifier = Modifier.fillMaxWidth(0.7f)
          ) {
            Text(
              modifier = Modifier.padding(horizontal = 16.dp),
              text = item.username,
              style = Typography.bodyLarge
            )
            Spacer(modifier = Modifier.padding(top = 4.dp))
            Text(
              modifier = Modifier.padding(horizontal = 16.dp),
              text = if (item.shown) {
                item.password
              } else {
                "**********"
              },
              style = Typography.titleMedium
            )
            Spacer(modifier = Modifier.padding(top = 4.dp))
          }
          Spacer(modifier = Modifier.padding(start = 4.dp))
          TextButton(onClick = {
            item = item.copy(
              shown = item.shown.not()
            )
          }) {
            Text(
              text = if (item.shown) {
                "Hide"
              } else {
                "Show"
              }
            )
          }
        }
        Divider()
      }
    }
  }
}
