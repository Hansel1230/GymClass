package com.universidad.gymclass.data.datasource.remote.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.universidad.gymclass.data.datasource.remote.dto.ClassDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getClasses(): List<ClassDto> {
        return try {
            val snapshot = firestore.collection("classes").get().await()
            // Mapeamos manualmente para incluir el ID del documento, que no viene en toObjects()
            snapshot.documents.mapNotNull { document ->
                val classDto = document.toObject(ClassDto::class.java)
                classDto?.copy(id = document.id) // Asignamos el ID del documento al campo 'id' del DTO
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}