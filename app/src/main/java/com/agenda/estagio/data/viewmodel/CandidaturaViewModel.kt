package com.agenda.estagio.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agenda.estagio.data.models.Candidatura
import com.agenda.estagio.data.models.CandidaturaStatus
import com.agenda.estagio.data.repository.CandidaturaRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CandidaturaViewModel : ViewModel() {
    private val repository = CandidaturaRepository()
    private val auth = FirebaseAuth.getInstance()
    
    private val _candidaturasState = MutableStateFlow<CandidaturasState>(CandidaturasState.Idle)
    val candidaturasState: StateFlow<CandidaturasState> = _candidaturasState.asStateFlow()
    
    private val _minhasCandidaturas = MutableStateFlow<List<Candidatura>>(emptyList())
    val minhasCandidaturas: StateFlow<List<Candidatura>> = _minhasCandidaturas.asStateFlow()
    
    private val _candidaturasPorVaga = MutableStateFlow<List<Candidatura>>(emptyList())
    val candidaturasPorVaga: StateFlow<List<Candidatura>> = _candidaturasPorVaga.asStateFlow()
    
    private val _candidaturasPorEmpresa = MutableStateFlow<List<Candidatura>>(emptyList())
    val candidaturasPorEmpresa: StateFlow<List<Candidatura>> = _candidaturasPorEmpresa.asStateFlow()
    
    fun carregarMinhasCandidaturas() {
        viewModelScope.launch {
            val alunoId = auth.currentUser?.uid ?: return@launch
            repository.getCandidaturasByAluno(alunoId).collect { candidaturas ->
                _minhasCandidaturas.value = candidaturas
            }
        }
    }
    
    fun carregarCandidaturasPorVaga(vagaId: String) {
        viewModelScope.launch {
            repository.getCandidaturasByVaga(vagaId).collect { candidaturas ->
                _candidaturasPorVaga.value = candidaturas
            }
        }
    }
    
    fun carregarCandidaturasPorEmpresa() {
        viewModelScope.launch {
            val empresaId = auth.currentUser?.uid ?: return@launch
            repository.getCandidaturasByEmpresa(empresaId).collect { candidaturas ->
                _candidaturasPorEmpresa.value = candidaturas
            }
        }
    }
    
    fun candidatar(candidatura: Candidatura) {
        viewModelScope.launch {
            _candidaturasState.value = CandidaturasState.Loading
            
            val result = repository.candidatar(candidatura)
            
            result.onSuccess {
                _candidaturasState.value = CandidaturasState.Success("Candidatura enviada com sucesso!")
                carregarMinhasCandidaturas()
            }.onFailure { error ->
                _candidaturasState.value = CandidaturasState.Error(error.message ?: "Erro ao candidatar")
            }
        }
    }
    
    fun atualizarStatus(candidaturaId: String, novoStatus: CandidaturaStatus) {
        viewModelScope.launch {
            _candidaturasState.value = CandidaturasState.Loading
            
            val result = repository.atualizarStatus(candidaturaId, novoStatus)
            
            result.onSuccess {
                _candidaturasState.value = CandidaturasState.Success("Status atualizado com sucesso!")
            }.onFailure { error ->
                _candidaturasState.value = CandidaturasState.Error(error.message ?: "Erro ao atualizar status")
            }
        }
    }
    
    fun cancelarCandidatura(candidaturaId: String) {
        viewModelScope.launch {
            _candidaturasState.value = CandidaturasState.Loading
            
            val result = repository.cancelarCandidatura(candidaturaId)
            
            result.onSuccess {
                _candidaturasState.value = CandidaturasState.Success("Candidatura cancelada")
                carregarMinhasCandidaturas()
            }.onFailure { error ->
                _candidaturasState.value = CandidaturasState.Error(error.message ?: "Erro ao cancelar")
            }
        }
    }
    
    suspend fun verificarCandidatura(vagaId: String): Boolean {
        val alunoId = auth.currentUser?.uid ?: return false
        val result = repository.verificarCandidatura(vagaId, alunoId)
        return result.getOrDefault(false)
    }
    
    fun resetState() {
        _candidaturasState.value = CandidaturasState.Idle
    }
}

sealed class CandidaturasState {
    object Idle : CandidaturasState()
    object Loading : CandidaturasState()
    data class Success(val message: String) : CandidaturasState()
    data class Error(val message: String) : CandidaturasState()
}
