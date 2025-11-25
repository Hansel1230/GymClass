package com.universidad.gymclass.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.universidad.gymclass.domain.model.GymClass
import com.universidad.gymclass.presentation.navigation.Screen
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.isUserLoggedOut) {
        if (state.isUserLoggedOut) {
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(0) // Limpia todo el back stack
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GymClass", fontWeight = FontWeight.Bold) },
                actions = {
                    TextButton(onClick = { navController.navigate(Screen.MyReservationsScreen.route) }) {
                        Icon(Icons.Default.CalendarToday, contentDescription = "Mis Reservas", modifier = Modifier.padding(end = 4.dp))
                        Text("Mis Reservas")
                    }
                    IconButton(onClick = { viewModel.signOut() }) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Cerrar Sesión")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                WelcomeMessage()
                DayFilterBar(
                    selectedDay = state.selectedDay,
                    onDaySelected = { viewModel.onDaySelected(it) }
                )

                val classesToShow = if (state.selectedDay == null) {
                    state.classes
                } else {
                    state.classes.filter { it.dayOfWeek == state.selectedDay }
                }

                val groupedClasses = classesToShow.groupBy { it.dayOfWeek }
                val sortedGroupedClasses = groupedClasses.entries.sortedBy { it.key }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    sortedGroupedClasses.forEach { (day, classes) ->
                        item {
                            Text(
                                text = day.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                            )
                        }
                        items(classes) { gymClass ->
                            ClassCard(
                                gymClass = gymClass,
                                onClick = {
                                    navController.navigate(
                                        Screen.ClassDetailScreen.createRoute(
                                            classId = gymClass.id,
                                            reservationId = null,
                                            classDate = System.currentTimeMillis()
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
}

@Composable
fun WelcomeMessage() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Bienvenido 👋", style = MaterialTheme.typography.titleMedium)
            Text(text = "Explora y reserva tus clases favoritas del gimnasio", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun DayFilterBar(selectedDay: DayOfWeek?, onDaySelected: (DayOfWeek?) -> Unit) {
    val days = listOf(null) + DayOfWeek.values().toList()

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(days) { day ->
            val isSelected = day == selectedDay
            FilterChip(
                selected = isSelected,
                onClick = { onDaySelected(day) },
                label = {
                    Text(
                        text = when (day) {
                            null -> "Todos"
                            else -> day.getDisplayName(TextStyle.SHORT, Locale("es", "ES"))
                        }
                    )
                },
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}


@Composable
fun ClassCard(gymClass: GymClass, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = gymClass.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(text = "Instructor: ${gymClass.instructor}", style = MaterialTheme.typography.bodyMedium)
            Text(text = gymClass.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Schedule, contentDescription = "Horario", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${gymClass.startTime} - ${gymClass.endTime}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Person, contentDescription = "Cupos", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${gymClass.availableSlots} lugares disponibles",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}