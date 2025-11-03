package com.universidad.gymclass.data.datasource.remote.firebase

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.universidad.gymclass.data.datasource.remote.dto.ClassDto
import com.universidad.gymclass.data.datasource.remote.dto.ReservationDto
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class FirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val classesCollection = firestore.collection("classes")
    private val reservationsCollection = firestore.collection("reservations")

    suspend fun getClasses(): List<Pair<String, ClassDto>> {
        return try {
            val snapshot = classesCollection.get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(ClassDto::class.java)?.let { dto ->
                    doc.id to dto
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun createReservation(reservationDto: ReservationDto) {
        reservationsCollection.add(reservationDto).await()
    }

    suspend fun getUserReservations(userId: String): List<Pair<String, ReservationDto>> {
        return try {
            val snapshot = reservationsCollection.whereEqualTo("userId", userId).get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(ReservationDto::class.java)?.let { dto ->
                    doc.id to dto
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun cancelReservation(reservationId: String) {
        reservationsCollection.document(reservationId).delete().await()
    }

    suspend fun hasExistingReservation(userId: String, classId: String, date: Date): Boolean {
        val snapshot = reservationsCollection
            .whereEqualTo("userId", userId)
            .whereEqualTo("classId", classId)
            .whereEqualTo("classDate", Timestamp(date))
            .limit(1)
            .get().await() // Corrected line
        return !snapshot.isEmpty
    }
}