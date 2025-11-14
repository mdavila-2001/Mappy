package com.mdavila_2001.mappyapp.ui.components.global

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    logOutEnabled: Boolean = true,
    backEnabled: Boolean = false,
    onLogoutClick: () -> Unit,
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier
) {
    TopAppBar(
        title = {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.Black
                )
            }
        },
        navigationIcon = {
            if (backEnabled && onBackClick != null) {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.Black
                    )
                }
            }
        },
        actions = {
            if (logOutEnabled) {
                IconButton(
                    onClick = onLogoutClick
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Salir",
                        tint = Color.Black
                    )
                }
            }
        },
        modifier = modifier
            .padding(bottom = 4.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0XFF16FA2C),
                        Color(0XFF28E4FA)
                    ),
                    start = Offset(-200f, 0f),
                    end = Offset(500f, 1000f)
                )
            ),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    AppBar(
        title = "Mappy App",
        onLogoutClick = {},
        modifier = Modifier.height(64.dp),
        backEnabled = true,
        onBackClick = {}
    )
}