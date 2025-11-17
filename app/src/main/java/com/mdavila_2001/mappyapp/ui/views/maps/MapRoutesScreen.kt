package com.mdavila_2001.mappyapp.ui.views.maps

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.mdavila_2001.mappyapp.R
import com.mdavila_2001.mappyapp.tools.Tools.bitmapDescriptorFromVector
import com.mdavila_2001.mappyapp.ui.components.global.AppBar
import com.mdavila_2001.mappyapp.ui.viewmodels.maps.MapRoutesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapRoutesScreen(
    navController: NavController,
    viewModel: MapRoutesViewModel = viewModel(),
    routeId: Int,
    routeName: String
) {
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()

    var hasLocationPermission by remember { mutableStateOf(false) }

    var customIcon by remember {
        mutableStateOf<BitmapDescriptor?>(null)
    }

    var mapsInitialized by remember {
        mutableStateOf(false)
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                hasLocationPermission = true
            } else {
                Toast.makeText(context, "Permiso de ubicación negado", Toast.LENGTH_SHORT).show()
            }
        }
    )
    LaunchedEffect(Unit) {
        try {
            MapsInitializer.initialize(context)
            mapsInitialized = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (mapsInitialized) {
            try {
                customIcon = bitmapDescriptorFromVector(
                    context,
                    R.drawable.pin_point,
                    width = 120,
                    height = 120
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            hasLocationPermission = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        viewModel.loadLocations(routeId)
    }

    Scaffold(
        topBar = {
            AppBar(
                title = routeName,
                logOutEnabled = false,
                backEnabled = true,
                onLogoutClick = {},
                onBackClick = { navController.popBackStack() },
                modifier = Modifier
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (!hasLocationPermission) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(
                            R.drawable.no_map
                        ),
                        contentDescription = "No hay mapa"
                    )
                    Text("Se necesita permiso de ubicación para ver el mapa")
                }
            } else {
                val mapPoints = uiState.locations.mapNotNull { location ->
                    val lat = location.latitude.toDouble()
                    val lng = location.longitude.toDouble()
                    if (lat != 0.0 && lng != 0.0) {
                        LatLng(lat,lng)
                    } else {
                        null
                    }
                }

                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(
                        mapPoints.firstOrNull() ?: LatLng(-17.7833, -63.1833),
                        15f
                    )
                }

                LaunchedEffect(mapPoints) {
                    if (mapPoints.isNotEmpty()) {
                        if (mapsInitialized) {
                            try {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(
                                        mapPoints.first(),
                                        15f
                                    )
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                                cameraPositionState.position = CameraPosition.fromLatLngZoom(mapPoints.first(), 15f)
                            }
                        } else {
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(mapPoints.first(), 15f)
                        }
                    }
                }

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(isMyLocationEnabled = true)
                ) {
                    mapPoints.forEach { point ->
                        Marker(
                            state = MarkerState(position = point),
                            title = "Ubicación",
                            icon = customIcon
                        )
                    }
                }
            }
        }
    }
}