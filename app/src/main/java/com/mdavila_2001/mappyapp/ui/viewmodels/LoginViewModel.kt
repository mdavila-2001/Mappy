package com.mdavila_2001.mappyapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mdavila_2001.mappyapp.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(application: Application): AndroidViewModel(application) {
    private val sessionManager = SessionManager(application)

    private val _navigateToMain = MutableStateFlow(false)
    val navigateToMain: StateFlow<Boolean> = _navigateToMain

    fun onLoginClicked(username: String) {
        if(username.isBlank()) {
            return
        }
        sessionManager.saveUsername(username.trim())
        _navigateToMain.value = true
    }

    fun onNavigationDone() {
        _navigateToMain.value = false
    }
}