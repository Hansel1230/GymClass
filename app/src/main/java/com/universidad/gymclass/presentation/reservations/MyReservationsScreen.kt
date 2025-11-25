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
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(androidx.compose.ui.Alignment.Center))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.reservations) { reservationDetails ->
                    ReservationItem(
                        reservationDetails = reservationDetails,
                        onClick = {
                            navController.navigate(
                                Screen.ClassDetailScreen.createRoute(
                                    classId = reservationDetails.gymClass.id,
                                    reservationId = reservationDetails.reservation.id,
                                    classDate = reservationDetails.reservation.classDate.time
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ReservationItem(
    reservationDetails: ReservationWithClassDetails,
    onClick: () -> Unit
) {
    val dateFormatter = SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
    val formattedDate = dateFormatter.format(reservationDetails.reservation.classDate)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = reservationDetails.gymClass.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Instructor: ${reservationDetails.gymClass.instructor}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Fecha: $formattedDate")
            Text(
                text = "Estado: ${reservationDetails.reservation.status}",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}