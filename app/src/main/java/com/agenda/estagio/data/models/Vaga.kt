package com.agenda.estagio.data.models

import com.google.firebase.firestore.DocumentId

data class Vaga(
    @DocumentId
    val id: String = "",
    val empresaId: String = "",
    val empresaNome: String = "",
    val titulo: String = "",
    val descricao: String = "",
    val area: String = "",
    val requisitos: String = "",
    val beneficios: String = "",
    val cargaHoraria: String = "",
    val bolsa: String = "",
    val localizacao: String = "",
    val cidade: String = "",
    val estado: String = "",
    val modalidade: String = "", // Presencial, Remoto, Híbrido
    val status: VagaStatus = VagaStatus.ATIVA,
    val numeroVagas: Int = 1,
    val criadoEm: Long = System.currentTimeMillis(),
    val atualizadoEm: Long = System.currentTimeMillis()
)

enum class VagaStatus {
    ATIVA,
    PAUSADA,
    ENCERRADA
}

fun Vaga.toMap(): Map<String, Any> {
    return hashMapOf(
        "empresaId" to empresaId,
        "empresaNome" to empresaNome,
        "titulo" to titulo,
        "descricao" to descricao,
        "area" to area,
        "requisitos" to requisitos,
        "beneficios" to beneficios,
        "cargaHoraria" to cargaHoraria,
        "bolsa" to bolsa,
        "localizacao" to localizacao,
        "cidade" to cidade,
        "estado" to estado,
        "modalidade" to modalidade,
        "status" to status.name,
        "numeroVagas" to numeroVagas,
        "criadoEm" to criadoEm,
        "atualizadoEm" to atualizadoEm
    )
}
