package com.agenda.estagio.data.models

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    val id: String = "",
    val email: String = "",
    val nome: String = "",
    val tipo: UserType = UserType.ALUNO,
    val telefone: String = "",
    val cpf: String = "",
    val dataNascimento: String = "",
    
    // Campos específicos para Aluno
    val curso: String = "",
    val instituicao: String = "",
    val periodo: String = "",
    val areaInteresse: String = "",
    val habilidades: List<String> = emptyList(),
    val experiencia: String = "",
    
    // Campos específicos para Empresa
    val cnpj: String = "",
    val razaoSocial: String = "",
    val nomeFantasia: String = "",
    val setor: String = "",
    val descricao: String = "",
    val endereco: String = "",
    val cidade: String = "",
    val estado: String = "",
    
    val criadoEm: Long = System.currentTimeMillis(),
    val atualizadoEm: Long = System.currentTimeMillis()
)

enum class UserType {
    ALUNO,
    EMPRESA,
    ADMIN
}

fun User.toMap(): Map<String, Any> {
    return hashMapOf(
        "email" to email,
        "nome" to nome,
        "tipo" to tipo.name,
        "telefone" to telefone,
        "cpf" to cpf,
        "dataNascimento" to dataNascimento,
        "curso" to curso,
        "instituicao" to instituicao,
        "periodo" to periodo,
        "areaInteresse" to areaInteresse,
        "habilidades" to habilidades,
        "experiencia" to experiencia,
        "cnpj" to cnpj,
        "razaoSocial" to razaoSocial,
        "nomeFantasia" to nomeFantasia,
        "setor" to setor,
        "descricao" to descricao,
        "endereco" to endereco,
        "cidade" to cidade,
        "estado" to estado,
        "criadoEm" to criadoEm,
        "atualizadoEm" to atualizadoEm
    )
}
