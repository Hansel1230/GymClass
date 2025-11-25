package com.universidad.gymclass.data.datasource.remote.firebase

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.universidad.gymclass.data.datasource.remote.dto.ClassDto
import com.universidad.gymclass.data.datasource.remote.dto.ReservationDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val classesCollection = firestore.collection("classes")
    private val reservationsCollection = firestore.collection("reservations")

    fun getClassesFlow(): Flow<List<Pair<String, ClassDto>>> = callbackFlow {
        val listenerRegistration = classesCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val classes = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(ClassDto::class.java)?.let { dto ->
                        doc.id to dto
                    }
                }
                trySend(classes).isSuccess
            }
        }
        awaitClose { listenerRegistration.remove() }
    }
    
    fun getClassByIdFlow(classId: String): Flow<Pair<String, ClassDto>?> = callbackFlow {
        val listenerRegistration = classesCollection.document(classId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val dto = snapshot.toObject(ClassDto::class.java)
                    if (dto != null) {
                        trySend(snapshot.id to dto).isSuccess
                    } else {
                        trySend(null).isSuccess
                    }
                } else {
                    trySend(null).isSuccess
                }
            }
        awaitClose { listenerRegistration.remove() }
    }

    suspend fun createReservation(reservationDto: ReservationDto): String {
        val documentReference = reservationsCollection.add(reservationDto).await()
        return documentReference.id
    }

    fun getUserReservationsFlow(userId: String): Flow<List<Pair<String, ReservationDto>>> = callbackFlow {
        val listenerRegistration = reservationsCollection
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val reservations = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(ReservationDto::class.java)?.let { dto ->
                            doc.id to dto
                        }
                    }
                    trySend(reservations).isSuccess
                }
            }
        awaitClose { listenerRegistration.remove() }
    }

    suspend fun cancelReservation(reservationId: String) {
        reservationsCollection.document(reservationId).delete().await()
    }

    suspend fun hasExistingReservation(userId: String, classId: String): Boolean {
        val snapshot = reservationsCollection
            .whereEqualTo("userId", userId)
            .whereEqualTo("classId", classId)
            .limit(1)
            .get().await()
        return !snapshot.isEmpty
    }

    suspend fun getExistingReservation(userId: String, classId: String): Pair<String, ReservationDto>? {
        val snapshot = reservationsCollection
            .whereEqualTo("userId", userId)
            .whereEqualTo("classId", classId)
            .limit(1)
            .get().await()
        
        return snapshot.documents.firstOrNull()?.let { doc ->
            doc.toObject(ReservationDto::class.java)?.let { dto ->
                doc.id to dto
            }
        }
    }

    suspend fun getReservation(reservationId: String): Pair<String, ReservationDto>? {
        val doc = reservationsCollection.document(reservationId).get().await()
        return doc.toObject(ReservationDto::class.java)?.let { dto ->
            doc.id to dto
        }
    }

    suspend fun updateClassSlots(classId: String, amount: Long) {
        classesCollection.document(classId).update("availableSlots", FieldValue.increment(amount)).await()
    }
}