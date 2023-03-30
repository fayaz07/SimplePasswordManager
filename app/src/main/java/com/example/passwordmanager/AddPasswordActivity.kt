package com.example.passwordmanager

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AddPasswordActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val viewModel = AddPasswordViewModel(database!!)

    setContent {
      Scaffold(
        topBar = {
          TopAppBar(
            navigationIcon = {
              IconButton(onClick = { }) {
                Icon(Icons.Default.ArrowBack, "back")
              }
            },
            title = {
              Text(text = "Add Password")
            }
          )
        }
      ) {
        Content(it, viewModel)
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
  paddingValues: PaddingValues,
  viewModel: AddPasswordViewModel
) {
  val activity = (LocalContext.current as Activity)
  var username by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }

  LaunchedEffect(Unit) {
    viewModel.events.consumeAsFlow().collect {
      if (it == 100) {
        activity.finish()
      }
    }
  }

  Column(
    modifier = Modifier
      .padding(paddingValues)
      .padding(16.dp)
  ) {
    OutlinedTextField(
      value = username,
      onValueChange = {
        username = it
      },
      label = { Text(text = "Username") }
    )
    OutlinedTextField(
      value = password,
      onValueChange = {
        password = it
      },
      label = { Text(text = "Password") }
    )

    OutlinedButton(onClick = {
      viewModel.savePassword(username, password)
    }) {
      Text(text = "Save")
    }
  }
}
