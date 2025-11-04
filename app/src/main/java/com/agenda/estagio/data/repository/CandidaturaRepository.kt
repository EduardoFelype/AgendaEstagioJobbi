package com.agenda.estagio.data.repository

import com.agenda.estagio.data.models.Candidatura
import com.agenda.estagio.data.models.CandidaturaStatus
import com.agenda.estagio.data.models.toMap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class CandidaturaRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    
    suspend fun candidatar(candidatura: Candidatura): Result<String> {
        return try {
            val alunoId = auth.currentUser?.uid ?: throw Exception("Usuário não autenticado")
            
            // Verificar se já existe candidatura para esta vaga
            val existente = firestore.collection("candidaturas")
                .whereEqualTo("vagaId", candidatura.vagaId)
                .whereEqualTo("alunoId", alunoId)
                .get()
                .await()
            
            if (!existente.isEmpty) {
                throw Exception("Você já se candidatou a esta vaga")
            }
            
            val candidaturaComAluno = candidatura.copy(
                alunoId = alunoId,
                criadoEm = System.currentTimeMillis(),
                atualizadoEm = System.currentTimeMillis()
            )
            
            val docRef = firestore.collection("candidaturas")
                .add(candidaturaComAluno.toMap())
                .await()
            
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun atualizarStatus(
        candidaturaId: String,
        novoStatus: CandidaturaStatus
    ): Result<Unit> {
        return try {
            firestore.collection("candidaturas")
                .document(candidaturaId)
                .update(
                    mapOf(
                        "status" to novoStatus.name,
                        "atualizadoEm" to System.currentTimeMillis()
                    )
                )
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun cancelarCandidatura(candidaturaId: String): Result<Unit> {
        return atualizarStatus(candidaturaId, CandidaturaStatus.CANCELADA)
    }
    
    fun getCandidaturasByAluno(alunoId: String): Flow<List<Candidatura>> = callbackFlow {
        val listener = firestore.collection("candidaturas")
            .whereEqualTo("alunoId", alunoId)
            .orderBy("criadoEm", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val candidaturas = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Candidatura::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                
                trySend(candidaturas)
            }
        
        awaitClose { listener.remove() }
    }
    
    fun getCandidaturasByVaga(vagaId: String): Flow<List<Candidatura>> = callbackFlow {
        val listener = firestore.collection("candidaturas")
            .whereEqualTo("vagaId", vagaId)
            .orderBy("criadoEm", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val candidaturas = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Candidatura::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                
                trySend(candidaturas)
            }
        
        awaitClose { listener.remove() }
    }
    
    fun getCandidaturasByEmpresa(empresaId: String): Flow<List<Candidatura>> = callbackFlow {
        val listener = firestore.collection("candidaturas")
            .whereEqualTo("empresaId", empresaId)
            .orderBy("criadoEm", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val candidaturas = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Candidatura::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                
                trySend(candidaturas)
            }
        
        awaitClose { listener.remove() }
    }
    
    suspend fun verificarCandidatura(vagaId: String, alunoId: String): Result<Boolean> {
        return try {
            val snapshot = firestore.collection("candidaturas")
                .whereEqualTo("vagaId", vagaId)
                .whereEqualTo("alunoId", alunoId)
                .get()
                .await()
            
            Result.success(!snapshot.isEmpty)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun contarCandidaturasPorVaga(vagaId: String): Result<Int> {
        return try {
            val snapshot = firestore.collection("candidaturas")
                .whereEqualTo("vagaId", vagaId)
                .get()
                .await()
            
            Result.success(snapshot.size())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
