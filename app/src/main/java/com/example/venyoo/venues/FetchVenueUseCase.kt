package com.example.venyoo.venues

import com.example.venyoo.networking.TicketMasterApi
import com.example.venyoo.screens.VenueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Use usecase when logic requires more than one repository. inject into viewmodel(?)
 */
//class FetchVenueUseCase @Inject constructor(private val venueRepository: VenueRepository) {
//
//    sealed class Result {
//        data class Success(val venus: List<Venue>) : Result()
//        object Failure : Result()
//    }
//
//    suspend fun fetchVenues(venueName: String): Result {
//        return withContext(Dispatchers.IO) {
//            try {
//                val venue = ticketMasterApi.venue(venueName)
//                if (venue != null) {
//                    return@withContext Result.Success(ticketMasterApi.venue(venueName))
//                } else {
//                    return@withContext Result.Failure
//                }
//            } catch (e: Exception){ //TODO: EXCEPTION HANDLING
//                return@withContext Result.Failure
//            }
//        }
//    }
//}