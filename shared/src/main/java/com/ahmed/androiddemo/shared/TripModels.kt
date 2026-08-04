package com.ahmed.androiddemo.shared

/**
 * Represents a trip destination with all relevant information
 */
data class Trip(
    val id: String,
    val destinationName: String,
    val destinationAddress: String,
    val etaMinutes: Int,
    val distanceKm: Double,
    val weatherCondition: String,
    val temperatureCelsius: Int,
    val status: TripStatus = TripStatus.PLANNED,
    val imageUrl: String? = null,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

/**
 * Trip status enum for state management across devices
 */
enum class TripStatus {
    PLANNED,        // Trip is planned but not started
    IN_PROGRESS,    // Currently driving/traveling
    ARRIVED,        // Reached destination
    CANCELLED       // Trip was cancelled
}

/**
 * Simple in-memory repository for trip data
 * In production, this would use Room, Firebase, or other persistence
 */
object TripRepository {
    
    private val trips = mutableMapOf<String, Trip>()
    private var activeTripId: String? = null
    
    /**
     * Get the currently active trip (if any)
     */
    fun getActiveTrip(): Trip? {
        return activeTripId?.let { trips[it] }
    }
    
    /**
     * Get all trips
     */
    fun getAllTrips(): List<Trip> {
        return trips.values.toList()
    }
    
    /**
     * Create a new trip
     */
    fun createTrip(
        destinationName: String,
        destinationAddress: String,
        etaMinutes: Int = 30,
        distanceKm: Double = 25.0,
        weatherCondition: String = "Sunny",
        temperatureCelsius: Int = 22,
        latitude: Double = 0.0,
        longitude: Double = 0.0
    ): Trip {
        val trip = Trip(
            id = System.currentTimeMillis().toString(),
            destinationName = destinationName,
            destinationAddress = destinationAddress,
            etaMinutes = etaMinutes,
            distanceKm = distanceKm,
            weatherCondition = weatherCondition,
            temperatureCelsius = temperatureCelsius,
            latitude = latitude,
            longitude = longitude
        )
        trips[trip.id] = trip
        return trip
    }
    
    /**
     * Start a trip (set as active)
     */
    fun startTrip(tripId: String): Boolean {
        val trip = trips[tripId] ?: return false
        val updatedTrip = trip.copy(status = TripStatus.IN_PROGRESS)
        trips[tripId] = updatedTrip
        activeTripId = tripId
        return true
    }
    
    /**
     * Mark trip as arrived
     */
    fun arriveAtDestination(tripId: String): Boolean {
        val trip = trips[tripId] ?: return false
        val updatedTrip = trip.copy(status = TripStatus.ARRIVED)
        trips[tripId] = updatedTrip
        if (activeTripId == tripId) {
            activeTripId = null
        }
        return true
    }
    
    /**
     * Cancel a trip
     */
    fun cancelTrip(tripId: String): Boolean {
        val trip = trips[tripId] ?: return false
        val updatedTrip = trip.copy(status = TripStatus.CANCELLED)
        trips[tripId] = updatedTrip
        if (activeTripId == tripId) {
            activeTripId = null
        }
        return true
    }
    
    /**
     * Update ETA (simulating traffic changes)
     */
    fun updateEta(tripId: String, newEtaMinutes: Int): Boolean {
        val trip = trips[tripId] ?: return false
        val updatedTrip = trip.copy(etaMinutes = newEtaMinutes)
        trips[tripId] = updatedTrip
        return true
    }
    
    /**
     * Clear all trips (for demo reset)
     */
    fun clearAll() {
        trips.clear()
        activeTripId = null
    }
}

/**
 * Sample trip data for demo purposes
 */
object SampleTrips {
    fun createSampleTrip(): Trip {
        return Trip(
            id = "sample-1",
            destinationName = "Science Museum",
            destinationAddress = "Exhibition Rd, South Kensington, London",
            etaMinutes = 25,
            distanceKm = 18.5,
            weatherCondition = "Partly Cloudy",
            temperatureCelsius = 19,
            status = TripStatus.PLANNED,
            latitude = 51.4978,
            longitude = -0.1747
        )
    }
    
    fun createSampleActiveTrip(): Trip {
        return Trip(
            id = "sample-active-1",
            destinationName = "Tate Modern",
            destinationAddress = "Bankside, London SE1 9TG",
            etaMinutes = 15,
            distanceKm = 12.3,
            weatherCondition = "Sunny",
            temperatureCelsius = 21,
            status = TripStatus.IN_PROGRESS,
            latitude = 51.5076,
            longitude = -0.0994
        )
    }
}
