package com.mdavila_2001.mappyapp.ui.components.routes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdavila_2001.mappyapp.R
import com.mdavila_2001.mappyapp.data.remote.models.Route
import com.mdavila_2001.mappyapp.ui.theme.MappyTheme

@Composable
fun RouteList(
    routes: List<Route>,
    onRouteClick: (Route) -> Unit,
    isMine: Boolean,
    onEditClick: (Route) -> Unit,
    onDeleteClick: (Route) -> Unit
) {
    if(routes.isEmpty()){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(
                        R.drawable.no_routes
                    ),
                    contentDescription = "Sin rutas"
                )
                Text(if (isMine) "Sin rutas tuyas por el momento, dale al signo '+' para crear tus rutas." else "No hay rutas disponibles")
            }
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(routes) { route ->
                RouteItem(
                    route = route,
                    onClick = { onRouteClick(route) },
                    isMine = isMine,
                    onEditClick = { onEditClick(route) },
                    onDeleteClick = { onDeleteClick(route) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RouteListPreview() {
    MappyTheme() {
        val sampleRoutes = listOf(
            Route(
                id = 1,
                name = "Ruta 1",
                username = "user123",
                createdAt = "2023-01-01",
                updatedAt = "2023-01-01"
            ),
            Route(
                id = 2,
                name = "Ruta 2",
                username = "user123",
                createdAt = "2023-01-01",
                updatedAt = "2023-01-01"
            )
        )
        RouteList(
            routes = sampleRoutes,
            onRouteClick = {},
            isMine = true,
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}