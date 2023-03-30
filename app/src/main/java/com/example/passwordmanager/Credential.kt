package com.example.passwordmanager

data class Credential(val username: String, val password: String, var shown: Boolean = false)