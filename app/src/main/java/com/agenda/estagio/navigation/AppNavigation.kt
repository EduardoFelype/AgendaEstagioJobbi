package com.agenda.estagio.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// IMPORTS CORRETOS — remova os desnecessários
import com.agenda.estagio.ui.screens.*
import com.agenda.estagio.ui.screens.admin.*
import com.agenda.estagio.ui.screens.aluno.*
import com.agenda.estagio.ui.screens.empresa.*
import com.agenda.estagio.screens.AlunoLoginScreen
import com.agenda.estagio.screens.EmpresaCadastroScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.UserTypeSelection.route
    ) {
        // === TELA INICIAL ===
        composable(Routes.UserTypeSelection.route) {
            UserTypeSelectionScreen(navController = navController)
        }

        // === EMPRESA ===
        composable(Routes.EmpresaLogin.route) {
            EmpresaLoginScreen(navController = navController)
        }
        composable(Routes.EmpresaCadastro.route) {
            EmpresaCadastroScreen(navController = navController)
        }
        composable(Routes.EmpresaHome.route) {
            EmpresaHomeScreen(navController = navController)
        }
        composable(Routes.EmpresaVagas.route) {
            EmpresaVagasScreen(navController = navController)
        }
        composable(Routes.EmpresaCriarVaga.route) {
            EmpresaCriarVagaScreen(navController = navController)
        }

        // === ALUNO ===
        composable(Routes.AlunoLogin.route) {
            AlunoLoginScreen(navController = navController)
        }
        composable(Routes.AlunoCadastro.route) {
            AlunoCadastroScreen(navController = navController)
        }
        composable(Routes.AlunoHome.route) {
            AlunoHomeScreen(navController = navController)
        }
        composable(Routes.AlunoVagas.route) {
            AlunoVagasScreen(navController = navController)
        }
        composable(Routes.AlunoCandidaturas.route) {
            AlunoCandidaturasScreen(navController = navController)
        }

        // === ADMIN ===
        composable(Routes.AdminLogin.route) {
            AdminLoginScreen(navController = navController)
        }
        composable(Routes.AdminHome.route) {
            AdminHomeScreen(navController = navController)
        }

        // === SUCESSO ===
        composable(
            route = Routes.Sucesso.route,
            arguments = listOf(navArgument("mensagem") { type = NavType.StringType })
        ) { backStackEntry ->
            val mensagem = backStackEntry.arguments?.getString("mensagem") ?: "Sucesso!"
            SucessoScreen(
                navController = navController,
                mensagem = mensagem
            )
        }
    }
}
