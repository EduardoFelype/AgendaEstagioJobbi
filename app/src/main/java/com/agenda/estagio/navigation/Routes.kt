package com.agenda.estagio.navigation

sealed class Routes(val route: String) {
    // Telas de Login/Seleção
    object UserTypeSelection : Routes("user_type_selection")
    
    // Empresa
    object EmpresaLogin : Routes("empresa_login")
    object EmpresaCadastro : Routes("empresa_cadastro")
    object EmpresaPerfil : Routes("empresa_perfil")
    object EmpresaDados : Routes("empresa_dados")
    object EmpresaAreaInteresse : Routes("empresa_area_interesse")
    object EmpresaHome : Routes("empresa_home")
    object EmpresaVagas : Routes("empresa_vagas")
    object EmpresaCriarVaga : Routes("empresa_criar_vaga")
    
    // Aluno
    object AlunoLogin : Routes("aluno_login")
    object AlunoCadastro : Routes("aluno_cadastro")
    object AlunoPerfil : Routes("aluno_perfil")
    object AlunoDados : Routes("aluno_dados")
    object AlunoHome : Routes("aluno_home")
    object AlunoVagas : Routes("aluno_vagas")
    object AlunoCandidaturas : Routes("aluno_candidaturas")
    
    // Admin
    object AdminLogin : Routes("admin_login")
    object AdminHome : Routes("admin_home")
    object AdminGerenciarAlunos : Routes("admin_gerenciar_alunos")
    object AdminGerenciarEmpresas : Routes("admin_gerenciar_empresas")
    object AdminGerenciarVagas : Routes("admin_gerenciar_vagas")
    
    // Compartilhadas
    object Sucesso : Routes("sucesso/{mensagem}") {
        fun createRoute(mensagem: String) = "sucesso/$mensagem"
    }
}
