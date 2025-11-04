package com.agenda.estagio.data.repository

import com.agenda.estagio.data.models.User
import com.agenda.estagio.data.models.UserType
import com.agenda.estagio.data.models.toMap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    
    val currentUser: FirebaseUser?
        get() = auth.currentUser
    
    suspend fun cadastrarUsuario(
        email: String,
        senha: String,
        user: User
    ): Result<String> {
        return try {
            // Criar usuário no Firebase Auth
            val authResult = auth.createUserWithEmailAndPassword(email, senha).await()
            val userId = authResult.user?.uid ?: throw Exception("Erro ao criar usuário")
            
            // Salvar dados do usuário no Firestore
            val userWithId = user.copy(id = userId, email = email)
            firestore.collection("users")
                .document(userId)
                .set(userWithId.toMap())
                .await()
            
            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun login(email: String, senha: String): Result<String> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, senha).await()
            val userId = authResult.user?.uid ?: throw Exception("Erro ao fazer login")
            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUserData(userId: String): Result<User> {
        return try {
            val document = firestore.collection("users")
                .document(userId)
                .get()
                .await()
            
            val user = document.toObject(User::class.java)
                ?: throw Exception("Usuário não encontrado")
            
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getCurrentUserData(): Result<User> {
        return try {
            val userId = currentUser?.uid ?: throw Exception("Usuário não autenticado")
            getUserData(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun atualizarUsuario(user: User): Result<Unit> {
        return try {
            val userId = currentUser?.uid ?: throw Exception("Usuário não autenticado")
            val userAtualizado = user.copy(
                id = userId,
                atualizadoEm = System.currentTimeMillis()
            )
            
            firestore.collection("users")
                .document(userId)
                .set(userAtualizado.toMap())
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun logout() {
        auth.signOut()
    }
    
    fun isLoggedIn(): Boolean {
        return currentUser != null
    }
}
