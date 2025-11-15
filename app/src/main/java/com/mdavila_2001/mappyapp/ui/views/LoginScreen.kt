package com.mdavila_2001.mappyapp.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mdavila_2001.mappyapp.R
import com.mdavila_2001.mappyapp.ui.NavRoutes
import com.mdavila_2001.mappyapp.ui.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    var userName by rememberSaveable { mutableStateOf("") }

    val navigate by viewModel.navigateToMain.collectAsState()

    LaunchedEffect(navigate) {
        if(navigate){
            navController.navigate(NavRoutes.RoutesList.route) {
                popUpTo(NavRoutes.Login.route) {
                    inclusive = true
                }
            }
            viewModel.onNavigationDone()
        }
    }

    Scaffold() { paddingValues ->
        LoginView(
            username = userName,
            onUserNameChanged = { userName = it },
            onLoginClick = { viewModel.onLoginClicked(userName) },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun LoginView(
    username: String,
    onUserNameChanged: (String) -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo Mappy",
            modifier = Modifier.size(360.dp)
        )
        Text(
            text = "Ingrese su usuario:",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value= username,
            onValueChange = onUserNameChanged,
            placeholder = { Text("Nombre de Usuario") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLoginClick,
            enabled = username.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Iniciar Sesión")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginView(
        username = "",
        onUserNameChanged = {},
        onLoginClick = {},
        modifier = Modifier
    )
}