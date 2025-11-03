package com.universidad.gymclass.presentation.reservations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.universidad.gymclass.domain.model.ReservationWithClassDetails
import com.universidad.gymclass.presentation.navigation.Screen
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyReservationsScreen(
    navController: NavController,
    viewModel: ReservationsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Reservas") },
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
            } else if (state.reservations.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Aún no tienes reservas.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.reservations) { reservationDetails ->
                        ReservationCard(
                            reservationDetails = reservationDetails,
                            onClick = {
                                // Navegamos a la pantalla de detalle, pasando ambos IDs
                                navController.navigate(
                                    Screen.ClassDetailScreen.createRoute(
                                        classId = reservationDetails.gymClass.id,
                                        reservationId = reservationDetails.reservation.id
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReservationCard(reservationDetails: ReservationWithClassDetails, onClick: () -> Unit) {
    val dateFormatter = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = reservationDetails.gymClass.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Instructor: ${reservationDetails.gymClass.instructor}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Fecha: ${dateFormatter.format(reservationDetails.reservation.classDate)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Estado: ${reservationDetails.reservation.status}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}