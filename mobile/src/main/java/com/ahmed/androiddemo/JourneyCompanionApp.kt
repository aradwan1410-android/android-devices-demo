package com.ahmed.androiddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ahmed.androiddemo.shared.Trip
import com.ahmed.androiddemo.shared.TripRepository
import com.ahmed.androiddemo.shared.TripStatus
import com.ahmed.androiddemo.shared.SampleTrips

class JourneyCompanionApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TripApp()
                }
            }
        }
    }
}

@Composable
fun TripApp() {
    var trips by remember { mutableStateOf(TripRepository.getAllTrips()) }
    var activeTrip by remember { mutableStateOf(TripRepository.getActiveTrip()) }
    var showNewTripDialog by remember { mutableStateOf(false) }

    // Initialize with sample trip if empty
    LaunchedEffect(Unit) {
        if (trips.isEmpty()) {
            TripRepository.createTrip(
                destinationName = "Science Museum",
                destinationAddress = "Exhibition Rd, South Kensington, London",
                etaMinutes = 25,
                distanceKm = 18.5,
                weatherCondition = "Partly Cloudy",
                temperatureCelsius = 19
            )
            trips = TripRepository.getAllTrips()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Journey Companion") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showNewTripDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("+")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (activeTrip != null) {
                ActiveTripCard(
                    trip = activeTrip!!,
                    onStartNavigation = {
                        TripRepository.startTrip(activeTrip!!.id)
                        activeTrip = TripRepository.getActiveTrip()
                        trips = TripRepository.getAllTrips()
                    },
                    onArrive = {
                        TripRepository.arriveAtDestination(activeTrip!!.id)
                        activeTrip = TripRepository.getActiveTrip()
                        trips = TripRepository.getAllTrips()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = "Your Trips",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(trips) { trip ->
                    TripListItem(
                        trip = trip,
                        onSelect = {
                            TripRepository.startTrip(trip.id)
                            activeTrip = TripRepository.getActiveTrip()
                            trips = TripRepository.getAllTrips()
                        }
                    )
                }
            }
        }
    }

    if (showNewTripDialog) {
        NewTripDialog(
            onDismiss = { showNewTripDialog = false },
            onConfirm = { name, address ->
                TripRepository.createTrip(
                    destinationName = name,
                    destinationAddress = address
                )
                trips = TripRepository.getAllTrips()
                showNewTripDialog = false
            }
        )
    }
}

@Composable
fun ActiveTripCard(
    trip: Trip,
    onStartNavigation: () -> Unit,
    onArrive: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Active Trip",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = trip.destinationName,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TripStat(label = "ETA", value = "${trip.etaMinutes} min")
                TripStat(label = "Distance", value = "${trip.distanceKm} km")
                TripStat(label = "Weather", value = "${trip.temperatureCelsius}°C")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onStartNavigation) {
                    Text("Start Navigation")
                }
                if (trip.status == TripStatus.IN_PROGRESS) {
                    Button(
                        onClick = onArrive,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Arrived")
                    }
                }
            }
        }
    }
}

@Composable
fun TripStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun TripListItem(
    trip: Trip,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onSelect
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = trip.destinationName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = trip.destinationAddress,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Status: ${trip.status.name.replace('_', ' ')}",
                    style = MaterialTheme.typography.bodySmall,
                    color = when (trip.status) {
                        TripStatus.PLANNED -> MaterialTheme.colorScheme.primary
                        TripStatus.IN_PROGRESS -> MaterialTheme.colorScheme.tertiary
                        TripStatus.ARRIVED -> MaterialTheme.colorScheme.secondary
                        TripStatus.CANCELLED -> MaterialTheme.colorScheme.error
                    }
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${trip.etaMinutes} min",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${trip.distanceKm} km",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun NewTripDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var destinationName by remember { mutableStateOf("") }
    var destinationAddress by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Trip") },
        text = {
            Column {
                OutlinedTextField(
                    value = destinationName,
                    onValueChange = { destinationName = it },
                    label = { Text("Destination Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = destinationAddress,
                    onValueChange = { destinationAddress = it },
                    label = { Text("Destination Address") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(destinationName, destinationAddress) },
                enabled = destinationName.isNotBlank() && destinationAddress.isNotBlank()
            ) {
                Text("Create Trip")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
