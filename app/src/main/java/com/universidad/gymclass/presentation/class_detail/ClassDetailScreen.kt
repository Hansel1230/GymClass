package com.universidad.gymclass.presentation.class_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetailScreen(
    navController: NavController,
    classId: String,
    reservationId: String?,
    viewModel: ClassDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.statusMessage) {
        state.statusMessage?.let { message ->
            scope.launch {
                snackbarHostState.showSnackbar(message)
            }
        }
    }
    
    LaunchedEffect(state.reservationCancelled) {
        if (state.reservationCancelled) {
            navController.popBackStack()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Detalle de la Clase") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.gymClass != null) {
                val gymClass = state.gymClass!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = gymClass.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text(text = gymClass.description, style = MaterialTheme.typography.bodyLarge)

                    Divider()

                    InfoRow(icon = Icons.Default.Person, title = "Instructor", subtitle = gymClass.instructor)
                    InfoRow(icon = Icons.Default.Schedule, title = "Horario", subtitle = "${gymClass.startTime} - ${gymClass.endTime}")
                    InfoRow(icon = Icons.Default.CalendarToday, title = "Capacidad", subtitle = "${gymClass.availableSlots} de 25 disponibles")

                    Spacer(modifier = Modifier.weight(1f))

                    // Botón dinámico
                    if (state.isReserved) {
                        Button(
                            onClick = { viewModel.onCancelClicked() },
                            enabled = !state.isProcessingReservation, // Control de habilitación
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Cancelar Reserva")
                        }
                    } else {
                        Button(
                            onClick = { viewModel.onReserveClicked() },
                            enabled = !state.isProcessingReservation, // Control de habilitación
                            modifier = Modifier.fillMaxWidth().height(50.dp)
                        ) {
                            Text("Reservar Lugar")
                        }
                    }
                }
            } else {
                Text(state.error ?: "Clase no encontrada", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, title: String, subtitle: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = title, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            Text(text = subtitle, style = MaterialTheme.typography.bodyMedium)
        }
    }
}