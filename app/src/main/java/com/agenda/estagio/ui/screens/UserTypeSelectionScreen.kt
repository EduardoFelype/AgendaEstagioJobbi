package com.agenda.estagio.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.agenda.estagio.navigation.Routes
import com.agenda.estagio.ui.theme.PrimaryBlue

@Composable
fun UserTypeSelectionScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Agenda Digital de Estágios",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = PrimaryBlue
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Selecione o tipo de acesso",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Botão Empresa
        UserTypeButton(
            text = "Sou uma Empresa",
            icon = Icons.Default.Business,
            onClick = { navController.navigate(Routes.EmpresaLogin.route) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Botão Aluno
        UserTypeButton(
            text = "Sou um Aluno",
            icon = Icons.Default.Person,
            onClick = { navController.navigate(Routes.AlunoLogin.route) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Botão Admin
        UserTypeButton(
            text = "Sou Administrador",
            icon = Icons.Default.AdminPanelSettings,
            onClick = { navController.navigate(Routes.AdminLogin.route) }
        )
    }
}

@Composable
fun UserTypeButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryBlue
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}
