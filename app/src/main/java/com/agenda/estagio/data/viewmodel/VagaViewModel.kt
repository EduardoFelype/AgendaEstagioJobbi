package com.agenda.estagio.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agenda.estagio.data.models.Vaga
import com.agenda.estagio.data.repository.VagaRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VagaViewModel : ViewModel() {
    private val repository = VagaRepository()
    private val auth = FirebaseAuth.getInstance()
    
    private val _vagasState = MutableStateFlow<VagasState>(VagasState.Idle)
    val vagasState: StateFlow<VagasState> = _vagasState.asStateFlow()
    
    private val _vagas = MutableStateFlow<List<Vaga>>(emptyList())
    val vagas: StateFlow<List<Vaga>> = _vagas.asStateFlow()
    
    private val _vagasEmpresa = MutableStateFlow<List<Vaga>>(emptyList())
    val vagasEmpresa: StateFlow<List<Vaga>> = _vagasEmpresa.asStateFlow()
    
    init {
        carregarVagasAtivas()
    }
    
    fun carregarVagasAtivas() {
        viewModelScope.launch {
            repository.getVagasAtivas().collect { vagasList ->
                _vagas.value = vagasList
            }
        }
    }
    
    fun carregarVagasEmpresa() {
        viewModelScope.launch {
            val empresaId = auth.currentUser?.uid ?: return@launch
            repository.getVagasByEmpresa(empresaId).collect { vagasList ->
                _vagasEmpresa.value = vagasList
            }
        }
    }
    
    fun criarVaga(vaga: Vaga) {
        viewModelScope.launch {
            _vagasState.value = VagasState.Loading
            
            val result = repository.criarVaga(vaga)
            
            result.onSuccess {
                _vagasState.value = VagasState.Success("Vaga criada com sucesso!")
                carregarVagasEmpresa()
            }.onFailure { error ->
                _vagasState.value = VagasState.Error(error.message ?: "Erro ao criar vaga")
            }
        }
    }
    
    fun atualizarVaga(vaga: Vaga) {
        viewModelScope.launch {
            _vagasState.value = VagasState.Loading
            
            val result = repository.atualizarVaga(vaga)
            
            result.onSuccess {
                _vagasState.value = VagasState.Success("Vaga atualizada com sucesso!")
                carregarVagasEmpresa()
            }.onFailure { error ->
                _vagasState.value = VagasState.Error(error.message ?: "Erro ao atualizar vaga")
            }
        }
    }
    
    fun deletarVaga(vagaId: String) {
        viewModelScope.launch {
            _vagasState.value = VagasState.Loading
            
            val result = repository.deletarVaga(vagaId)
            
            result.onSuccess {
                _vagasState.value = VagasState.Success("Vaga deletada com sucesso!")
                carregarVagasEmpresa()
            }.onFailure { error ->
                _vagasState.value = VagasState.Error(error.message ?: "Erro ao deletar vaga")
            }
        }
    }
    
    fun buscarVagas(query: String) {
        viewModelScope.launch {
            _vagasState.value = VagasState.Loading
            
            val result = repository.buscarVagas(query)
            
            result.onSuccess { vagasList ->
                _vagas.value = vagasList
                _vagasState.value = VagasState.Idle
            }.onFailure { error ->
                _vagasState.value = VagasState.Error(error.message ?: "Erro ao buscar vagas")
            }
        }
    }
    
    fun resetState() {
        _vagasState.value = VagasState.Idle
    }
}

sealed class VagasState {
    object Idle : VagasState()
    object Loading : VagasState()
    data class Success(val message: String) : VagasState()
    data class Error(val message: String) : VagasState()
}
