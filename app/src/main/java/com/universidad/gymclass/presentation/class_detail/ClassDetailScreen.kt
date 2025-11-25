package com.universidad.gymclass.presentation.class_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetailScreen(
    navController: NavController,
    classId: String,
    reservationId: String?,
    classDate: java.util.Date,
    viewModel: ClassDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    LaunchedEffect(state.statusMessage) {
        state.statusMessage?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    LaunchedEffect(state.reservationCancelled) {
        if (state.reservationCancelled) {
            navController.popBackStack()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Detalle de la Clase") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.gymClass != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(text = state.gymClass!!.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = state.gymClass!!.description, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(24.dp))

                    InfoRow(icon = Icons.Default.Person, label = "Instructor", text = state.gymClass!!.instructor)
                    InfoRow(icon = Icons.Default.Schedule, label = "Horario", text = "${state.gymClass!!.startTime} - ${state.gymClass!!.endTime}")
                    InfoRow(icon = Icons.Default.CalendarToday, label = "Disponibilidad", text = "${state.gymClass!!.availableSlots} lugares disponibles")

                    Spacer(modifier = Modifier.weight(1f))

                    if (state.isReserved) {
                        Button(
                            onClick = { viewModel.onCancelClicked() },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !state.isProcessingReservation,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Cancelar Reserva")
                        }
                    } else {
                        Button(
                            onClick = { viewModel.onReserveClicked() },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !state.isProcessingReservation && state.gymClass!!.availableSlots > 0
                        ) {
                            Text(if (state.gymClass!!.availableSlots > 0) "Reservar Lugar" else "Clase Llena")
                        }
                    }
                }
            } else if (state.error != null) {
                Text(
                    text = "Error: ${state.error}",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)) {
        Icon(icon, contentDescription = label, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = text, style = MaterialTheme.typography.bodyLarge)
        }
    }
}