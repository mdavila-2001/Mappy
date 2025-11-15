package com.mdavila_2001.mappyapp.ui.components.global

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mdavila_2001.mappyapp.ui.theme.MappyTheme
import org.w3c.dom.Text

@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier
) {
    NavigationBar(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF03FF1E),
                        Color(0xFF28E4FA)
                    ),
                    start = Offset(-500f, 0f),
                    end = Offset(500f, 0f)
                )
            ),
        containerColor = Color.Transparent,
    ) {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            icon = {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = "Todas las Rutas",
                    tint = if (selectedTab == 0) Color.White else Color.Black
                )
            },
            label = {
                Text(
                    "Buscar",
                    color =Color.Black
                )
            }
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Mis Rutas",
                    tint = if (selectedTab == 1) Color.White else Color.Black
                )
            },
            label = {
                Text(
                    "Mis Rutas",
                    color = Color.Black
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    MappyTheme() {
        BottomNavigationBar(
            selectedTab = 0,
            onTabSelected = {},
            modifier = Modifier
        )
    }
}