package com.agenda.estagio.data.models

import com.google.firebase.firestore.DocumentId

data class Candidatura(
    @DocumentId
    val id: String = "",
    val vagaId: String = "",
    val vagaTitulo: String = "",
    val empresaId: String = "",
    val empresaNome: String = "",
    val alunoId: String = "",
    val alunoNome: String = "",
    val alunoEmail: String = "",
    val alunoCurso: String = "",
    val alunoInstituicao: String = "",
    val status: CandidaturaStatus = CandidaturaStatus.PENDENTE,
    val mensagem: String = "",
    val criadoEm: Long = System.currentTimeMillis(),
    val atualizadoEm: Long = System.currentTimeMillis()
)

enum class CandidaturaStatus {
    PENDENTE,
    EM_ANALISE,
    APROVADA,
    REJEITADA,
    CANCELADA
}

fun Candidatura.toMap(): Map<String, Any> {
    return hashMapOf(
        "vagaId" to vagaId,
        "vagaTitulo" to vagaTitulo,
        "empresaId" to empresaId,
        "empresaNome" to empresaNome,
        "alunoId" to alunoId,
        "alunoNome" to alunoNome,
        "alunoEmail" to alunoEmail,
        "alunoCurso" to alunoCurso,
        "alunoInstituicao" to alunoInstituicao,
        "status" to status.name,
        "mensagem" to mensagem,
        "criadoEm" to criadoEm,
        "atualizadoEm" to atualizadoEm
    )
}
