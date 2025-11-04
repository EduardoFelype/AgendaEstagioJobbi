package com.agenda.estagio.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agenda.estagio.data.models.User
import com.agenda.estagio.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()
    
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    init {
        verificarUsuarioLogado()
    }
    
    private fun verificarUsuarioLogado() {
        if (repository.isLoggedIn()) {
            carregarDadosUsuario()
        }
    }
    
    fun cadastrarAluno(
        email: String,
        senha: String,
        nome: String,
        telefone: String,
        cpf: String,
        dataNascimento: String,
        curso: String,
        instituicao: String,
        periodo: String
    ) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            
            val user = User(
                email = email,
                nome = nome,
                tipo = com.agenda.estagio.data.models.UserType.ALUNO,
                telefone = telefone,
                cpf = cpf,
                dataNascimento = dataNascimento,
                curso = curso,
                instituicao = instituicao,
                periodo = periodo
            )
            
            val result = repository.cadastrarUsuario(email, senha, user)
            
            result.onSuccess {
                carregarDadosUsuario()
                _authState.value = AuthState.Success("Cadastro realizado com sucesso!")
            }.onFailure { error ->
                _authState.value = AuthState.Error(error.message ?: "Erro ao cadastrar")
            }
        }
    }
    
    fun cadastrarEmpresa(
        email: String,
        senha: String,
        nomeFantasia: String,
        razaoSocial: String,
        cnpj: String,
        telefone: String,
        setor: String,
        cidade: String,
        estado: String
    ) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            
            val user = User(
                email = email,
                nome = nomeFantasia,
                tipo = com.agenda.estagio.data.models.UserType.EMPRESA,
                telefone = telefone,
                cnpj = cnpj,
                razaoSocial = razaoSocial,
                nomeFantasia = nomeFantasia,
                setor = setor,
                cidade = cidade,
                estado = estado
            )
            
            val result = repository.cadastrarUsuario(email, senha, user)
            
            result.onSuccess {
                carregarDadosUsuario()
                _authState.value = AuthState.Success("Cadastro realizado com sucesso!")
            }.onFailure { error ->
                _authState.value = AuthState.Error(error.message ?: "Erro ao cadastrar")
            }
        }
    }
    
    fun login(email: String, senha: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            
            val result = repository.login(email, senha)
            
            result.onSuccess {
                carregarDadosUsuario()
                _authState.value = AuthState.Success("Login realizado com sucesso!")
            }.onFailure { error ->
                _authState.value = AuthState.Error(error.message ?: "Erro ao fazer login")
            }
        }
    }
    
    fun carregarDadosUsuario() {
        viewModelScope.launch {
            val result = repository.getCurrentUserData()
            
            result.onSuccess { user ->
                _currentUser.value = user
            }.onFailure {
                _currentUser.value = null
            }
        }
    }
    
    fun atualizarUsuario(user: User) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            
            val result = repository.atualizarUsuario(user)
            
            result.onSuccess {
                _currentUser.value = user
                _authState.value = AuthState.Success("Dados atualizados com sucesso!")
            }.onFailure { error ->
                _authState.value = AuthState.Error(error.message ?: "Erro ao atualizar dados")
            }
        }
    }
    
    fun logout() {
        repository.logout()
        _currentUser.value = null
        _authState.value = AuthState.Idle
    }
    
    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
}
