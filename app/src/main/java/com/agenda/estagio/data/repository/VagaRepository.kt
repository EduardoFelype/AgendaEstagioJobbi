package com.agenda.estagio.data.repository

import com.agenda.estagio.data.models.Vaga
import com.agenda.estagio.data.models.VagaStatus
import com.agenda.estagio.data.models.toMap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class VagaRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    
    suspend fun criarVaga(vaga: Vaga): Result<String> {
        return try {
            val empresaId = auth.currentUser?.uid ?: throw Exception("Usuário não autenticado")
            
            val vagaComEmpresa = vaga.copy(
                empresaId = empresaId,
                criadoEm = System.currentTimeMillis(),
                atualizadoEm = System.currentTimeMillis()
            )
            
            val docRef = firestore.collection("vagas")
                .add(vagaComEmpresa.toMap())
                .await()
            
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun atualizarVaga(vaga: Vaga): Result<Unit> {
        return try {
            val vagaAtualizada = vaga.copy(
                atualizadoEm = System.currentTimeMillis()
            )
            
            firestore.collection("vagas")
                .document(vaga.id)
                .set(vagaAtualizada.toMap())
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deletarVaga(vagaId: String): Result<Unit> {
        return try {
            firestore.collection("vagas")
                .document(vagaId)
                .delete()
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getVagasAtivas(): Flow<List<Vaga>> = callbackFlow {
        val listener = firestore.collection("vagas")
            .whereEqualTo("status", VagaStatus.ATIVA.name)
            .orderBy("criadoEm", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val vagas = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Vaga::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                
                trySend(vagas)
            }
        
        awaitClose { listener.remove() }
    }
    
    fun getVagasByEmpresa(empresaId: String): Flow<List<Vaga>> = callbackFlow {
        val listener = firestore.collection("vagas")
            .whereEqualTo("empresaId", empresaId)
            .orderBy("criadoEm", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val vagas = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Vaga::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                
                trySend(vagas)
            }
        
        awaitClose { listener.remove() }
    }
    
    suspend fun getVagaById(vagaId: String): Result<Vaga> {
        return try {
            val document = firestore.collection("vagas")
                .document(vagaId)
                .get()
                .await()
            
            val vaga = document.toObject(Vaga::class.java)?.copy(id = document.id)
                ?: throw Exception("Vaga não encontrada")
            
            Result.success(vaga)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun buscarVagas(query: String): Result<List<Vaga>> {
        return try {
            val snapshot = firestore.collection("vagas")
                .whereEqualTo("status", VagaStatus.ATIVA.name)
                .get()
                .await()
            
            val vagas = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Vaga::class.java)?.copy(id = doc.id)
            }.filter { vaga ->
                vaga.titulo.contains(query, ignoreCase = true) ||
                vaga.area.contains(query, ignoreCase = true) ||
                vaga.empresaNome.contains(query, ignoreCase = true) ||
                vaga.cidade.contains(query, ignoreCase = true)
            }
            
            Result.success(vagas)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
